# 정리
1. 컨트롤러
2. 서비스
3. 레포
4. 엔티티
5. DTO


# ORM 이해하기

# 영속성 이해하기
## 영속성(Persistence Context)
Persistence Context  
직관적으로 이해 해 보자  

## EntityManagerFactory
Entity Manager 를 만들고 구성하는 법을 제공하는 interface 


## EntityManager
DB table 과 mapping 된 객체인 Entity에 대한 CRUD 작업을 수행하기 위한  
method들을 제공, Entity의 라이프 사이클과 영속성 관리등을 담당함

## Persistence Context
1. entity의 영속화에 관여하며
2. entity들이 DB로 바로 가지 않고 entity를 저장하는 환경으로서의 역활을 함  

> ! Context
> 사전적 의미 자체는 맥락이나 문맥을 의미하지만  
> 개발에서 말하는 Context의 주된 뜻은 이벤트가 일어나는 조건, 환경 등이 된다.
> 코드의 배경이 되는 조건, 환경정도

정리하자면  
엔티티...


# 구현시 문제점

## 명세서 관련

### 1.Entity 명세서(변수 이름 지정)
Entity를 만들때 어떤 명세서를 봐야되는가
ERD 다이어그램 먼저 만들어야 될거같다
미리 명세

### 2. DB 에서 list를 추출할때

재고 구매의 문제점

POST /order

```json
//Input
{
    "items" : [ 
    {
        "name" : "apple", 
        "cnt" : 20 //물건 구매 갯수
    },
    {
        "name" : "water",
        "cnt" : 20
    }
    ]

}
```

```Json
// OUTPUT
{
    "totalPrice": 40000 ,
    "cart" : [
        {
            "name" : "apple",
            "cnt" : 20,
            "price" : 20000
        },
        {
            "name" : "water",
            "cnt" : 20,
            "price" : 20000

        }]
}
    
```



output에서 리스트로 빠져 나오는데 어떻게 쿼리를 작성해야될까

### 자바 예외처리 할때
Response객체로 예외처리하면 swagger 에서도 보이지만
Exception 던지면 안보인다 어떻게 예외처리를 해야될까?

### DTO 객체를 받을때
Entity 로 받고 응답 DTO로 값 옮기기
처음부터 응답 DTO로 받기

-> N+1 문제와 연관 있는거같음

