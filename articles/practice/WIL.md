# 정리
1. 컨트롤러
2. 서비스
3. 레포
4. 엔티티
5. DTO

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

현재 
상품 조회 서비스 클래스 수정해야됨