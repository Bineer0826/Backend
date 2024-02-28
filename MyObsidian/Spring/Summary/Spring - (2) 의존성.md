#Spring #FrameWork 

> 앞에서 배운대로 팩토리 메서드 사용, 매개변수 지정하여 설정된 속성을 통한 객체 들은 종속성이 있다고 배웠다. 구체적으로 어떤 기능인지 알아보자.

## 의존성 주입(DI)
Dependency InJection
: 서로 연관이 있게끔 팩토리 메서드, 정적메서드 매개변수 지정 등 객체 인스턴스에 설정된 속성을 적용해서 다른 객체와의 연관성(종속성)을 정의하는 프로세스
<br>

- 코드가 깔끔해진다
- 종속성이 제공되면 분리가 쉽다
- 추상 클래스가 있는 경우 클래스를 테스트하기 쉽다
- 테스트에서 스텁 또는 모의 구현 이용
<br>
<br>

### 생성자 기반 주입
: [spring - (2) 스프링 container & bean] 에 설명한 것처럼 static 메서드를 이용하는 것과 같이 매개변수 지정으로  팩토리(최상위)의 메서드를 호출해 종속성을 주입하는 것
<br>

```java
public class SimpleMovieLister {

	// the SimpleMovieLister has a dependency on a MovieFinder
	private final MovieFinder movieFinder; //선언

	// a constructor so that the Spring container can inject a MovieFinder
	public SimpleMovieLister(MovieFinder movieFinder) {
	//매개변수에 MovieFinder movieFinder를 사용해서 simpleMovieLister에 종속관계 있다는 것을 보여줌
		this.movieFinder = movieFinder;
	}

	// business logic that actually uses the injected MovieFinder is omitted...
}

```

<br>
<br>
#### 생성자 일치 확인(인수 해결)
: 정말로 종속관계 있는 것들이 생성자로 사용할 수 있는지 확인하려면 유형을 이용하면 된다

- 타입을 정하지 않은 경우
```java
package x.y;

public class ThingOne {

	public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
	// one에는 2와 3이 종속되어있다
		// ...
	}
}

```

XML 에서는 `<constructor-arg/>`를 이용한다
```xml
<beans>
	<bean id="beanOne" class="x.y.ThingOne">
		<constructor-arg ref="beanTwo"/> // 2가 연관됨을 보임
		<constructor-arg ref="beanThree"/> //3이 연관됨을 보임
	</bean>

	<bean id="beanTwo" class="x.y.ThingTwo"/>

	<bean id="beanThree" class="x.y.ThingThree"/>
</beans>

```
<br>

아래의 예시는 두 인수가 같지 않게 분리하는 방법들이다

- 타입을 정한 경우 : `type`
```java
package examples;

public class ExampleBean {

	// Number of years to calculate the Ultimate Answer
	private final int years;

	// The Answer to Life, the Universe, and Everything
	private final String ultimateAnswer;

	public ExampleBean(int years, String ultimateAnswer) {
		this.years = years;
		this.ultimateAnswer = ultimateAnswer;
	}
}

```

xml에서 동일하게 나와있는 것을 볼 수 있다.
```xml
<bean id="exampleBean" class="examples.ExampleBean">
	<constructor-arg type="int" value="7500000"/> //int타입, 값지정
	<constructor-arg type="java.lang.String" value="42"/> //String
</bean>
```
<br>

- 타입을 정한 경우 : `index`
```xml
<bean id="exampleBean" class="examples.ExampleBean">
	<constructor-arg index="0" value="7500000"/> //0번째
	<constructor-arg index="1" value="42"/> // 1번째
</bean>
```

<br>

- 매개변수로 정하기 : 디버그 플래그를 사용하는 방법은 추후에...
```xml
<bean id="exampleBean" class="examples.ExampleBean">
	<constructor-arg name="years" value="7500000"/>
	<constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```
이러한 방법을 사용하면 동일한 유형의 두 인수를 갖는 모호성을 해결


<br>
<br>

### Setter 기반 주입
: static 메서드를 호출한 수 빈에서 setter메소드를 호출한다

```java
public class SimpleMovieLister {

	// the SimpleMovieLister has a dependency on the MovieFinder
	private MovieFinder movieFinder;

	// a setter method so that the Spring container can inject a MovieFinder
	public void setMovieFinder(MovieFinder movieFinder) {
		this.movieFinder = movieFinder;
	}

	// business logic that actually uses the injected MovieFinder is omitted...
}
```


## 세부 종속성 및 구성
## depends - on
## 지연 초기모드 Bean
## 자동연결모드
## 메서드 주입