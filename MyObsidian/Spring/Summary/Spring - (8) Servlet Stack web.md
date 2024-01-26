#Spring #FrameWork 

# Web MVC
: Servlet API 기반으로 구축된 웹 프레임워크이고 Spring 처음부터 포함됨.

>MVC와 병행해서 스프링 5.0에서는 Spring WebFlux의 반응형 스택 웹을 도입하게 되었다. 이번에는 서블릿 스택 웹을 알아보자
<br>
<br>

## DispatcherServlet
> MVC는  Servlet에 위치한 중앙컨트롤러 패턴을 중심으로 설계되었다.  

: 요청에 대해 처리하는 공유 알고리즘을 제공하고 의존하는 구성요소에 의해서 수행해 유현하고 다양한 워크플로우를 제공한다
이렇게 보면 매우 추상적이므로 아래의 기능들로 정의를 유추해보면,,,
<br>
- 기본적으로 Servlet 기반이기 때문에 Servlet java 구성을 사용하거나 사양에 따라 선언되고 매핑 되어야 한다
- xml에서는 `web.xml`
<br>
==즉, 스프링을 사용하면서 요청에 대한 매핑, 해결, 예외처리 등 필요한 위임 구성요소를 검색하는게 DispatcherServlet이라고 볼 수 있다.==

<br>
Servlet 컨테이너에 의해 자동으로 감지(매핑)되는 DS를 등록하고 초기화하는 에이다.
```java
public class MyWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {

		// Load Spring web application configuration
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();//configuration 으로 객체 생성
		context.register(AppConfig.class);// 클래스 등록

		// 디스패쳐서블릿 만들고 등록하기
		DispatcherServlet servlet = new DispatcherServlet(context); // 객체 생성
		ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet); // 매핑 이름설정(등록)
		registration.setLoadOnStartup(1);
		registration.addMapping("/app/*"); // 매핑주소
	}
}
```
<br>

xml에서는....
```xml
<web-app>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/app-context.xml</param-value>
	</context-param>

	<servlet>
		<servlet-name>app</servlet-name> //서블렛 매핑 이름
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value></param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>app</servlet-name>
		<url-pattern>/app/*</url-pattern>
	</servlet-mapping>

</web-app>
```
<br>
<br>

### Context 계층
#### WebApplicationContext
:  앞에서 배운  ApplicationContext를 확장한 것 + 서블릿 기반의 웹 애플리케이션 활용
[[Spring - (2) 2. Java 기반 컨테이너 구성]] 참고
<br>

- ApplicationContext 기능
-  웹 환경 지원 : 빈 설정을 처리하는 기능
- 서블릿 컨텍스트와 연동 : 생명주기 조율
- DispatcherServlet과 연관 : 웹 컨트롤러, 뷰를 관리함
- 웹에 특화된 빈 설정 : `Root WebApplicationContext`은 공유해야하는 데이터 저장소나 비즈니스 서비스 같은 bean을 포함한다
- scope지원 : 빈 생성과 관리

<br>
<br>
[context 계층구조](https://docs.spring.io/spring-framework/reference/_images/mvc-context-hierarchy.png)
<br>
```java
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { App1Config.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/app1/*" };
	}
}
```

<br>
<br>
<br>

### 특수 Bean
: 웹에서 dispatcherSevlet 요청을 처리하고 응답을 렌더링 하기 위한 프레임워크 계약을 구현하기 위한 bean

| 종류 | 설명 |
| ---- | ---- |
| HandlerMapping | 사전 & 사후처리를 위한 인터셉터 목록과 함께 핸드러에 매핑 |
| HandlerAdapter |  |
| HandlerExceptionResolver |  |
| ViewResolver |  |
| LocaleResolver / LocaleContextResolver |  |
| ThemeResolver |  |
| MultipartResolver |  |
| FlashMapManager |  |