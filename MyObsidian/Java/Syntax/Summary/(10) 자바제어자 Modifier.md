---
tags:
  - 자바완전정복
  - Java
  - Syntax
level: 개념
Linked Project: "[[Do it! 자바 완전 정복]]"
banner: https://velog.velcdn.com/images/bineer0826/post/e55a2a5c-5a02-4b1b-9390-cbd08a8a3945/image.jpeg
---
## 접근지정자
### 멤버 및 생성자의 접근지정자 + 사용범위

#### 1. public 
-> 동일 패키지의 모든 클래스 + 다른 패키지 모든 클클래스
#### 2. protected
-> 동일 패키지의 모든 클래스 + 다른 패키지의 <span style = "color:red">**자식**</span> 클래스
#### 3. default(=package) : 멤버 앞에 아무것도 표기 하지 않음
-> 동일 패키지의 모든 클래스
#### 4. private
-> 동일 클래스 가능


** 1->4번으로 갈수록 접근범위가 좁아진다 **
** ❗️접근지정자는 항상 붙어있다**
<br>

```java
package abc;
public class A{
	public int a;
    protected int b;
    int c;
    private int d;
    
    void abc();
    
    //a,b,c,d 사용가능
}
class B{
	//a,b,c 사용가능
}
```

```java
package bcd;
class C{
	//a 사용가능
}
class D extend A {

	//a,b 사용가능

}
```
<br>


---
### 클래스의 2가지 접근지정자 

#### 1. public
#### 2. default

```java
package abc.bcd;

public class E{

}
class A { //default 접근지정자

}

class B {
	void bcd(){
    	A a = new A(); // 같은 패키지 내에서 가능
    }

}
```
```java
package bcd.cde;
import abc.bcd.A;//불가능
import abc.bcd.E;//public이라 가능
class C { 
	// A클래스 사용 불가능
}
```
<br>

---
### 클래스 접근지정자 vs 생성자의 접근지정자

> 생성자가 없는 class를 만들면 컴파일러가 자동으로 생성자를 추가한다
그 생성자에는 앞에서 배운 내용대로**_ 1~4번 중 하나가 무조건 붙어있는다_
**
그렇다면 1~4번 중 어떤 것으로 지정할까?

#### 클래스의 접근지정자와 동일하게 붙여준다 -> 클래스와 동일 하게 1번 or 2번

- public
  ```java
  package abc.bcd;

  public class A {
      public A(){
      //1~4번 중 public 클래스 이므로 1번으로 동일하게 써준다
      //다른 클래스에서도 접근이 가능하다
      }
  }
  ```
  
  `A a = new A();`
  -> A a는 class가 public이라 가능한것이고 new A();는 임포트가 되어있다면 생성자가 public이라 가능한 것이다 (분리해서 생각!)

- default
  ```java
  package abc.bcd;

    class A {
         A(){
            //클래스가 default이므로 동일하게 써준다
            //다른 클래스에서 접근이 불가능하다
            //타 클래스에서 임포트도 안되고 객체생성불가
            }
    }
  ```

- 혼용
  ```java
  package abc.bcd;

      class A {
           public A(){
           //public이지만 default class라 임포트도 안되고 객채 생성도 불가하다.
        
	        }
      }
  ```
<br>
  
---
 
## static 키워드
 
>  원래 객체 생성(A a = new A();)를 해야 사용할 수 있었다면 객체 생성 없이 클래스내에서 바로 사용할 수 있게 해준다 (static영역에 저장)

### 필드

1. 객체생성없이 사용(출력)가능
2. static 필드의 객체 공유기능

`int m=3;` -> 인스턴스(객체object 내의) 필드(변수)
`static int n=5;` -> static 필드 / object 내에 있지 않음(슈붕이 아니어도 슈크림을 꺼내먹을 수 있다) 

- 바로 출력
```java
System.out.println(A.n);//5 Static필드로 생각
```

- 원래대로 객체생성 후 출력(지양)
```java
A a = new A();
System.out.println(a.n); //인스턴스 필드로 생각
```
-> 이렇게 되면 stack 메모리에서 a가 있는 heap메모리로 가서 n을 찾는데, 그 n은 객체없이 static영역에 있으므로 n=5의 결과를 내놓는다


### 메서드

1. 객체 생성 없이 사용가능
2. static 메서드의 공유기능

필드와 동일한 원리
```java
A a = new A();
a.abc();//인스턴스 메서드

A.bcd();//객체 생성 없이 바로 사용
A a = new A();
a.bcd; // 객체 생성 후 사용(지양)
```
<br>

### static 메서드 내의 static
: static은 객체 생성 전에 사용 가능한게 장점이다-> static 메서드 내에 인스턴스 필드와 this메서드는 불가능하다( 객체가 있어야 하기 때문!!)

```java
class A {
	int a; //인스턴스 필드
    static int b; 
    
    void abc(){
    //객체가 이미 생겼기 때문에 a,b,bcd(),cde() 사용가능
    }
    static void bcd(){
    //객체생성x
    // b,cde()
    }
    static void cde(){
    //객체생성x
    // b,bcd()
    }
}
```
❗️ 정적메서드 내부는 정적 멤버들만 가능
<br>
### static 초기화 블록
instance 필드의 초기화 위치 : 생성자
static 필드의 초기화 위치 : <span style = "color:skyblue">`static {}`</span> (생성자 호출없이 사용가능)

```java
class A {
	int a; 
    static int b; 

static {
	b=5;
    System.out.println("클래스가 로딩될 때 static block 실행");
} // 여기까지 static 가능

A() {
	a=3; //인스턴스 필드 초기화 위치
}
  
}
```

❗️`System.out.println(A.b);`
-> class A에 static int b가 실행하게 된다 static b가 가리키는 곳은 static 초기화 구문 실행되기 때문에  static 출력값이 먼저 나오고 그 후에 5가 출력된다.
<br>

### JVM에서의 main 메서드 실행
: A.java를 컴파일해서 A.class가 실행되면 jvm은 A.main()을 실행한다 -> 객체 생성없이 main()가 실행되기 때문에 static을 쓰는 이유가 된다

`public static void main(String[] ar){..}`

---
## final
### ⭐️final 필드, 지역변수
: 이름 대로 마지막 필드or지역변수이기 때문에 지정한 값을 바꿀 수 없다
#### 필드 : 선언과 값대입을 **동시에** 한 경우
  ```java
class A1 {
	int a = 3;
    final int b = 5; //불변
    A1() {
    }
}
  ```
메모리 내에서는 객체내에  b=5;가 생성되고 동시에 final영역에 복사가 된다

#### 지역변수 : 선언 후 생성자에 값대입한 경우
  ```java
class B {
    void bcd() {
    	int a=3;
        final int b=5;
    }
}
  ```
bcd 메서드 내에 있어서 Stack메모리에 저장되고 메서드 중괄호가 끝나면 final영역에 복사가 된다 

### final 메서드
: 오버라이딩이 안된다-> 마지막 메서드인데 또 복붙하면 의미가 없어지기 때문..!
```java
class A {
	void abc() {}
	final void bcd() {}
}

class B extends A {
	void abc() {}
	//void bcd() {} //상속시 오버라이딩 불가
}
```
<br>

### final 클래스
: 메서드와 같은 이유로 상속이 불가하다... 마지막인데 자꾸 복붙하고 상속 만들어내면 final의 의미가 없다.
<br>

---
## abstract
> 뒤에서 추상클래스로 사용 이유와 목적을 더 자세하게 배운다 그냥 이런게 있다 정도만 생각

### abstract 메서드
: 이름만 정의한 추상적인 미완성 메서드
-> 기능수행을 하지 않는다
-> 사용 목적 : 부모타입으로부터 기능을 정의하지 않는 메서드를 호출하기 위함 
```java
class Animal {
	void cry(){
    //이렇게 의미없는 코드블럭 대신에
    }
}
abstract class Animal {
	abstract void cry();
}
class Cat extends Animal {
	void cry(){
    	System.out.println("야옹");
    }
}
class Dog extends Animal {
	void cry(){
    	System.out.println("멍멍");
    }
}
```

### abstract 클래스
: 추상메서드(미완성메서드)를 하나 이상 포함하는 클래스
-> 추상 메서드와 동일하다

```java
abstract class Animal { //class앞에 abstract 붙인다
	abstract void cry();
}
```



---
**출처**

**참고문헌**


