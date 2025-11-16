# Controller에서 Service로의 분리

### Separation of Concerns, SoC

컨트롤러가 DB 접근, 예외처리, 비즈니스 로직을 모두 부담하게 되면, 변경할 때마다 전체를 고쳐야 하고 코드가 비대해진다. 여러 기능이 한 클래스에 섞여 있으면 유지보수 또한 어려워진다. 따라서, 한 컴포넌트는 하나의 역할(Concerns)만 수행해야 한다. 이렇게 하게 되면..

1. 낮은 결합도 (Loose Coupling) : 각각의 코드가 서로 얽혀있지 않고 독립적으로 잘 분린된다. 결합도란 의존성의 정도를 나타내며, 다른 모듈에 대해 얼마나 많은 지식을 갖고 있는가에 대해 나타낸다. 결합도가 높을수록 함께 변경해야하는 모듈의 수도 늘어나기 때문에 결합도를 낮게 하는것이 좋다.
2. 높은 응집도 (High Cohesive) : 응집도는 내부 요소들이 연관되어 있는 정도이다. 하나의 목적을 위해 서로 협력한다면, 그 모듈은 높은 응집도를 가지게 된다. 관심사 분리를 하게 되면 유사한 내용끼리 비슷한 위치에 잘 뭉쳐있기 때문에 높은 응집도를 가지게 된다.

이렇게 예쁘게 관심사를 여러 개로 분리하여 여러 계층으로 분리한 아키텍쳐를 Layered Architecture라고 한다. 이들은 추상화된 인터페이스로만 소통한다. 소통은 자신에게 인접한 **하위** 계층에 요청을 보내는 방식으로 진행한다. 핵심은, 하위 계층은 상위 계층을 몰라야 한다. 하위 계층은 인터페이스만 제공하고 요청만 받을 뿐이다. 이것을 **단방향 의존성**이라고 한다. 

### Service Layer

1. 비즈니스 로직의 분리 : 어떠한 기능 단위의 결과물을 Controller로 전달한다. 계층의 한가운데에서 핵심 비즈니스 로직을 처리하는 역할을 맡는다.
2. 트랜잭션 관리 : 각 핵심 기능마다 데이터의 일관성과 무결성을 보장하기 위해 트랜잭션을 관리한다. 어떠한 기능을 처리하기 위해서는 여러 DAO 혹은 Service를 통해 데이터를 다루어야 하는데, 중간에 어떤 예상치 못한 요소로 인해 기능이 중단될 때 처음 상태로 되돌려야 하기 때문이다. 이 속성을 트랜잭션의 네 가지 요소 ACID 중 원자성(Atomicity)이라고 한다. All Of Notiong 으로 실행되면 모두 실행, 하나라도 실패하면 모두 정지 후 원상 복귀한다.
3. 코드 재사용성 촉진 : 비즈니스 로직을 캡슐화하므로 애플리케이션의 여러 부분에서 코드 재사용성을 촉진한다. 여러 컨트롤러가 동일한 서비스를 이용하여 작업을 한다면 서비스 계층의 코드를 불러와 사용하면 되기 때문에 코드 중복을 줄일 수 있다.

---

## Layered Architecture 에서 DDD로

기존의 전통적인 Layered Architecture에서는 Service계층이 모든 비즈니스 로직을 담당했다. 업무(도메인) 중점의 개발을 하기 위해 Domain Driven Design(DDD)가 탄생했다. 여기에서는 비즈니스 로직을 Service 계층에 모두 맡기는 것이 아니다. Service 계층은 이제 Application Service와 Domain Service 두 계층으로 분리된다.

### Application Service

비즈니스 흐름(Orchestration)만 담당한다. 비즈니스 규칙은 여기에 없고, 로직은 도메인 모델에 위임한다. 여기서는 트랜잭션을 시작/종료하며 여러 도메인 객체를 조합하여 실행하고, 외부 시스템(API 등)을 호출한다. 또한, Repository를 통해 Aggregates를 로드/저장한다.

### Domain Service

실제 소프트웨어가 해결하고자 하는 문제의 키가 담겨있다. 각 도메인의 기능은 타 도메인에서는 수행할 수 없고 해당 도메인을 통해서만 실행되고 관리될 수 있게 분리해서 설계하여야 한다.

### Aggregate란?

도메인의 여러 객체의 묶음이다. 하나의 일관성(Consistency)을 유지해야 하는 도메인 객체들의 집합으로, 이 집합을 대표하는 주인을 Aggregate Root라고 한다.

실무에서는 개별 객체(Entity)가 단독으로 쓰이는 경우가 거의 없다. 예를 들어 주문 도메인에서는 Order, OrderItem, Money, PaymentInfo 등등.. 여러 엔티티들로 묶여 있다. 이 객체들은 하나의 “주문” 이라는 논리적 단위로 묶인 Aggregate가 되고, 이 전체를 책임지는 대표 엔티티인 Order가 바로 Aggregate Root이다.

1. Aggregate Root만 외부에서 접근할 수 있다. 외부는 OrderItem과 같은 내부 엔티티를 직접 변경할 수 없다. 항상 root를 통해 접근해야한다. 이로써 일관성이 무너지는 걸 방지할 수 있다.
2. 하나의 Aggregate는 하나의 트랜잭션 단위이다. 주문 Aggregate 전체는 ㄴ하나의 트랜잭션으로 처리되어야 한다. ACID가 지켜져야 한다는 의미이다.
3. Repository는 반드시 Aggregate Root 기준으로만 만든다.

---

# 객체 지향 설계의 5원칙

## SOLID

### Single Responsibility Principle : 단일 책임 원칙

클래스(객체)는 단 하나의 기능만을 담당해야 한다. 하나의 클래스에 여러 개의 기능이 있다면 기능 변경 시 수정사항이 많아진다. 프로그램의 유지보수를 위해 클래스에는 하나의 기능만을 담당할 수 있도록 노력해야 한다.

### Open Closed Principle : 개방 폐쇄 원칙

확장에는 열려있어야 하며, 수정에는 닫혀있어야 한다. 새로운 변경 사항이 발생했을 때 유연하게 코드를 추가함으로써 큰 힘을 들이지 않고 기능을 확장할 수 있어야 하지만 객체를 직접적으롤 수정하는 것은 제한해야 한다.

### Liskov Substitution Principle : 리스코프 치환 원칙

자식 타입은 언제나 부모 타입으로 교체할 수 있어야 한다. 즉, 부모 클래스의 행동 규약을 자식 클래스가 위반하면 안 된다. 오버라이딩 시 재정의를 주의하여 해야 한다는 것이다.

### Interface Segregation Principle : 인터페이스 분리 원칙

인터페이스는 제약 없이 자유롭게 다중 상속이 가능하기 때문에, 분리할 수 있으면 잘게 분리하여 각 클래스 용도에 맞게 implements 하자.

### Dependency Inversion Principle : 의존 역전 원칙

어떤 Class를 참조해야 하는 상황이 생긴다면, 그 Class를 직접 참조하는 것이 아닌 대상의 상위 요소인 Abstract Class나 Interface로 참조하라는 원칙이다. 의존 관계를 맺을 때는 변화하기 어려운(거의 변화하지 않는)것에 의존하라는 뜻이다.

## 객체 분석

### ProductController

Role : 상품 조회 및 구매 요쳥을 처리하는 객체 → Presentation

1. HTTP 요청을 해석한다.
    1. /products/purchase 의 본문을 읽고 Map으로 받는다.
    2. /products?name=String 형식의 쿼리를 받아 상품명을 추출한다.
2. 입력 값을 서비스 계층으로 분석한다. 비즈니스 로직은 컨트롤러에서 진행하지 않는다. (SRP, 단일 책임 원칙)

### AdminProductController

Role : 상품 등록, 수정, 삭제를 담당하는 관리자 기능을 구현한 객체

1. 입력 DTO를 해석한다. CreateProduct, AddStock, DeleteProducts와 같은 DTO를 해석하고 Entity로 변환한다.
2. 역시 비즈니스 로직 실행을 서비스 계층으로 위임하고 있다. SRP를 준수하고 있다.

두 컨트롤러 모두 Service를 직접 new로 생성하거나 구체 구현체를 의존하지 않고 있다. 스프링의 DI 방식은 추상에 의존하는 구조로 이루어져 있으며 컨트롤러는 Service의 구체가 아닌 역할에 의존하고 있기 때문에 DIP를 지키고 있다.

이제 배운 대로 AdminProduct 로직을 수정해보자.

```java
public AdminProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        // 이름 중복 검사를 실행한다.
        if (productRepository.findByName(request.getName()).isPresent()) // 리포지토리 계층에서 상품의 이름을 조회 후 isPresent 한지 확인
            throw new IllegalArgumentException("이미 존재하는 상품입니다."); // 중복 예외처리

        // 엔티티를 생성한다. createProductRequest는 DTO에서 자세히 구현한다.
        Product product = new Product(); // 새 상품 객체 생성
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        // DB에 저장한다
        Product saved = productRepository.save(product);

        // DTO로 변환 후 반환한다.
        return new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock()
        );
    }

    public ProductResponse addStock(AddStockRequest request) { // DTO를 받자.
        // 이름으로 상품을 찾는다. 대신 이름과 맞는 상품이 없을 경우 예외 처리 한다. 이때, ()는 람다식으로 매개변수 없는 함수를 의미.
        // 만약 findByName() 결과가 비어 있으면, new Illegal 예외를 만들어 던져라.
        // 상품을 조회한다.
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("ID와 맞는 상품을 찾을 수 없습니다."));

        // 재고를 증가시킨다.
        product.setStock(product.getStock() + request.getAddStock());

        // DB에 저장한다.
        Product updated = productRepository.save(product);

        // 반환을 DTO로 변환하여 반환한다. 이때 Repository는 Entity만 Save 하므로 기존 코드 수정.
        return new ProductResponse(
                updated.getId(),
                updated.getName(),
                updated.getPrice(),
                updated.getStock()
        );
    }

    // 한 개만 지우는 설계는 배제했다.
    public ProductsStockResponse deleteProducts(DeleteProductsRequest request) { // DTO를 받도록 수정
        // 또한 여러 개의 입력을 받을 수 있도록 구현해야 한다.
        // 예외 처리는 생략했다.
        // 삭제한다.
        productRepository.deleteAllById(request.getIds()); // DB에서 삭제한다.
        
        // 삭제 후 전체 목록을 조회한다.
        List<Product> totalStock = productRepository.findAll();
        
        // 엔티티를 DTO로 매핑한다. DTO로 응답하기로 결정했으니까.
        List<ProductResponse> responses = totalStock.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock()
                ))
                .toList();
        
        // 만들어진 DTO를 반환한다.
        return new ProductsStockResponse(responses);
    }
}
```

Controller의 코드를 최대한 Service 계층으로 뺐다. 이때 하나는 DTO 형식으로 반환하고 하나는 엔티티를 반환하고 있는 것을 발견했다. 명세에서 제품 추가는 추가한 제품 정보를 반환하라는 명령이 없던 반면, 재고 변경은 재고가 어떻게 바뀌었는지 반환하라는 명령 때문에 차이가 난 것으로 예상했다. 따라서 서비스 계층은 항상 DTO를 반환하도록 정했다.

```java
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;
    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @PostMapping // 새로운 상품을 등록한다.
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) { // 기본적으로 한 번에 하나의 등록, 수정만 가능하다고 하자.
        // 객체를 생성하고 예외 처리하는 역할은 이제 서비스 계층이 맡도록 하자.
        // DTO를 받기 위해 받는 매개변순 타입을 ProductResponse로 변경하자.
        return ResponseEntity.ok(adminProductService.createProduct(request));
    }

    @PatchMapping("/stock") // 상품의 재고를 변환한다.
    public ResponseEntity<ProductResponse> addStock(@RequestBody AddStockRequest request) { // dto에서 구현해야 할 내용.
        return ResponseEntity.ok(adminProductService.addStock(request));
    }

    @DeleteMapping// 입력은 한 개 이상의 물품일 수 있다고 했다.
    public ResponseEntity<ProductsStockResponse> deleteProducts(@RequestBody DeleteProductsRequest request) {
        return ResponseEntity.ok(adminProductService.deleteProducts(request));
    }
}
```

컨트롤러가 매우 가벼워졌음을 확인할 수 있다.