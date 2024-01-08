---
tags: 
level: 
Linked Project: 
banner: 
---
> 쓰레드의 속성을 반복해서 봐야한다. 특히, 데몬설정!!

## Program vs Process vs Thread
### 컴퓨터 구조
CPU(고속), 메모리(용량 낮,속도 높), 하드디스크(용량 높, 속도 낮) => 컴퓨터의 부품

1. 프로그램 실행(cpu 사용)하려면 하드디스크의 내용을 메모리에 올려야된다(loading)
❗️cpu는 하드디스크과 직접 상대하지 않고 메모리와 주고받는다

2. 1번의 이유로 하드디스크에 프로그램이 설치가 되어있다(C:ProgramFiles/...) => <span style = "color:yellow">**Program**</span>

3. 1번처럼 loading이 된 프로그램 => <span style = "color:yellow">**Process**</span>
<br>

### Process 구조
엄밀히 말하자면, **cpu**와 메모리 속의 process 속의 **Thread**가 주고받는 것이다.

❗️Thread가 없는 process라는 것은 cpu를 사용하지 않는 프로그램인 것.... 그건 프로그램이 아님

쓰레드 1개만 주고 받으면, 단일 쓰레드 프로세스
쓰레드 여러개 주고 받으면, 멀티 쓰레드 프로세스


### 자바 프로그램의 Thread
#### Thread : CPU의 최소 단위

1. JVM의 할 일 : main()를 먼저 실행시키는 것

2. main()로 인해 main Thread가 생성됨
= 메모리 내에 process 내에 main Thread
= 초기에는 쓰레드 1개 실행

3. 이후에 main Thread 내에 여러 객체나 메서드(해당 Thread)가 생성되고 실행됨
= 멀티 쓰레드

Q. main() 내에 2개의 메서드가 실행되고 있다면, 몇개의 쓰레드가 존재하는 것인지?
A. 3개(main,2개 메서드의 thread) = 멀티 쓰레드
<br>

---

## Multi-Thread의 필요성
: 동시에 일을 수행하기 위함 but, 매번 속도가 빨라지는 건 아님

### 진행과정
#### 순차(Sequential) -> 단일 쓰레드 
: 하나 끝내고 다른 거 진행. abc(), bcd()가 있다면 abc() 진행 후 bcd()
#### 동시(Concurrency) -> 멀티 쓰레드
: CPU가 1개만 있을 때 교차하면서 진행=> 교차 시간이 너무 짧아서 동시처럼 보임
abc(),bcd(),abc(),bcd() ...

⭐️ 작업 수 > cpu 코어 수
#### 병렬(Parallelism) -> 멀티 쓰레드
: cpu가 여러 개 있을 때, 정말로 동시에 진행하고 속도도 빠르다
abc() -> 결과물 1
bcd() -> 결과물 2

⭐️ 작업 수 < cpu 코어 수


    Q1. 작업이 2개, 코어가 4개인 경우?
    A. 병렬, 속도 빨라짐
    Q1. 작업이 4개, 코어가 1개인 경우?
    A. 동시
    Q1. 작업이 6개, 코어가 2개인 경우?
    A. 동시, 병렬


**❗️Thread는 동시성과 병렬성을 둘 다 가진다**

<br>

---

## Thread 생성 & 실행
### 생성
비슷한 메커니즘으로 간다 클래스냐 인터페이스냐 익명이너클래스인지 택 1
### 실행
: `start()`메서드 호출

#### 방법 1) Thread class 상속 후 run() 메서드 재정의
- 클래스 정의
: thread class를 상속받아 run()오버라이딩 or 익명클래스이용
  ```java
  class MyThread extends Thread{
      @override
      public void run() {
          //쓰레드 작업내용
      }
  }
  ```
- 객체 생성 :  `A a = new A();` 형태 그대로
  ```java
  Thread myThread = new MyThread();
  or
  MyThread mythread = new MyTHread();
  ```
- thread 실행 : start이용
 ```java
 myThread.start();
  ```

 
 
 ❗️run()실행이 아닌 start()를 실행하는 이유는
_**새로운 쓰레드 생성이나 추가를 위한 모든 준비 +  run() 실행**_ 하기 위함

 ❗️run()은 주로 단일 쓰레드로 이용



#### 방법 2) Runnable interface 구현(abstract run()메서드 구현) 후 Thread 생성자로 Runnable 객체 전달


- 인터페이스 구현
  ```java
  class VideoRunnable implements Runnable {
      @override
      public void run() {
          //run()에 다가 직접 내용을 넣어서 구현시킴
      }
  }
  ```

- main()에 runnable, thread 객체 생성
  ```java
  public static void main(String[] ar) {
  //runnable 생성
  Runnable VideoRunnable = new VideoRunnable();
  
  //Thread 생성
  Thread myThread = new Thread(VideoRunnable);
  }
  ```

- thread 실행
  ```java
  myThread.start();
  ```

#### 방법 3) 익명이너클래스 이용

  ```java
  public static void main(String[] ar) {
  Thread myThread = new Thread(VideoRunnable() {
      pulic void run() {
          //내뇽	
          }

      });
  }
  ```

<br>

----
## Thread의 속성 5가지

### 1. 쓰레드 객체 참조(정적메서드)

: **현재 Thread** 객체를 참조하고 참조 객체가 없는 경우, thread 클래스의 정적메서드를 이용

`static Thread Thread.currentThread()`

### 2. 쓰레드 개수 확인하기

: **실행 중인** Thread의 개수, 동일하게 정적메서드를 이용

`static int Thread.activeCount()`
-> 같은 쓰레드 그룹 내의 active thread 개수


### 3. 이름 설정 및 가져오기

: thread 클래스의 인스턴스 메서드를 이용

이름 설정 : `String.setName(String name)`
이름 가져오기 : `String.getName()`

❗️앞의 예문들처럼 따로 쓰레드 이름을 설정하지 않는 경우, 컴파일러가 thread-0,...,thread-N 처럼 이름을 자동으로 부여한다

### 4. 우선 순위

: cpu 작업 중 동시성으로 교차로 작업을 진행한다면, **우선순위가 높은것을 더 많은 범위로**(많은 시간을 들여서) 작업을 진행함
+Thread의 인스턴스 메서드를 이용

`int getPriorty ()`
`void setPriority (int priority)`

가장 높음 : `Thread.MAX_PRIORIRY`
기본값 : `Thread.NORM_PRIORIRY`
가장 낮음 : `Thread.MIN_PRIORIRY`

### 5. 데몬 설정

`void setDeamon(boolean on)` -> 인스턴스메서드 이용
❗️ 반드시 start() 전에 사용해야 함
#### 일반 쓰레드(디폴트)
: main쓰레드가 실행 완료 후, 안에 다른 thread 작업중일 때, 작업 중인 thread가 끝나야 프로그램이 종료
#### 데몬 쓰레드
: main쓰레드가 실행 완료 후, 안에 다른 thread 작업 중이어도 프로그램 종료
❗️main 쓰레드를 포함해서 모든 일반 쓰레드가 종료해야 함께 종료됨

<br>

---
## Thread 동기화 (synchronized)
### 동기화
:하나의 작업이 완전히 완료된 후 다른 작업 수행, 한번에 한 개의 일만 실행 = ㅏ동기식
(<->비동기식 : 완료와 상관없이 명령에 의해 다른 작업 수행)

#### 동기화의 필요성
두 개의 thread가 하나의 객체를 공유할 때 필요
![](https://velog.velcdn.com/images/bineer0826/post/3d74ffd7-6812-45f9-a15c-c8e752c5cade/image.png) (완전정복 자바 중...)


-> 작업 1과 2는 더하기를 수행하는 작업이고 어떤 객체의 값이 3이라면,,,,
작업 1이 3에서 4로 변하는 대기시간에 작업 2가 그사이에 실행하면 객체의 값이 3인 상태이므로 작업 2도 3에서 4로 변하게 된다. **즉, 작업 1과 2는 같은 객체를 공유해서 같은 값이 나온다는 문제**가 된다.

-> 동기화상태가 아니라면 작업 1이 3->4 변한 후에 객체의 값이 4로 재정의되고 작업 2가 4->5로 변환시키므로 그 객체의 최종 값은 5가 된다

#### 문제점 해결
해결 : 같은 객체를 수행작업을 하나의 작업 끝내고 그 다음 작업을 시행하게끔한다 -> 하나의 작업이 실행될때는 lock을 걸어놓는다
<br>

### 동기화 사용방법

#### 방법 1) 메서드 동기화
: 동시에 두 개의 thread가 동기화 메서드를 사용하지 못하도록 하고 그래서 동기화 문제점을 해결한다

```java
접근지정자 synchronized 리턴타입 메서드명(입력매개변수){
	//동기화가 필요한 코드
}
```
-> 메서드를 실행하러 올때 이미 작업이 진행 중이면 대기 시켜놓는 기능이 있다(synchronized)

```java
class MyData{
	int data = 3;
    public synchronized void plusData(){
    //데이터를 바로 가져와 2초 뒤에 결과값 저장
    int mydata = data;
    try{ Thread.sleep(2000);}
    }
    catch(InterruptedException e){}
    data = mydata+1;
}
```

#### 방법 2) 블록 동기화(block)
: 동시에 두 개의 thread가 동기화 블록을 사용하지 못하게 하고 이로 인해 동기화 문제점을 해결한다

```java
synchronized (임의의 객체) {
	//동기화가 필요한 코드
}
```
```java
class MyData{
	int data = 3;
    synchronized(this) {
    //데이터를 바로 가져와 2초 뒤에 결과값 저장
    int mydata = data;
    try{ Thread.sleep(2000);}
    }
    catch(InterruptedException e){}
    data = mydata+1;
}
```

⭐️ **모든 객체는 단 하나의 <span style = "color: yellow">자물쇠(lock기능)</span>를 가진다 또한, 여러 동기화 블럭이어도 동일한 키가 필요하다
동기화 될 때 처음 사용하는 thread가 해당 객체의 <span style = "color: yellow">key(열쇠기능)</span>를 가진다
그래서, 먼저 실행 중인 thread가 key를 반납할 때까지 다른 thread는 대기상태!
쉽게 말해, 동기화는 해당 메서드나 부분을 자물쇠로 잠구는 행위라고 볼 수 있다
**

---
## Thread 상태

>new(객체 만들기) ---> Runnable(cpu가 사용상태) ---> Terminated
T t = new t / t.start()--->(실행대기와 실행)--->run()완료


#### Runnable 내에서는,
- 양보 : Thread.yield()
#### Runnable이 일시정지 상태일 때,
- Timed Waiting : Thread.sleep(1000), join()-> 1초 딜레이 / interrupt() -> 시간종료
- Blocked : 사용객체 lock/unlock
- Waiting : join()(대기),waiting()대기 / notify(), notifyAll() 

### 상태값 가져오기
: 인스턴스 메서드를 이용
`Thread.State getState()`
```
State state = myThread.getState();
switch(state){
	case Thread.state.NEW:
    ...
}
```
<br>

### NEW
: new를 통해서 thread의 객체 생성된 상태(start()전)
<br>

### RUNNABLE
: start() 이후 CPU를 사용할 수 있는 상태 -> 동시성에 의해 실행과 실행대기를 교차함
- **Thread.yield()** : 다른 쓰레드에게 cpu사용을 양보하고 자신은 실행대기 상태로 전환


#### TIMED_WAITING
- ** sleep(time)** : sleep(시간)을 호출한 쓰레드가 설정한 시간동안 일시정지 상태를 유지
`static void sleep(long millis)`
<br>
- **join(time)** : thread.join(시간)을 호출하면 지정시간동안 thread가 cpu를 사용하며 호출한 쓰레드는 일시정지 상태 = cpu를 중간에 가져가는 것
`void join(long millis)`

<br>
❗️<span style = "color:yellow">sleep과 join은 CPU사용할 수 없음</span>, 정해진 시간이 종료되거나 interrupt()를 발생해서 RUNNABLE상태로 되돌아가야 한다

- **interrupt()** : interruptedException 예외를 발생시켜서 일시정지 상태를 탈출 = 일시정지 시간 종료
`void interrupt()`
<br>

#### BLOCKED = 동기화파트
- ** Lock **: 동기화 메서드 or 블록으로 lock이 걸린 경우, 사용 객체가 lock이 된다
- ** Unlocked** : 동기화 메서드 블록이 unlock인 경우, 사용객체가 unlock.

❗️ 일정시간 일시정지 상태이므로 cpu 사용불가, 각 **쓰레드가 key를 획득하면 runnable 상태로 전환 = 동기화내용과 동일**

#### WAITING


- **wait()** : <span style = "color:yellow">**object메서드**.</span>   wait()를 호출한 메서드는 일시정지 상태가 되며 다른 쓰레드가 notify() / notifyAll()로만 일시정지에서 벗어날 수 있음
`void wait()`

❗️ wait() 다음 줄부터 실행되고 동기화 메서드와 블록 내에서만 사용 가능
<br>
- **join()** : join시 시간을 주지 않으면 thread가 종료할 때까지 호출했던 쓰레드는 일시정지한 상태

- **notify()** : <span style = "color:yellow">**object메서드**.</span> **하나의 쓰레드를 탈출**
- **notifyAll()** : <span style = "color:yellow">**object메서드**.</span> wait() 중인 **모든 thread를 탈출**

❗️  동기화 메서드와 블록 내에서만 사용 가능
<br>

### TERMINATED
: run()의 작업 내용이 모두 완료되어 thread가 종료된 상태
❗️한 번 실행된 thread는 재실행 불가-> 새롭게 객체 생성해야 함




---
**출처**

**참고문헌**


