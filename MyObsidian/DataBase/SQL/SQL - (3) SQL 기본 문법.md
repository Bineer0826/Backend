#혼공S #SQL #FrameWork 

> 문법은 늘 그렇듯 활용을 100% 활용하도록 해보자
> 다음 예시는 MySQL의 기능으로 사용하지 않고 SQL 문법을 이용한 결과이다(결과는 동일)

## SQL 문법 사용

`SELECT 열이름 FROM 테이블이름 WHERE 조건식`
: 구축이 완료된 테이블에서 데이터를 추출하는 기능
- 추출 기능이라 데이터 변경 X
<br>

앞에서 배운 것처럼,
==**데이터베이스 만들기 -> 테이블 만들기 -> 데이터 입력 -> 데이터 활용**== 순서로 진행하면 된다

### 데이터 베이스 만들기

- `DROP DATABASE IF EXISTS market_db;` 
DROP DATABASE는 market_db를 삭제하는 문장. -> 기존의 데이터가 열려있는 경우 삭제함

- `CREATE DATABASE market_db;`
CREATE DATABASE 는 market_db를 생성하는 문장
<br>

### 테이블 만들기

#### 회원 테이블

- USE 문 : 데이터 베이스를 사용하려고 선택하는 문장 = 데이터베이스를 더블클릭으로 활성화하는 것과 같은 기능

```sql
USE market_db;
CREATE TABLE member -- 회원 테이블
( mem_id  		CHAR(8) NOT NULL PRIMARY KEY, -- 사용자 아이디(PK)
  mem_name    	VARCHAR(10) NOT NULL, -- 이름
  mem_number    INT NOT NULL,  -- 인원수
  addr	  		CHAR(2) NOT NULL, -- 지역(경기,서울,경남 식으로 2글자만입력)
  phone1		CHAR(3), -- 연락처의 국번(02, 031, 055 등)
  phone2		CHAR(8), -- 연락처의 나머지 전화번호(하이픈제외)
  height    	SMALLINT,  -- 평균 키
  debut_date	DATE  -- 데뷔 일자
);
```
<br>

#### 구매 테이블

- CREATE로 구매 테이블 만들기
- AUTO_INCREMENT : 자동으로 숫자를 입력해주는 기능(리스트)

```sql
CREATE TABLE buy -- 구매 테이블
(  num 		INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 순번(PK)
   mem_id  	CHAR(8) NOT NULL, -- 아이디(FK)
   prod_name 	CHAR(6) NOT NULL, --  제품이름
   group_name 	CHAR(4)  , -- 분류
   price     	INT  NOT NULL, -- 가격
   amount    	SMALLINT  NOT NULL, -- 수량
   FOREIGN KEY (mem_id) REFERENCES member(mem_id)
);
```
<br>

### 데이터 입력하기

- INSERT 문 : 데이터 값을 설정하는 것
- 회원은 member에, 구매는 buy에 값을 입력함
- 위에 테이블 만들대로, 순서대로 넣는다
- buy 테이블에서 NULL은 위에서 AUTO_INCREMENT 이기 때문에 null로 적어주면 됨

```sql
INSERT INTO member VALUES('TWC', '트와이스', 9, '서울', '02', '11111111', 167, '2015.10.19');

INSERT INTO buy VALUES(NULL, 'BLK', '지갑', NULL, 30, 2);

```
<br>

### 데이터 조회하기
- 각각의 테이블을 선택하여 보여준다 ( * 는 전체를 의미한다)
```sql
SELECT * FROM member;
SELECT * FROM buy;
```
<br>
<br>

----
## SELECT 문
### USE
: 데이터 베이스를 지정 또는 변경하는 형식

`USE 데이터베이스_이름;` => ex) `USE market_db`

❗️use문 이후에 select문을 이용해서 테이블을 불러오기 위해서는 데이터베이스 내에 해당 테이블이 있어야 한다
<br>

### 기본 조회하기 : SELECT와 FROM
 
형식 : `SELECT 열 이름 FROM 테이블 이름`
- * 는 모든 것을 의미 
- 테이블 이름은 주로 `데이터베이스_이름.테이블_이름` => ex) `market_db.member` = 마켓 데이터베이스에 회원 테이블

❗축약형으로 테이블_이름만 사용한다
<br>

ex 1) 기본 구문
```sql
use market_db;
select * from member(market_db.member);
```
member테이블에서 모든 데이터를 조회한다는 문장이다
<br>

ex 2) 필요한 열만 가져오기 or 여러개의 필요한 열만 가져오기(,로 구분한다)
```sql
select mem_name from member;
select mem_name, mem_id, number from member;
```

![[스크린샷 2024-02-16 오후 3.32.40.png]]
<br>
![[스크린샷 2024-02-16 오후 3.36.37.png]]

<br>
<br>

### 특정 조건만 조회하기 : SELECT ~ FROM ~ WHERE

WHERE 절 : 조회하는 결과에 특정한 조건을 추가해서 원하는 데이터만 보고싶을 때 사용

형식 : `SELECT 열_이름 FROM 테이블_이름 WHERE 조건식;`
or
```sql
SELECT 열_이름
	FROM 테이블_이름
	WHERE 조건식;
```
<br>
- `열_이름 = 값` : 열의 값에 해당하는 결과만 보여줌
- 문자형 CHAR의 경우 '~'로 묶어준다
<br>

 ex 1) 전체 데이터베이스 중 회원 블랙핑크를 조회하기
 `select * from member where mem_name = '블랙핑크';`
![[스크린샷 2024-02-16 오후 3.51.07.png]]
<br>

ex 2) 전체 데이터베이스 중 인원이 4명인 회원 조회하기
`select * from member where mem_number = 4;`
![[스크린샷 2024-02-16 오후 3.54.37.png]]
<br>
<br>

#### 관계연산자와 논리연산자로 범위 설정
형식 : `where 특정범위 관계연산자 값`
조건식이 여러 개일 경우 : `where 조건식 논리연산자 조건식`
 <br>
 <br>
1. BETWEEN ~ AND
ex 1) AND 이용한 평균 키가 163~165인 회원 조회 : 조건식을 두 번 써야함
```sql
select mem_name, height
	FROM member
	WHERE height >= 163 AND height <= 165; 
```
<br>
ex 2) between과 and : 조건식을 한 번만 쓴다
```sql
select mem_name, height
	FROM member
	WHERE height BETWEEN 163 AND 165; 
```
![[스크린샷 2024-02-16 오후 4.18.02.png]]
<br>

2. IN()
문자로 표현되는 경우 범위를 지정할 수 없다 그래서 여러개의 조건식을 작성할 때 OR로 연결하지만 IN()을 사용하면 간결하게 작성할 수 있다.
<br>
ex 1) or로 여러번 사용
```sql
select mem_name, addr
	from member
	where addr = '경기' or addr = '전남' or addr = '경남';
```
<br>
ex 2) in()으로 작성 : 조건식을 한 번에 작성
```sql
select mem_name, addr
	from member
	where addr IN('경기', '전남', '경남');
```
![[스크린샷 2024-02-16 오후 4.32.36.png]]
<br>
3. LIKE
   : 문자열 중 일부 글자를 검색하는 기능
   - ==%는 뒤에나오는 데이터 무엇이든 허용한다는 의미==
   - __ 언더바는 앞에 나오는 데이터 무엇이든 허용한다는 의미
   <br>
```sql
select *
	from member
	where mem_name LIKE '에%';
```
![[스크린샷 2024-02-16 오후 4.39.07.png]]
<br>
```sql
select *
	from member
	where mem_name LIKE '__핑크';
```
![[스크린샷 2024-02-16 오후 4.41.46.png]]
<br>
