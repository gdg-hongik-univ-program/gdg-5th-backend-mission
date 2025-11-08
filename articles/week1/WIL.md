# Rest

# API

## Swagger

## Spring Rest Docs

# Spring-boot

## Entity와 DTO의 차이점 

## 계층구조의 필요성

## Bean이란?

## 의존성 loc와 DI

### Autowired 그리고 NoArgsConstructor
다음 코드에 문제점은 ?

```Java
public class ProductCreateRequest {

    private String name;
    private Long stock;
    private Long price;

}
```

JPA와 같은 프레임 워크는 내부적으로 객체를 생성하기 위해 기본 생성자를 필요로 한다.
왜냐하면 JPA가 데이터베이스에서 데이터를 조회하여 엔티티 객체를 생성할 때  
 **리플렉션(Reflection)** 이라는 기술을 사용하기 때문이다

리플렉션은  
자바 런타임에서 클래스의 구조를 검사하고 동적으로 객체를 생성할 수 있게 해준다.  
이 과정에서 JPA는 엔티티의 기본 생성자를 호출해 객체를 인스턴스화 하고,  
이후 데이터베이스에서 조회한 값을 엔티티 필드에 매핑한다.  
기본생성자가 없으면 JPA는 객체를 생성 할 수 없어서 오류가 발생한다.  

정리
JPA가 DB에서 데이터를 조회 후 객체 생성할때 리플렉션 사용  
리플랙션은 기본 생성자를 호출해 객체를 인스턴스화 함
그러므로 기본 생성자가 없으면 객체를 생성 못하므로 오류발생

```Java
public class ProductService {
     private ProductRespository productRespository;
}
```








## ResponseEntity
### HttpEntity클래스
Spring Framework에서 제공하는 클래스중 HttpEntity라는 클래스가 존재한다  
이것은 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스이다.  
HttpEntity 클래스를 상속받아 구현한 클래스가 RequestEntity, RequestEntity다

### ResponseEntity
HttpRequest에 대한 응답 데이터를 포함하는 클래스다
HttpStatus, HttpHeaders, HttpBody를 포함한다.  

