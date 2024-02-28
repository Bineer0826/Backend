

## @Bean & @Configuration  기본개념

> 스프링의 자바 구성 지원의 핵심은 @Configuration  주석의 클래스와 @Bean의 주석의 메서드이다
> <bean/>은 @Bean과 동일한 역할을 한다 하나씩 알아보자.

- 어떤 클래스에 `@Configuration`이 있다면, bean 정의를 나타낸다
- 클래스에서는  `@Configuration` 다른 메서드를 호출해서 bean 간의 종속성을 정의할 수 있음
<br>
<br>
```java
@Configuration
public class AppConfig { // AppConfig 클래스에 configuration주석으로 bean을 정의를 나타냈다

	@Bean
	public MyServiceImpl myService() { // bean 정의
		return new MyServiceImpl();
	}
}
```
동일하게 xml에서 표현할 수 있다.
```xml
<beans>
	<bean id="myService" class="com.acme.services.MyServiceImpl"/>
</beans>
```
<br>
<br>


##### @bean이 없는 상태의 @Configuration 적용하면??
: @Bean이 없는 @Configuration의 메소드가 호출되면 라이트모드에 들어간다.
이렇게 configuration 라이트버전에서는 @Bean 메서드는 bean의 종속성을 선언할 수 없다
자세한 사항은 문서 참고
<br>
<br>
<br>
<br>

----

## AnnotationConfigApplicationContext
: ApplicationContext의 구현채로 @Configuration 클래스 뿐만 아니라 어노테이션 클래스도 ok. 또, 어노테이션 클래스를 사용하면 @Autowired나 @Inject 같은 종속성 메타데이터 사용되었다고 가정함
<br>
<br>


### Spring 컨테이너 인스턴스화(객체생성 방법)
> 위에서 배운 것처럼 @Configuration로 클래스가 입력되면 @Bean으로 정의가 등록되고 선언된 모든 메서드도 Bean 정의로 등록된다
> 

<br>
<br>
#### 구성
`AnnotationCOnfigApplicationContext` 와 @Configuration으로 Spring 컨테이너(객체 모음박스)가 xml없이 사용 가능함.

```java
public static void main(String[] args) {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	MyService myService = ctx.getBean(MyService.class);
	myService.doStuff();
}
```
<br>
혹은 @Component로 생성자를 제공할 수도 있다.

`AnnotationCOnfigApplicationContext`타입에 appConfig. class 이외에 여러가지 작성하면 종속성 주입이라고 볼 수 있다.
<br>
<br>
<br>
<br>

###  register(class< ?>) 생성자로 컨테이너 만들기
: `AnnotationCOnfigApplicationContext` 객체 생성(인스턴스화) 이후에 `register()`를 이용해서 컨테이너를 만들 수 있다.

```java
public static void main(String[] args) {
	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(); //인스턴스화
	ctx.register(AppConfig.class, OtherConfig.class); // register 생성자를 이용해서 클래스 컨테이너 만들기
	ctx.register(AdditionalConfig.class); // 같은 방법
	ctx.refresh();
	MyService myService = ctx.getBean(MyService.class);
	myService.doStuff();
}
```

<br>
<br>
<br>
<br>

###  구성요소 검색
#### @ComponentScan
: 스프링이 @Component 주석을 보면 `com.acme`를 찾기 위해 패키지를 스캔하고 해당 주석이 있는 클래스는 컨테이너 내의 bean으로 등록한다
	즉,  컨테이너에 등록되어있는 것들은 스캔할때 나와야한다는 것.
```java
@Configuration
@ComponentScan(basePackages = "com.acme") 
public class AppConfig  {
	// ...
}
```
```xml
<beans>
	<context:component-scan base-package="com.acme"/> //xml선언도 당연히 가능
</beans>
```

<br>
<br>
<br>

#### .scan(String ...)
: 이거는 동일한 구성요소를 검색할 수 있는 메서드를 `AnnotationConfigApplicationContext`에 노출한다

```java
public static void main(String[] args) {
	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(); // 객체 생성
	ctx.scan("com.acme"); // 동일한 구성요소 검색
	ctx.refresh();
	MyService myService = ctx.getBean(MyService.class);
}
```
<br>

❗️ @Configuration 클래스는 @Component가 이미 달려있으므로 구성요소 검색대상인 셈이다! 또한, .scan("com.acme"); 에서 .refresh()까지 bean 정의로으로 처리한다

<br>
<br>
<br>

---

## @Bean 주석 사용
다음의 기능을 지원함
- 초기화 방법
- 삭제 방법
- 자동 연결
- `name.`
-  @Configuration 과 @Component 클래스 내에서도 사용 가능하다
<br>
<br>

### bean 선언
: 선언하기 위해서 @Bean 주석을 사용해야한다
ApplicationContext 메서드를 이용해서 지정된 유형으로 bean을 등록함

==`@Bean("__")` ""안에 값으로 이름의 커스터마이징 가능하고, `,`로 `name` 구분==

❗️ ApplicationContext는 어플리케이션의 구성을 제공하는 중앙 인터페이스이다.
	[[Spring - (1) 스프링 Container & Bean]] 참고
	정의읽기, 검색기능이 있다

	ApplicationContext 메서드
	
		1. `   getBean(String name)`: 빈의 이름을 기반으로 빈을 반환합니다. 이 메서드를 사용하여 ApplicationContext에서 빈을 가져올 수 있습니다.
		2. `getBean(Class<T> requiredType)`: 지정된 타입의 빈을 반환합니다. 이 메서드를 사용하여 빈을 타입에 따라 가져올 수 있습니다.
		3. `containsBean(String name)`: 주어진 이름으로 된 빈이 ApplicationContext에 등록되어 있는지 여부를 확인합니다.
		4. `isSingleton(String name)`: 주어진 이름의 빈이 싱글톤인지 여부를 확인합니다.
		5. `getAliases(String name)`: 주어진 이름에 대한 별칭(aliases) 배열을 반환합니다
		6. `getDisplayName()`: ApplicationContext의 디스플레이 이름을 반환합니다.
		7. `getParent()`: 부모 ApplicationContext를 반환합니다. 부모 컨텍스트가 설정되어 있는 경우 사용됩니다.
		8. `getEnvironment()`: ApplicationContext의 환경(Environment)을 반환합니다. 환경 설정 및 프로퍼티 관리에 사용됩니다.
		9. `getMessageSource()`: 메시지 소스(MessageSource)를 반환합니다. 국제화(i18n)와 메시지 처리에 사용됩니다.
		10. `start()`: ApplicationContext를 시작합니다. 주로 라이프사이클 관리에 사용됩니다.
		11. `stop()`: ApplicationContext를 중지합니다. 주로 라이프사이클 관리에 사용됩니다.
		12. `close()`: ApplicationContext를 종료하고 리소스를 해제합니다. 애플리케이션이 종료될 때 호출됩니다.
		13. `getId()`: ApplicationContext의 고유 식별자(ID)를 반환합니다.
		14. `getApplicationName()`: ApplicationContext의 어플리케이션 이름을 반환합니다.
		15. `getAutowireCapableBeanFactory()`: Autowire를 지원하는 빈 팩토리를 반환합니다.
<br>
ApplicationContext의 메서드 이용한 선언이다.
```java
@Configuration
public class AppConfig {
	@Bean 
	//빈을 선언
	public TransferServiceImpl transferService() { //TransferSerive 이름의 클래스 등록
		return new TransferServiceImpl(); // transterServiceImpl를 메서드처럼 사용가능
	}
}
```
<br>
XML으로 나타내면
```xml
<beans>
	<bean id="transferService" class="com.acme.TransferServiceImpl"/> // 앞서 배운것처럼 @configuration은 검색 대상이므로 com.acme.~로 등록된 것을 볼 수 있다.
</beans>
```
<br>
기본 메서드를 사용해서 정의가능하다
```java
public interface BaseConfig {
	// configuration을 사용하지 않고 bean을 정의 했다
	@Bean
	default TransferServiceImpl transferService() {
		return new TransferServiceImpl();
	}
}

@Configuration
public class AppConfig implements BaseConfig {
// baseconfig를 구현한 클래스 내에서 trans~ 메서드를 호출할 수 있다.
}
```
<br>
원래는 위와같이 인터페이스를 구현후에 사용해야하지만 그냥 기본 클래스로 사용해서 메서드를 선언할 수 있다.
```java
@Configuration
public class AppConfig { //인터페이스 구현 안함
//하지만 configuration으로 trans~ 메서드 사용가능
	@Bean
	public TransferService transferService() { 
		return new TransferServiceImpl();
	}
}
```
->따로 구현하지 않으면 transferService처럼 예상이 가능한 메서드만 사용가능하도록 제한한다 그러나, TransferServiceImpl 영향을 받은 다른 bean이 객체 생성하면 컨테이너 전체에 알려진다. 동일한 컨테이너를 시도하는 경우 다른 유형으로 간주될 수 있다(어려운 개념이므로 더 공부하기로 한다....)

<br>
<br><br>



<br>
<br>

### Bean 의존성
: 반환할 메서드의 매개변수(파라미터)를 이용하면 종속성을 구체화 할 수 있다
```java
@Configuration
public class AppConfig {

	@Bean
	public TransferService transferService(AccountRepository accountRepository) {
	//그냥 transferService가 아닌 accountRepository와 연결되어있다고 구체적으로 알려준다.
		return new TransferServiceImpl(accountRepository);
	}
}
```

<br>
<br>
<br>

### 수명주기 콜백
: 객체가 언제 딱 호출되고 사라질 것인지의 수명주기를  앞서서 컨테이너가 지원한다고 배웠다 보통 일반 수명주기를 알려주고 Spring의 라이프사이클도 알려준다

- 수명주기 알기
	- @Bean 에서 일반 수명주기 알기
	- @PostConstruct 등의 주석을 이용해서 수명주기 알기
- 라이프사이클 알기
- `Aware` 인터페이스 세트 지원(spring 익숙해지고 나서 알아보자)
<br>
<br>

#### @Bean에서 일반 수명주기 알기
**초기화 정리 메서드를 직접 이용한다**
`@Bean(initMethod = "__" or destroyMethod = "__")` 를 이용
<br>
> 방법이 다양하다로 알고 있으면 된다 겁먹지 말것!
<br>

```java
public class BeanOne {

	public void init() { 
		// initialization logic
	}
}

public class BeanTwo {

	public void cleanup() {
		// destruction logic
	}
}

//configuration에는 자동으로 소멸 공백에 등록하게 된다

@Configuration
public class AppConfig { 

	@Bean(initMethod = "init") 
	public BeanOne beanOne() {
		return new BeanOne();
	}

	@Bean(destroyMethod = "cleanup") // 비활성할 수도 있다.
	public BeanTwo beanTwo() {
		return new BeanTwo();
	}
}
```
<br>

**직접 메서드에서 init()호출**
```java
@Configuration
public class AppConfig {

	@Bean
	public BeanOne beanOne() {
		BeanOne beanOne = new BeanOne();//객체 만들고
		beanOne.init();//바로 init 생성자호출
		return beanOne;
	}

	// ...
}
```
<br>
<br>

#### @PostConstruct & @ PreDestroy 사용

**@PostConstruct** : 빈이 초기화된 후에 실행되어야 하는 메서드를 표시해준다
- 빈이 초기화 후에 호출
- 빈의 설정과 초기화 작업을 수행하는데 사용

```java
import javax.annotation.PostConstruct;

public class MyBean {
    @PostConstruct // 초기화 되고서 호출했다
    public void init() { //init()메서드로 초기화 작업 수행
        // 초기화 로직을 여기에 작성
        System.out.println("빈 초기화됨");
    }
}

```
<br>

**@PreDestroy** : 빈이 제거되기 전에 실행되어야 하는 메서드를 표시
- 빈이 제거되기 전에 호출
- 정리 작업이나 리소스 해제 작업 수행
```java
import javax.annotation.PreDestroy;

public class MyBean {
    @PreDestroy // 제거 전에 호출
    public void destroy() { //destroy메서드를 이용해서 어덯게 정리할 건지 작성
        // 정리 로직을 여기에 작성
        System.out.println("빈 제거됨");
    }
}

```
<br>

스프링이 위의  두 개의 어노테이션을 인식하려면 `CommonAnnotationBeanPostProcessor`을 구성해야함

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor; 
@Configuration
public class AppConfig {

    @Bean
    public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
        return new CommonAnnotationBeanPostProcessor();// @초기화와 정리 어노테이션를 인식할수 있도록 객체를 생성한다 = 자동호출가능

    }
}

```
<br>
<br>

### 범위 지정
**`@Scope` 이용** : 기본범위이지만 싱글톤을 이용해서 범위를 재정의할 수 있다
   
```java
@Configuration
public class MyConfiguration {

	@Bean
	@Scope("prototype")
	public Encryptor encryptor() {
		// ...
	}
}
```
<br>

**`@Scope` + `scoped-proxy` 이용** : 범위가 지정된 프록시를 이용해서 범위가 지정된 종속성을 작업할 때 사용
이건 어떻게사용하는지 모르니까 강의로....
```java
// an HTTP Session-scoped bean exposed as a proxy
@Bean
@SessionScope
public UserPreferences userPreferences() {
	return new UserPreferences();
}

@Bean
public Service userService() {
	UserService service = new SimpleUserService();
	// a reference to the proxied userPreferences bean
	service.setUserPreferences(userPreferences());
	return service;
}
```
<br>
<br>


### 설명문
`@Bean` 이후에 `@Description("_하고싶은 말_")` 을 이용해서  설명을 추가할 수 있다
```java
@Configuration
public class AppConfig {

	@Bean
	@Description("Provides a basic example of a bean") //설명문
	public Thing thing() {
		return new Thing();
	}
}
```

<br>
<br>
<br>
<br>

----
## @Configuration 사용
: 클래스를 설정파일로 지정하면 해당 클래스내의 정의된 bean에 대한 구성정보를 스프링 컨테이너에 제공하는 주석

- @configuration 클래스는 어노테이션 메서드를 통해 bean 선언
- 빈 사이의 종속성 주입
- 조회 방법 주입
- 내부적으로 작동하는 방식에 대한 추가정보

참고 : [[#@Bean & @Configuration 기본개념]]
<br>

### Bean 사이의 종속성 주입
생성자를 추가해서 beanOne과 beanTwo가 연결됨을 알 수 있다.
```java
@Configuration
public class AppConfig {

	@Bean
	public BeanOne beanOne() {
		return new BeanOne(beanTwo());//연관되어있음
	}

	@Bean
	public BeanTwo beanTwo() {
		return new BeanTwo();
	}
}
```

❗️일반 @Component 클래스를 사용해서 Bean 종속성을 선언할 수는 없다
<br>
<br>

### 조회 방법 주입
: 고급기능이라 잘 사용을 안하는 기능... 나중에 알아보자

### Java 기반 구성이 내부적으로 작동하는 방식에 대한 추가정보
: 주석 달린 메서드가 2번 호출된다
```java
@Configuration
public class AppConfig {

	@Bean
	public ClientService clientService1() {
		ClientServiceImpl clientService = new ClientServiceImpl();
		clientService.setClientDao(clientDao());
		return clientService;
	}

	@Bean
	public ClientService clientService2() {
		ClientServiceImpl clientService = new ClientServiceImpl();
		clientService.setClientDao(clientDao());
		return clientService;
	}

	@Bean
	public ClientDao clientDao() {
		return new ClientDaoImpl();
	}
}
```

<br>
<br>
<br>
<br>

---
## Java 기반 구성 작성

### @Import 사용
:  구성 모듈화로 <import/> 로 xml에 적용하는 것처럼 다른 클래스의 구성을 현재 클래스에 가져와서 사용하도록 지시하는 어노테이션
- 여러 구성 클래스를 하나로 조합
- 하나로 조합하면 여러개의 설정을 관리하기 좋음
- 만들어놓은 모듈화를 재사용가능
<br>
<br>

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConfigA.class, ConfigB.class}) // A와 B연결
public class AppConfig {
    // AppConfig 클래스에서 ConfigA 및 ConfigB 구성 클래스를 가져옵니다.
}

```
또한 A와 B를 한 번에 객체  생성도 가능하다
```java
public static void main(String[] args) {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);

	// now both beans A and B will be available...
	A a = ctx.getBean(A.class);
	B b = ctx.getBean(B.class);
}
```
<br>
<br>

#### @Bean에 가져온 정의에 대한 종속성
> 앞에서 임포트와 더불어 bean은 많이 연관되어 있고 서로서로 종속성을 갖는다. xml을 사용할 때는 ,컴파일러를 포함하지 않아서 종속성에 대해서 문제가 생기지 않고 컨테이너 초기화 하더라도 스프링을 이용하면 된다
> 하지만 @Configuration 클래스를 사용할 때, java 컴파일러는 다른 bean에 대한 참조가 자바 구문이어야 한다는 점에서 제약이 생긴다

(사실 어떤 문제점이 생기는 건지 다가오지 않는다 이부분은 다시 읽어봐야할듯)

이를 해결하기 위해서, [[#Bean 사이의 종속성 주입]] 에서 매개변수를 가지면 된다

```java
@Configuration
public class ServiceConfig { //클래스 정하고, 객체 생성하고

	@Bean
	public TransferService transferService(AccountRepository accountRepository) {
		return new TransferServiceImpl(accountRepository);
	}
}

@Configuration
public class RepositoryConfig { // 클래스 정하고, 객체 생성하고

	@Bean
	public AccountRepository accountRepository(DataSource dataSource) {
		return new JdbcAccountRepository(dataSource);
	}
}

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class}) //두가지를 종속하도록 임포트
public class SystemTestConfig { //두 클래스를 종속하는 클래스 생성

	@Bean
	public DataSource dataSource() { // 두 클래스와 관련된 data source
		// return new DataSource
	}
}

public static void main(String[] args) {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);//또다른 클래스와 연관
	// everything wires up across configuration classes...
	TransferService transferService = ctx.getBean(TransferService.class); // 서비스의 객체를 반환하는 zm클래스에서 빈을 등록
	transferService.transfer(100.00, "A123", "C456");//값
}
```

- 하나의 bean에 다른 bean 연결하기
```java
@Configuration
public class ServiceConfig {

	@Autowired
	private AccountRepository accountRepository; //리포지토리와 연결

	@Bean //빈 정의와 등록
	public TransferService transferService() {
		return new TransferServiceImpl(accountRepository); // 매개변수 리포지토리로
	}
}

@Configuration
public class RepositoryConfig { 

	private final DataSource dataSource;

	public RepositoryConfig(DataSource dataSource) { //리포지토리인데 ,data를 기반
		this.dataSource = dataSource;
	}

	@Bean
	public AccountRepository accountRepository() { //리포지토리 생성 data reSource연결
		return new JdbcAccountRepository(dataSource);
	}
}

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class}) // 두 클래스 가져오기
public class SystemTestConfig { // 클래스 생성

	@Bean
	public DataSource dataSource() { //dataSource 정의
		// return new DataSource
	}
}

public static void main(String[] args) {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class); // 테스트롤 객체 생성해도 dataSource 필요하고 datasource는 레파지토리와 어카운트 레파지토리 모두 끌려온다
	// everything wires up across configuration classes...
	TransferService transferService = ctx.getBean(TransferService.class); //이 클래스에 서비스 클래스에 대한 객체 등록
	transferService.transfer(100.00, "A123", "C456");
}
```




### @Configuration 클래스 또는 @Bean 메소드를 조건부로 포함
`@Profile` 은 `@Conditional`이라는 주석을 더 유연하게 사용해서 구현한다
`@Conditionoal`  :  빈이 등록되기 전에 org.springframework.context.annotation.Condition를 먼저 참조해야한다

- boolean 값을 반환하는 `match(...)를 사용한다
```java
@Override
public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) { // 매치 구문을 사용했다,
	// Read the @Profile annotation attributes
	MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
	if (attrs != null) {
		for (Object value : attrs.get("value")) {
			if (context.getEnvironment().acceptsProfiles(((String[]) value))) {
				return true; // 불리언 값으로 반환
			}
		}
		return false; // 불리언 값으로 반환
	}
	return true; // 불리언 값으로 반환
}
```

### Java 와 XML 구성 결합