---
tags:
  - 자바완전정복
  - Java
  - Syntax
level: 개념
Linked Project: "[[Do it! 자바 완전 정복]]"
---

> 어떤 상품에 마다 클래스를 추가하는 것은 비효율적이다. 그래서 클래스 1개만 만들고 각각 다른 상품들을 저장하고 관리할 수 없을까?
> 그것에 대한 해답은 제네릭!!!

위와 같은 질문으로 Object 클래스를 이용해 해결하는 방법도 있다. 

```java
Goods goods1 = new Goods();
goods1.set(new Apple());// 사과저장
Apple apple = (Apple)goods1.get();
// 원래는 오브젝트클래스라 오브젝트 객체타입인데, 다운캐스팅을 통해 사과 객체타입으로 꺼낸다
..
//같은 방법으로 연필도 저장

Goods goods3 = new Goods();
goods3.set(new Apple());// 사과저장
Pencil pen = (Pencil)goods3.get();
//사과저장한 걸 꺼내야 하는데
//연필을 꺼내버리면 오류가 난다 = ClassCastException
```

오류가 나타날 때, runtimeException이므로 컴파일이 오류검사를 안하고 종료시켜버린다
이것을 **약한 타입체크** 라고 하고 이러한 문제점이 생긴다..!

이 모든 것의 해결책이 제네릭 타입이다
<br>

---
## Generic 클래스&인터페이스
### 정의
정해지지 않는 제네릭 타입 변수를 영대문자 하나로 사용한다
#### 제네릭 타입 변수명 1개
```java
접근지정자 class 클래스명<T> {
	// 타입 T를 사용한 코드
}
```
```java
접근지정자 interface 클래스명<T> {
	// 타입 T,K를 사용한 코드
}
```

#### 제네릭 타입 변수명 2개
```java
접근지정자 class 클래스명<T,K> {
	// 타입 T를 사용한 코드
}
```
```java
접근지정자 interface 클래스명<T,K> {
	// 타입 T,K를 사용한 코드
}
```
### 객체 생성
: 제네릭 타입으로 지정하지 않으면 object클래스로 인식하기때문에 꼭 제네릭타입을 입력해준다
= 클래스에 정하지 않은 타입을 **객체 생성시에 정의하겠다는 것**

❗️실제 타입을 정해서 작성한다
```java
클래스명<실제제네릭타입> 참조변수명 = new 클래스명<실제제네릭타입>();
or
클래스명<실제제네릭타입> 참조변수명 = new 클래스명<>();
//생성자의 경우, 타입생략가능
```

<br>

---
## generic 메서드
: 리턴타입 또는 매개변수 타입을 제네릭타입으로 선언하고 선언 할 때 타입을 지정함

### 정의
#### 제네릭 타입변수 1개
```java
public <T> T method1(T t) {
	//타입 T를 사용한 코드
}
```

#### 제네릭 타입변수 2개
```java
public <T,V> T method1(T t,V v) {
	//타입 T, V를 사용한 코드
}
```

#### 매개변수만 제네릭 적용
```java
public <T> void method1(T t) {
	//리턴타입은 void로 매개변수만 제네릭적용
	//타입 T를 사용한 코드
}
```

#### 리턴타입만 제네릭 적용
```java
public <T> T method1(int a) {
	// 리턴타입 T, 매개변수는 int a로 리턴타입만 제네릭적용
	//타입 T를 사용한 코드
}
```
<br>

### 메서드 호출

`참조객체.<제네릭타입>메서드이름(매개변수);`
or 매개변수타입으로 제네틱타입유추 가능한 경우엔 생략
`참조객체.메서드이름(매개변수);`

```java
public <T> T method1(T t) {
	return t;
}
//t타입으로 유추가 가능하다

참조객체.method1(100);
```
<br>

### 제네릭 메서드 내에서 사용가능한 메서드

정의 시점에서 모든 타입이 가능하기 때문에 모든 것의 최상위, **object 클래스의 메서드만** 가능하다

```java
class A {
	public <T> void method1 (T t) {
    //어떤 타입이 들어올지 모름
    }
}
```
만약 다른 메서드를 쓰고 싶다면 제네릭 타입의 범위를 제한해야한다


## 타입 범위 제한(bound)

앞서 말한 것처럼 어떤 타입이 올지 모르니까 내부에서는 object 메서드만 사용가능하게 되고 기능이 너무 **한정적**이게 된다

그래서 **Number class(char, boolean을 제외한 타입의 부모클래스)나 하위클래스(Integer, Double 등)만 가능**하다고 기능 추가가 되었다

#### 방법 1) 제네릭 클래스의 타입제한
`접근지정자 class 클래스명<T extends 제한하는 클래스명> { // T를 사용한 코드
}` 
-> 클래스/인터페이스 상관없이 extends
  ```java
  class Goods<T extends Fruits>{
      private T t;
      public T get() {
          return t;
      }
      public void set(T t) {
          this.t = t;
      }
  }
  ```

#### 방법 2) 제네릭 메서드의 타입제한
`접근지정자 <T extends 부모클래스명> T 메서드이름(T t) {// 부모클래스의 메서드 사용가능
}`

  ```java
  class GenericMethods {
      public <T extends String> void method(T t) {
          char c = t.chatAt(0);//string메서드 사용가능
      }
      System.out.println(c);
  }
  ```
#### 방법 3) 일반메서드 매개변수로서의 제네릭 클래스 타입제한

  1. `접근지정자 메서드이름(제네릭클래스명<제네릭타입명> ) {...}` -> method(Goods< B >) : B만 가능
  
  2. `접근지정자 메서드이름(제네릭클래스명<?> ) {...}` -> method(Goods< ? >) : A,B,C,D 가능
  
  3. `접근지정자 메서드이름(제네릭클래스명<? extends 상위클래스> ) {...}` -> method(Goods< ? extends B >) : B,C,D 가능

  4. `접근지정자 메서드이름(제네릭클래스명<? super 하위클래스> ) {...}` -> method(Goods< ? super B >) : A,B 가능
<br>


---

## Generic 상속
### 제네릭 클래스 상속
: 부모가 제네릭이면 자식 클래스도 제네릭변수를 물려받음 , 자식클래스의 제네릭 타입 개수는 부모보다 같거나 클 수 있음

`class parent<K,V> {...}`
-> `class Child<K,V> extends Parent<K,v>`
-> `class Child<K,V,T> extends Parent<K,V>`

### 제네릭 메서드 상속
:부모클래스가 제네릭메서드 가지면 자식도 제네릭 메서드를 그대로 물려받는다
동일하다


---


**출처**

**참고문헌**


