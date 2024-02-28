
> 스프링 컨테이너는 의존성 주입 DI와 밀접하다 왜냐하면 컨테이너는 쉽게 말해 객체를 모은 박스의 형태인데,
> 서로 참조가 많아지면 의존성이 높아지기 때문이다.
> 또 프로젝트가 많아지면 인터페이스의 구현 클래스 일부를 변경할 때 의존성을 제거하고 인터페이스에만 의존하도록 사용이 가능
<br>

## Container
: 스프링 프레임워크의 핵심 컴포넌트(중요한 요소)
<br>

- 자바 객체의 생명주기 관리, 객체들의 추가적인 기능제공 = 빈의 생명주기 관리, 기능제공
- 스프링에서는 자바 객체 = Bean 빈
- 원하는 만큼 많은 객체!  당연한 소리
- 서로 다른 빈을 연결해 애플리케이션 빈을 연결하는 역ㅏㄹ
- 메서드가 언제 호출되어야 하는지, 필요한 매개변수가 무엇인지 걱정 no
 
<br>
<br>


### 종류
두 종류의 인터페이스로 이루어짐
<br>
1. Beanfactory
    : 빈의 생성과 관계설정같은 제어를 담당 = IoC Object = 오브젝트 = 최상위 인터페이스
	    - 빈(객체)의 등록과 생성, 조회 -> ==`getBean()` 메서드를 이용해서 객체를 인스턴스화!!!==
	    - `@Bean` 어노테이션으로 스프링 빈의 이름을 사용해서 객체를 등록한다
	      
2. ApplicationContext
	   : Beanfactory가 최상위 인터페이스이므로 해당 기능을 상속받고 추가로 부가기능을 제공
	   
- bean의 정의를 읽기
```Java

// 동일 하게 new 메서드를 이용해서 생성
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```


  - bean의 인스턴스 대표적인 검색과 사용 = `getbean()`
```
// bean의 인스턴스를 검색할 수 있음 getbean을 이용하면 됨
PetStoreService service = context.getBean("petStore", PetStoreService.class);

// 인스턴스 사용
List<String> userList = service.getUsernameList();

```



| 기능 | 설명 |
| --- | --- |
| MessageSource | 메세지 인터페이스 |
| EnvironmentCapable |개발, 운영, 환경변수 등으로 나누어 처리하고, 애플리케이션 구동 시 필요한 정보들을 관리하기 위한 인터페이스
| ApplicationEventPublisher | 이벤트 관련 기능을 제공하는 인터페이스 |
| ResourceLoader |파일, 클래스 패스, 외부 등 리소스를 편리하게 조회|


### 생성 방법 + 방식
: 구성 메타데이터를 형식을 사용
XML 기반 메타데이터, 어노테이션 기반의 자바 설정 클래스로 만들었지만, 스프링 부트로 자동
<br>
> 하지만 기존 방식인 XML 방식도 알아보아야함.

꺽새조와 골뱅이 조로 나뉨ㅎㅎ
<br>
1. XML 방식 :  빈을 <bean/> -> 최상위, <beans/> -> 요소의 요소로 구성
```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
		https://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="..." class="...">
		<!-- collaborators and configuration for this bean go here -->
	</bean>

	<bean id="..." class="...">
		<!-- collaborators and configuration for this bean go here -->
	</bean>

	<!-- more bean definitions go here -->

</beans>

```

- 틀만 보기로...html처럼<> 로 이루어져있다

2. 자바 구성 방식 : 주로 @bean 내의 어노테이션 메서드를 이용한다 @Configuration

이렇게 bean을 정의하면 애플리케이션의 실제 객체에 해당하고 도메인 개체를 생성하고 로드하는 것은 리포지토리와 비즈니스 논리의 책음으로 넘어가기 때문에 컨테이너에서는 도메인 객체 구성을 하지 않는다
<br>
<br>
<br>
<br>

---
## Bean
### XML 방식

<br>

#### 빈 정의 
: `BeanDefinition` 에 하단의 메타데이터를 포함하는 객체가 표시됨
-  실제 구현클래스 이름
-  bean이 작업 수행에 필요한 다른 빈(객체)를 참조할때의 종속성
- 연결하는 범위를 관리하고 제한


| 속성 | 기능 |
| ---- | ---- |
| class | bean의 인스턴스화 |
| name | bean의 이름 |
| scope | bean의 범위 |
| constructor arguments | 의존성 주입 |
| 속성들 | 의존성 주입 |
| 자동연결모드 | 자동 연결 협력 |
| 지연초기화모드 | 지연 초기화 |
| 초기화모드 | 초기화 불러오기 |
| 파기모드 | 파기한거 불러오기 |

<br>
<br>

##### 1. 이름 설정
: 보통 한 개의 bean은 하나의 이름만 있지만 여러 개가 되는경우, 별명으로 간주한다.

- 내부에서 이름 지정 :  `id` 와 `name` 둘다 사용하여 빈을 구분
	- id는 무조건 하나만 가능! + 특수문자도 가능
	- id를 지정하지 않으면 스프링이 알아서 제공
	- 2개의 이름일 경우, name 사용
	- 2개의 문자가 나열될 경우, 대문자를 쓰는 카멜케이스 사용

- 외부에서 bean 별명 지정 : XML 의 `<alias/>` 요소 이용
```XML
<alias name="fromName" alias="toName"/>
```

my~Source 이름의 하위 A와 B가 있어도 이렇게 별칭을 이용하면 세 이름이 모두 동일한 개체를 참조하도록 함
```XML
<alias name="myApp-dataSource" alias="subsystemA-dataSource"/>
<alias name="myApp-dataSource" alias="subsystemB-dataSource"/>
```

<br>
<br>

##### 2. bean 인스턴스화
:  객체를 생성하는 방법
<br>

1) `new` 생성자 키워드 이용
    동일하게 id 와 name, class 지정하고 해당 class 내에 A a = new A(); 형식 작성
<br>

2) static 메서드 이용
	클래스 보다 먼저 객체를 생성할 수 있는  static 메서드를 호출하도록 지정한다
	ex)
	```XML
	<bean id="clientService" // 이름 지정
		class="examples.ClientService" // 클래스는 ClientService
		factory-method="createInstance"/> // 사용 메서드 -> 
	```
	함께 작동되는 클래스
	```java
	public class ClientService { // 클래스 이름 일치
		private static ClientService clientService = new ClientService(); 
		// static은 바로 객체 생성 가능
		private ClientService() {}
	
		public static ClientService createInstance() { // Static 사용메섣,
			return clientService;
		}
	}
	```
	자바코드로 인해 궁극적으로 clientService의 메서드를 호출하게 된다.
<br>

3) 사용하던 클래스의 메서드가 아닌 최상위 클래스 = beanFactory의  메서드를 이용
	사용하려는 빈의 상위 빈을 만들어서 이름 지정하고 `factory-bean` 과 `factory-method`를 이용해서 사용하려는 곳의 객체와 메서드를 만들어낸다
	
```xml
   <!-- the factory bean, which contains a method called createClientServiceInstance() -->
   
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
	<!-- inject any dependencies required by this locator bean -->
</bean>

<!-- the bean to be created via the factory bean -->
// serviceLocator의 상위 clientService 빈 생성과 메서드 이용을 한다
<bean id="clientService"
	factory-bean="serviceLocator"
	factory-method="createClientServiceInstance"/>

```
함께 작동되는 클래스
```java
public class DefaultServiceLocator { //사용하려는 클래스를 이용할 수 있게 되었다

	private static ClientService clientService = new ClientServiceImpl();
	//정적메서드로 객체 생성을 먼저 진행

	public ClientService createClientServiceInstance() {
	// factory-method로 지정한 메서드가 생성되었다
	//내부에서는 clientService를 리턴하고 있으므로 하위의 serviceLocator도 clientService를 값으로 내놓는다
		return clientService;
	}
}

```

❗이 방식은 종속성 주입을 통해 factory bean 자체를 관리하고 구성할 수 있다.
또, 팩토리 빈은 위와같이 인스턴스나 정적메서드를 이용해서 객체를 생성하는 빈을 의미함

<br>
<br>

##### 3. bean의 런타임 유형
: `BeanFactory.getType`  지정된 빈 이름을 호츌하는 것 / `BeanFactory.getBean` 동일하게 묶여있는 이름의 bean들을 호출

<br>
<br>
<br>
<br>

### @Bean 어노테이션 방식
#### 제공되는 속성
: XML과  비슷한 속성들을 제공한다 

- 초기화 방법
- 파기하는 방법
- 자동연결 모드
- `name`
<br>
<br>

#### 1. 빈 설정하기
@Bean을 이용해서 ApplicationContext의 정의 기능을 이용하고 메서드와 이름이 동일하다
```java
@Configuration
public class AppConfig {

	@Bean 
	//빈을 선언
	public TransferServiceImpl transferService() { //TransferSerive 이름
		return new TransferServiceImpl(); // 객체 생성
	}
}
```

XML으로 나타내면
```xml
<beans>
	<bean id="transferService" class="com.acme.TransferServiceImpl"/>
</beans>
```

==`@Bean("__")` ""안에 값으로 이름의 커스터마이징 가능하고, `,`로 `name` 구분==


<br>
<br>

#### 2. 빈 의존성
: 반환할 메서드의 매개변수를 이용하면 종속성을 구체화 할 수 있다
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

#### 3. 수명주기에 따른 콜백
: 객체가 언제 딱 호출되고 사라질 것인지의 수명주기를  앞서서 컨테이너가 지원한다고 배웠다
보통 일반 수명주기를 알려주고 Spring의 라이프사이클도 알려준다

`@Bean(initMethod = "__" or destroyMethod = "__")` 를 이용
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

@Configuration
public class AppConfig { 

	@Bean(initMethod = "init") 
	public BeanOne beanOne() {
		return new BeanOne();
	}

	@Bean(destroyMethod = "cleanup")
	public BeanTwo beanTwo() {
		return new BeanTwo();
	}
}
```
아니면 직접 메서드를 호출할 수도 있다.`init()` 생성자 사용.
```java
@Configuration
public class AppConfig {

	@Bean
	public BeanOne beanOne() {
		BeanOne beanOne = new BeanOne();
		beanOne.init();
		return beanOne;
	}

	// ...
}

```

<br>
<br>

#### 3. 범위 지정
1) `@Scope` 이용 : 기본범위이지만 싱글톤을 이용해서 범위를 재정의할 수 있다
   
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

2) `@Scope` + `scoped-proxy` 이용 : 범위가 지정된 프록시를 이용해서 범위가 지정된 종속성을 작업할 때 사용
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


#### 4. 설명문 작성
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

##

### Bean 정의 상속

> 앞서 배운 것처럼 생성자, 속성 값, 메소드, 정적 메소드등  객체 내에는 많은 정보가 포함 되어있다. 클래스 상속처럼 빈 하위 상속도 상위 빈의 데이터를 상속 받는다
> 템플릿처럼 상위 / 하위 bean 정의를 하용하면 입력 작업을 줄임
<br>
: 인터페이스로 작업하는 경우 하위 빈은 `ChildBeanDefinition` .
<br>
xml에서는... 

```xml
<bean id="inheritedTestBean" abstract="true"
		class="org.springframework.beans.TestBean">
	<property name="name" value="parent"/>
	<property name="age" value="1"/>
</bean>

<bean id="inheritsWithDifferentClass"
		class="org.springframework.beans.DerivedTestBean"
		parent="inheritedTestBean" init-method="initialize">
	<property name="name" value="override"/>
	<!-- the age property value of 1 will be inherited from parent -->
</bean>
```

하위 bean은 값, 메서드, 범위, 생성자 인수 재정의를 당연히 재정의 하지만, ==나머지는 하위정의(종속성, 자동연결모드,싱글톤 및 지연초기화)에서 가져옮==
<br>
<br>

----

### 출처

요약 정리 기본 : [https://ittrue.tistory.com/220](https://ittrue.tistory.com/220) [IT is True:티스토리]|
메타 데이터  : https://namu.wiki/w/%EB%A9%94%ED%83%80%EB%8D%B0%EC%9D%B4%ED%84%B0#s-2.3
코드 예제 : https://docs.spring.io/spring-framework/reference/core/beans/basics.html
