# Week1

Client → Controller → Service → Repository → Database

### Controller

HTTP 요청을 받고 DTO로 변환한다, Service를 호출하고 그 결과를 응답으로 돌려준다.

```java
@PostMapping // 새로운 상품을 등록한다.
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) { // 기본적으로 한 번에 하나의 등록, 수정만 가능하다고 하자.
        // createProductRequest는 DTO에서 자세히 구현한다.
        Product product = new Product(); // 새 상품 객체 생성
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = adminProductService.create(product); // create 서비스 호출
        // 이미 존재하는 상품에 대한 예외는 서비스 계층에서 처리한다.
        return ResponseEntity.ok(saved);
    }
```

POST, GET 등등의 HTTP 메소드에 따라 매핑해주는 어노테이션을 달아준다.

그리고 여기서 CreateProductRequest DTO에서 요청한 Body를 파싱한다. request.getName() 등이 그것이다. 그 값들을 이용하여 목표 Product Entity 객체를 생성한다. 이 객체를 이용해 해야 할 일을 Service를 호출하여 진행하고 있음을 adminProductService를 통해 알 수 있다. 그 결과를 saved에 받아온 뒤, ResponseEntity로 감싸 응답하고 있다.

### Service

핵심 비즈니스 로직을 수행한다.

```java
public Product create(Product p) {
        if (productRepository.findByName(p.getName()).isPresent()) // 리포지토리 계층에서 상품의 이름을 조회 후 isPresent 한지 확인
            throw new IllegalArgumentException("이미 존재하는 상품입니다."); // 중복 예외처리
        return productRepository.save(p); // 그렇지 않다면 상품을 저장한다.
    }
```

컨트롤러에서 받아온 Product p를 우선 이미 존재하는 상품인지 확인하기 위해 예외처리를 해 준다. 이 역할도 서비스 계층에서 실행한다.

통과했다면, 리포지토리 계층을 사용해 실제 SQL 명령을 실행한다.

### Repository

```java
public interface ProductRepository extends JpaRepository<Product,Long> { // 인터페이스 상속 시 extends 사용
    // JpaRepository로 기본 CRUD 자동 구현 가능
    Optional<Product> findByName(String name); // 이름으로 상품을 찾는다.
    // Optional을 쓰면 NULL 처리를 안전하게 할 수 있다.
}
```

리포지토리 계층은 JPA가 DB에 접근하여 SQL문을 실행시켜 CRUD를 수행한다.

SQL 실행 방법은 JPA Query Method를 믿고 의지하거나, JPQL을 직접 접근하는 방법도 있다..

[리포지토리 작성](https://www.notion.so/234d94c59ce880c58ffbd32b236f775e?pvs=21) 페이지에 예전에 정리해놓은 게 있으니 확인해보자. JPA는 간단히 말하면 내가 쓴 명령어를 분석해서 알아서 SQL을 만들고 실행해준다. 이걸 가져오기 위해 extends로 불러온다. Product는 이 Repository가 다룰 엔티티 클래스 타입이고, Long은 이 엔티티의 PK타입을 명시했다. 이 두 개의 파라미터가 필요하다. 이제 우리는 findByXXX, deleteByXXX 등의 메서드 네이밍으로 모든 SQL을 실행할 수 있다.

### Entity

엔티티 클래스는 실제 DB 테이블에 매핑되는 클래스이다.

```java
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 생성 전략
    private Long id;

    private String name;
    private int price;
    private int stock;
}
```

입출력은 없고, 그냥 우리가 다룰 객체가 어떤 값을 가지고 있는지, id 생성 전략은 어떻게 할 건지 (이것도 여러 방법이 있다. 역시 [JPA](https://www.notion.so/JPA-230d94c59ce880b8a25bf63da4fa31d7?pvs=21) 페이지에 예전에 공부하면서 정리했던 내용이 있으니 참고하자.) 에 대해 정의한다.

### DTO

DTO는 요청 / 응답 데이터를 전송하기 위한 전용 객체이다.

DTO에 대한 내용은 따로 정리했다.

## 총 흐름 정리 (상품 등록을 예시로)

1. 클라이언트가 요청한다. 사용자가 상품 등록을 제출하면 POST /admin/products 라는 요청 URL과 함께, JSON 형식의 Request Body가 전송된다.
2. 요청 데이터 수신을 맡고 있는 DTO가 이 요청을 받는다. 이 DTO는 받은 JSON 형식의 정보들을 자바 객체로 변환하고 추가적인 어노테이션으로 유효성 검사도 할 수 있다.
3. DTO가 JSON 데이터를 받았다면, 요청 URL은 Controller가 받는다. 요청 URL의 HTTP 메소드를 보고 Controller 내부에서 어노테이션 매핑을 통해 적절한 메소드가 받아주게 된다. 이제 그 안에서 요청 JSON 관련 DTO가 열심히 만들어 놓은 자바 객체를 가져온다. 이 객체를 이용해 추가할 Product 엔티티를 생성한다. 이제 이 객체를 Service 계층에 전달하여 등록해달라고 부탁한다.
4. Server 계층은 상품명이 중복되는지 확인 후 중복 시 예외 발생을 던진다. 중복이 없다면 리포지토리 계층의 save()로 DB에 insert한다.
5. Repository 계층은 JPA가 자동으로 CRUD 기능을 구현한다. 우선 SELECT * FROM product WHERE name = ‘apple’을 실행 후 INSERT INTO product (name, price, stock) VALUES (’apple’, 1000, 100)을 실행한다.
6. ResponseEntity가 반환되어 HTTP 응답 코드 200 OK와 함께 클라이언트로 전달된다.