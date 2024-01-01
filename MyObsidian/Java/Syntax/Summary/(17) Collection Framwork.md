---
tags:
  - Java
  - Syntax
  - 자바완전정복
level: 개념
---


>다양하게 자바에서 사용할 수 있는 프레임워크를 알아보자

## 개념 & 구조

**컬렉션** : 동일한 타입을 묶어서 관리하는 자료구조
- 저장공간의 크기(capacity)를 동적으로 관리
- 크기 변경 가능

	❗️ 배열도 동일한 타입만 묶어서 저장이 가능하지만 크기를 지정하고 추후에 변경이 불가능하다

**프레임워크** : 클래스와 인터페이스의 모임 (라이브러리)
- 클래스 정의에 설계의 원칙or구조가 존재

**컬렉션 프레임워크** : 리스트, 스택, 큐, 트리 등의 **자료 구조**에 정렬 탐색 등의 **알고리즘을 구조화 해놓은 프레임워크**


![](https://velog.velcdn.com/images/bineer0826/post/bc9975d6-1400-45a6-bd75-819fa21d25bb/image.png)


<br>


---
## List< E >

: 배열처럼 수집한 원소를 인덱스로 관리하는 인터페이스
-> 구현한 클래스 : ArrayList< E > , Vector< E >, LinkedList< E >

### 배열 vs 리스트

**배열에서는**,
필요하지 않은 배열 값의 저장공간을 지울 수 없다
```java
String[] array = new String[]{"가","나","다","라","마","바","사"};
//저장공간 7
array[2] = null; //다 삭제
array[5] = null; //바 삭제
System.out.println(array.length); // 7
SyStem.out.println(Arrays. toString(array)); //[가,나,null,라,마,null,사]
```

❗️**Arrays. toString(array)** = 배열 객체를 한번에 출력하는 메서드



**리스트에서는**,
String 타입만 있는 제네릭타입이고, remove를 이용해서 저장공간을 지울 수 있다.
```java
List<String> aList = new ArrayList<>(); //저장공간 0 -> 데이터가 없으므로
aList.add("가");aList.add("나");aList.add("다");
aList.add("리");aList.add("마");aList.add("바");
aList.add("사");
//저장공간 7


aList.remove("다");
aList.remove("바");
System.out.println(aList.size()); //저장공간 5
System.out.println(aList); //[기,나,라,마,사]
```

❗️**Sys~(aList); = 리스트 객체를 한번에 출력하는 메서드**
모든 컬렉션 객체(리스트,큐등등)는 toString()메서드를 오버라이딩 해놓았다. 왜냐하면, 최상위클래스의 메서드이기 때문이다



### 공통 객체 생성
#### 방법 1) 구현클래스 생성자로 동적컬렉션 생성

- 데이터 추가 / 삭제가 가능
- 기본생성자는 원소 10개를 저장할 수 있는 용량을 확보하고 원소가 많아지면 저장공간을 자동으로 추가
- 단, LinkedList는 미리 저장공간 지정 불가능

```java
List<Integer> aList2 = new ArrayList<Integer>(30); 
//capacity(30) = 공간이 30
```


#### 방법 2) Arrays.asList(T..)메서드를 이용하여 정적컬렉션 생성

- 데이터 추가 / 삭제가 불가능 -> Array를 이용하기 때문
- 데이터 변경은 가능
- 고정된 데이터를 저장하고 활용할 때 유용!

```java
List<Integer> aList1 = Arrays.asList(1,2,3,4); //고정된 데이터
aList1.set(1,7); // [1,7,3,4]
aList1.add(5);
//UnsopportedOperationException 오류
aList1.remove(3);//오류
```
<br>

### List< E > 공통 주요 메서드
구현클래스 모두 아래의 메서드를 포함한다

| 구분 | 리턴 타입 | 메서드이름 | 기능 |
|---|---|---|---|
|데이터 추가|boolean|add(E element)|매개변수로 입력된 원소를 리스트 마지막에 추가 -> 해당 데이터 옆의 기존 데이터는  한 칸씩 밀려나간다|
||void|add(int index,E element)|index 위치에 입력된 원소 추가|
||boolean|addAll(Collection< ? Extends E > c)|매개변수로 입력된 컬렉션 전체를 마지막에 추가|
||boolean|addAll(int index,Collection< ? Extends E > c)|index위치에 입력된 컬렉션 전체를 추가|
|데이터 변경|E|set(int index,E element)|index 위치의 원소값을 입력된 원소로 변경|
|데이터 삭제|E|remove(int index)|index 위치의 원소값 삭제|
||boolean|remove(Object o)|원소 중 매개변수 입력과 동일한 객체 삭제|
||void|clear()|전체 원소 삭제|
|리스트 데이터 정보 추출|E|get(int index)|index위치에 원소값을 꺼내서 출력|
||int|size()|리스트 객체 내에 포함된 원소의 개수(데이터개수) 출력|
||boolean|isEmpty()|리스트의 원소가 하나도 없는지 판단여부를 출력|
|리스트-> 배열로 변환|Object[]|toArray()|리스트를 object 배열로 전환|
||T[]|toArray(T[] t)|입력매개변수로 전달한 내가 원하는 타입의 배열로 전환|
<br>
<br>

 


### ArrayList< E >

: List< E >인터페이스를 구현한 구현 클래스

- 데이터추가
```java
List<Integer> aList1 = new ArrayList<Integer>();

//add(E element)
  aList1.add(4);
  aList1.add(8);
  aList1.add(6);
  System.out.println(aList.toString());
//[4,8,6]

//add(int index, E element)
  aList1.(1,7);
  System.out.println(aList.toString());
//[4,7,8,6] -> 한 칸씩 밀린다


List<Integer> aList2 = new ArrayList<Integer>();

//addAll(Collection< ? extends E> c)
  aList2.add(5);
  aList2.add(6);
  aList2.addAll(aList1);//aList1을 통째로 추가
  System.out.println(aList2.toString());
  //[5,6,4,7,8,6]

//addAll(int index, Collection< ? extends E> c)
  aList 2.addAll(1,aList1);
  System.out.println(aList2.toString()); 
//[5,4,7,8,6,6] 특정위치에 aList1 추가

```

- 데이터 변경
```java
List<Integer> aList3 = new ArrayList<Integer>();
aList3.add(8);
aList3.add(0);
//[8,0]

//set(int index, E element)
  aList3.set(1,2);
  aList3.set(0,9);
  System.out.println(aList3); //[2,9]

aList3.add(8);
aList3.add(0);//[2,9,8,0]
```

- 데이터 삭제
```java
//remove(int index)
  aList3.remove(1);//1번째를 지워라
  System.out.println(aList3);//[2,8,0]

//remove(object o)
  aList3.remove(new Integer(2));//값 2를 지워라
  System.out.println(aList3); //[8,0]
  
//clear()
  aList3.clear();
  System.out.println(aList3);//[]

```

- 데이터 정보 추출
```java
//isEmpty();
	System.out.println(aList3.isEmpty);
    //true : 데이터가 없으므로 참

//size();
  aList3.add(3);
  aList3.add(6);
  aList3.add(9);
  System.out.println(aList3.size());
  //3
  
//get(int index)
  System.out.println(aList3.get(2)); 
  //값 9
  System.out.println(aList3.get(0));
  //값 3

```

- 리스트 -> 배열 변환
```java
//toArray()
	Object[] object = aList3.toArray();	
    System.out.println(Arrays.toString(object));
    //오브젝트 배열[3,6,9]
    
//toArray(T[] t)
  Integer[] integer1 = aList3 toArray(new Integer[1]); //데이터 크기 1
  System.out.println(Arrays.toString(integer1));
  //[3,6,9] -> 데이터크기가 1이지만 알아서 3으로 만들어서 배열로 리턴한다

    
//toArray(T[] t)
  Integer[] integer2 = aList3 toArray(new Integer[5]);
  System.out.println(Arrays.toString(integer2));
  //[3,6,9,null,null] -> 5개의 데이터 배열을 만든다 하지만 3,4번째는 데이터값이 없으므로 integer의 초기값 null이 입력

```
<br>

### Vector< E >
#### ArrayList< E >  vs  Vector< E >

공통점(리스트 공통사항)
- 동일한 타입의 객체 수집
- 메모리 동적 할당
- 데이터의 추가, 삭제, 변경 등 메서드 제공

**차이점**
- **모든 메서드가 동기화 메서드로 구현되어있음**
- **멀티 쓰레드에 적합함**

데이터 추가, 변경 삭제, 다 위와 같다
<br>
<br>


### LinkedList< E >
#### ArrayList< E >  vs  LinkedList< E >

공통점(리스트 공통사항)
- 동일한 타입의 객체 수집
- 메모리 동적 할당
- 데이터의 추가, 삭제, 변경 등 메서드 제공

차이점
- 디폴트 저장공간(10으로만) 사용 -> 생성자로 저장공간 크기 지정 불가
- ⭐️ 데이터 내부 저장방식이 index( 몇번째)를 이용하지 않음 
- 앞뒤 **객체의 위치 정보를 저장**
ex) [7,6,2,3,1]이면, 6의 양 옆에는 7과 2가 있다는 위치정보를 알려준다



---
###### 출처

###### 참고문헌
1. ㅁㄴㅇ

