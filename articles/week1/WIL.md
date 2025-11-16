# Rest
RestAPI의 기본 특징과 파라미터 규칙, 동사와 명사  
CRUD, Error type 등등 기본적인 내용을 정리했다.

# API
자바에서 API를 작성할때 가장 많이 사용하는 도구중 하나인 Swagger을 사용해봤다  
검색하면서 알게된 점은 Spring Rest Docs과 Swagger 두개를 많이 사용하고  
장단점이 궁금하게 되어 찾아보게됨
## Swagger
장점 : 문서에서API test가 가능함 , 자동화
단점 : 테스트 코드가 강제되지 않음, 운영 코드에 침투적이다(Interface로 구분하는 방법도 있음)
## Spring Rest Docs
장점 : 문서화를 위해 테스트 코드가 강제됨, 비즈니스 코드가 깔끔해짐
단점 : 문서에서 API불가, 아름답지 않음(?)
# Spring-boot
과제 3번을 하면서 비즈니스 로직도 Controller에 짜다보니 자연스럽게  
Service계층의 필요성과 Controller의 차이점이 궁금하게 되어 찾아보았다.
그리고 Entity와 DTO의 개념이 아직은 모호한거같다

## 계층구조의 필요성

계층구조가 있으면 관심사 분리 할 수 있다
하나였던 코드량이 줄어들고 **응집도가 높아져 유지보수성** 이 높아진다

1. Controller : DB 관련 이외의 책임 HTTP 요청 & 응답 변환 책임
    + 직렬화/역직렬화 : 데이터 포맷 <-> 자바 객체 변환
2. Service : 비즈니스 로직 수행 책임
3. Repository : 도메인 <-> DB 전달 객체(DTO, Entity)로 변환하여 Service와 상호작용 책임
4. Dao : DB crud 수행 책임

어떤 문제가 생기면 어떤 층에서 문제가 생겼겠네 -> 유추 가능


## HttpEntity클래스
ResponseEntity의 사용법을 찾다가 상위 클래스를 알게됐다.  
Spring Framework에서 제공하는 클래스중 HttpEntity라는 클래스가 존재한다  
이것은 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스이다.  
HttpEntity 클래스를 상속받아 구현한 클래스가 RequestEntity, RequestEntity다

### ResponseEntity
HttpRequest에 대한 응답 데이터를 포함하는 클래스다
HttpStatus, HttpHeaders, HttpBody를 포함한다.  



## Bean
Swagger를 사용하기위해 문서를 찾아보던중 Configuration - @Bean에 대한 어노테이션이 보였다  
이전에도 자주 보던 개념들이지만 명확하지 않아 이참에 완전히 알고 가려 한다  
 
>자바 유래 : 자주 마시던 커피가 인도네시아 자바 섬 커피
>재사용 가능한 객체를 커피콩에 비유 -> 스프링 빈
**단순하고 가벼운 객체**라는 뜻  
컨테이너 안에 여러개의 빈이 들어있다는 개념도 이해가 간다  

### 스프링 빈의 역할
객체 생성 및 관리  
의존성 관리 - 두 객체를 자동으로 연결해줌
객체 생애 주기 관리  

### 스프링 빈 등록 법

1. 어노테이션 기반 설정 : @Component, @Service, @Repository, @Controller 
2. 자바 설정 파일 : @Configuration과 @Bean 어노테이션을 사용해 자바 클래스에서 직접 빈을 등록함. 좀 더 명시적임

```Java

@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserService();
    }

```

>정리  
>어노테이션 기반과 Bean 둘다 약간의 차이점은 있다

## 의존성 : loc와 DI
의존성 : 파라미터나 리턴값 또는 지역변수 등으로 다른 객체를 참조 하는것  

## IoC 컨테이너 
Inversion of Control : 제어반전
개발자가 직접 관리하지 않고 Ioc 컨테이너가 관리해줌(=스프링 컨테이너)

## IoC의 분류 DL과 DI 
+ DL(Dependency Lookup)  
저장소에 저장되어 있는 Bean에 접근하기 위해 컨테이너가 제공하는 API를  
이용하여 Bean을 Lockup하는것

+ DI (Dependency Injection)  
각 클래스간의 의존관계를 빈설정 정보를 바탕으로 컨테이너가 자동연결
 + Setter Injection
 + Constructor Injection
 + Method Injection


DL을 자주쓰면 컨테이너 종속이 증가하기에 DI를 자주씀



### 1. 생성자 주입 Autowired&All/NoArgsConstructor
클래스의 생성자가 하나이고, 그 생성자로 주입받을 객체가 빈으로 등록되어 있다면  
 @Autowired를 생략할 수 있다
이제 이러면 @Autowired의 기본적인 이해가 가능해짐  

```Java
@NoArgsConstructor
public class ProductService {
    @Autowired
     private ProductRespository productRespository;

    //-------------

    //@Autowired이게 자동으로 생성해 주는것
     public ProductService(ProductRespository productRespository){
        this.productRespository = productRespository;
     }

}
```
생성자를 통해 컨테이너가 객체를 직접 의존성 주입해준다
(Service -> Repository의 의존관계 주입)


### 2. 필드 주입

필드에 @Autowired 어노테이션만 붙여주면 자동으로 의존성 주입된다
```Java

@Entity
public class ProductCreateRequest {

    @Autowired
    private String name;
    private Long stock;
    private Long price;

}
```

>생성자와 객체 생성자가 필요한 이유  

JPA와 같은 프레임 워크는 내부적으로 객체를 생성하기 위해 기본 생성자를 필요로 한다.
왜냐하면 JPA가 데이터베이스에서 데이터를 조회하여 엔티티 객체를 생성할 때  
 **리플렉션(Reflection)** 이라는 기술을 사용하기 때문이다

리플렉션은  
자바 런타임에서 클래스의 구조를 검사하고 동적으로 객체를 생성할 수 있게 해준다.  
이 과정에서 JPA는 엔티티의 기본 생성자를 호출해 객체를 인스턴스화 하고,  
이후 데이터베이스에서 조회한 값을 엔티티 필드에 매핑한다.  
기본생성자가 없으면 JPA는 객체를 생성 할 수 없어서 오류가 발생한다.  
그래서 자동으로 생성자를 만들어 주는 **NoArgsConstructor**를 사용한다

>정리
>JPA가 DB에서 데이터를 조회 후 객체 생성할때 리플렉션 사용    
>리플랙션은 기본 생성자를 호출해 객체를 인스턴스화 함  
>그러므로 기본 생성자가 없으면 객체를 생성 못하므로 오류발생  
>NoArgsConstructor을 사용


### 3.수정자 주입
Setter의 문제점 때문에 생성자 주입의 사용을 권장한다.




## Builder Pattern
필드가 많을수록 생성자 패턴이 다양해 질 수 있다

>int add()
>int add(int x)
>int add(int y)
>int add(int x, int y)

이렇게 다양한 생성자의 패턴을 자동으로 만들어 주는 기능임


# 결론

땅바닥 부터 혼자 코드를 작성하려 하니까
평소에 헷갈렸던 개념들이 궁금해졌다.
옛날같았으면 그냥 GPT 돌리거나 인강보고 그렇구나 하고 넘어갔을 내용들인데 이번엔 궁금한 점들을 납득할때 까지 찾아본거같다.
Contoller계층에서 모든걸 구현하려고 하니 복잡성을 많이 느낀다.