# REST API

REST API는 자원(URI), 행위(HTTP METHOD), 표현(Representation) 세 가지로 구성되어 있다.

## REST의 특징

1. Uniform : URI로 지정한 리소스에 대한 조직을 통일되고 한정적인 인터페이스로 수행한다.
2. Stateless : 작업을 위한 상태정보를 따로 저장하고 관리하지 않는다. 들어오는 요청만 처리하면 된다. 세션 정보나 쿠키 정보를 별도로 저장하고 관리하지 않아 구현이 단순해진다.
3. Cacheable : HTTP 웹표준을 그대로 사용하기 때문에, 웹에서 사용하는 HTTP 프로토콜을 그대로 적용 가능하다. Last-Modified 태그 혹은 E-Tag를 이용하면 캐싱 구현이 가능하다.

### 캐싱 구현이란?

캐시 : 임시 저장소

즉, DB, 서버, 외부 API등 비용이 큰 연산의 결과를 미리 저장해두고 다시 같은 요청이 들어오면 저장된 값을 재사용한다.

1. Self-descriptiveness : REST API 메시지만 보고도 내가 뭘 하려는지, 쉽게 이해할 수 있는 자체 표현 구조로 되어 있다.
2. Client - Serer : REST 서버는 API 제공의 역할, 클라이언트는 사용자 인증이나 컨텍스트(세션, 로그인 정보)등을 직접 관리하는 역할을 각각 맡아서 하기 때문에 각가 개발해야 할 영역이 명확해지고 의존성을 약화시킨다.
3. 계층형 구조 : REST 서버는 다중 계층으로 구성될 수 있으며 보안, 암호화 계층 등을 따로 추가해 구조상의 유연성을 둘 수 있고, 네트워크 기반의 중간매체도 사용할 수 있다.

## 디자인 가이드

1. URI는 정보의 자원을 표시해야 한다.
    1. 서버가 다루어야 할 자원에 집중해 설계하여야 한다. 즉, 서버가 관리하는 자원을 어떻게 해야 하는지 보다는, 어떤 자원을 다루어야 하는지의 **명사**를 중심으로 URL을 설계한다.
    2. 예시
        
        
        | /members | 회원 목록 |
        | --- | --- |
        | /members/1 | 아이디가 1인 회원 |
        | /articles | 게시글 목록 |
        | /articles/1 | 아이디가 1인 게시글 |
        | /members/1/articles | 아이디가 1인 회원이 작성한 게시글 목록 |
2. 자원에 대한 행위는 HTTP METHOD로 표현한다.
    
    
    | GET | /members | 회원 목록을 조회한다. | 데이터 조회, 서버에서 정보를 가져온다. (당연히 읽기 전용) |
    | --- | --- | --- | --- |
    | GET | /members/1 | 아이디가 1인 회원을 조회한다. |  |
    | POST | /members | 회원을 생성한다. | 서버에 새로운 리소스를 생성한다. |
    | PUT | /memebrs/1 | 아이디가 1인 회원을 수정한다. | 해당 리소스 전체를 교체한다. |
    | DELETE | /members/1 | 아이디가 1인 회원을 삭제한다. | 해당 리소스를 삭제한다. |
    | PATCH |  |  | 해당 리소스의 **일부**를 수정한다. |

소유 HAS의 관계를 표현할 때는 {}를 활용한다.

/리소스명/리소스 ID/ 관계가 있는 다른 리소스명은,

GET : /users/{userid}/devices 로 표현 가능하다.

[RESTful API](https://www.notion.so/RESTful-API-23ad94c59ce88019b5ebd8c0b4739e10?pvs=21) 를 보자!

### CRUD

좀 더 자세하게, CRUD에 관여하는 네 가지 메서드는 POST, GET, PUT, DELETE이다.

POST : 리소스를 **생성**한다! 적절한 URI를 요청해주어야 한다. GET 메서드를 쓰지 않도록 주의한다.

GET : 해당 리소스를 조회한다.

PUT : 해당 리소스를 수정한다.

DELETE : 해당 리소스를 삭제한다.

## 주의할 점

1. 슬래시 구분자 / 는 계층 관계를 나타내는 데 사용한다.

[example.com](http://example.com) / houses / apartments

1. URI 마지막 문자로 슬래시 / 를 포함하지 않는다.

[example.com](http://example.com) / houses / apartments /  (X)

1. 하이픈 - 은 URI 가독성을 높이는 데 사용될 수 있다.
    1. 대신 밑줄 _ 은 사용하지 않는다.
2. URI 경로에는 소문자가 적합하다.
3. 파일 확장자(photo.jpg)는 URI에 포함시키지 않는다.
    1. 대신 Accept 헤더를 사용하도록 하자.

## Collenction vs Document

Document는 한 객체라면, Collection은 문서, 객체들의 집합이다.

~.com/sports/soccer 가 있다면 sports는 컬렉션, soccer는 도큐먼트이다.

~.com/sports/soccer/players/13

이라면 sports, players는 컬렉션. soccer와 13은 도큐먼트이다. 이때, 컬렉션을 의미하는 단어들은 전부 복수로 사용되고 있다.

## HTTP 응답 코드

| HTTP 상태 코드 | 의미 |
| --- | --- |
| 200 OK | 요청을 정상적으로 처리하였다. |
| 201 Created | 요청에 따라 자원을 정상적으로 생성하였다. |
| 400 Bad Request | 요청 정보가 잘못되었다. |
| 401 Unauthorized | 요청을 처리하기 위해서는 인증이 필요하다. |
| 403 Forbidden | 요청에 대한 권한이 없다. |
| 404 Not Found | 요청의 대상 자원이 없다. |
| 405 | 요청한 리소스에는 사용 불가능한 메소드를 이용했다. |
| 301 | 요청한 리소스에 대한 URI가 변경되었다. |
| 500 Internal Server Error | 서버 오류가 발생하였다. |

# DTO
DTO(Data Transfer Object)란 데이터 전송 객체로, 프로세스 간에 데이터를 전달하는 객체를 의미한다. API 와 Controller사이에 순수하게 전달하고 싶은 데이터를 DTO에 담아 전달한다. 즉, 요청으로 들어온 JSON 데이터를 TypeScript 클래스 형태로 변환해서 관리하는 구조이다.

```java
@Controller("user")
export class UserController {
	constructor(private readonly userService: UserService) {}

	@Get("")
	async getUser(@Query("id") id: string) {
		return this.userService.getUser(id);
	}
}
```

클라이언트의 HTTP 요청을 받는 진입점, URL, HTTP 메서드에 따라 서비스를 호출하는 역할을 한다. 요청 값을 파라미터로 받아 가공 후 전달하며 서비스 겨과를 클라이언트에게 JSON 형태로 응답한다.

```java
@Injectable()
export class UserService {
	constructor(
		@InjectRepository(User)
		private readonly userRepository: Repository<User>
	) {}

	async getUser(id: string) {
		return this.userRepository.findOneBy({ id });
	}
}
```

비즈니스 로직을 담당한다. 컨트롤러에서 받은 요청을 처리하고 DB 접근 로직을 수행한다. 엔티티와 통신하여 데이터를 CRUD 한다.

```java
@Entity("user")
export class User {
	@PrimaryColumn("varchar", { length: 50, name: "id" })
	id: string;

	@Column("varchar", { nullable: false, name: "pw" })
	pw: string;

	@Column("varchar", { nullable: false, length: 50, name: "name" })
	name: string;

	@CreateDateColumn()
	createdAt: Date;

	@UpdateDateColumn()
	updatedAt: Date;

	@DeleteDateColumn()
	deletedAt: Date;
}
```

DB 테이블과 1:1로 매핑되는 클래스, 각 속성이 DB Column에 대응된다. ORM이라고 하는 게 이 클래스를 이용해 SQL 쿼리를 자동으로 생성한다.

→ Controller는 요청을 받고, Service는 로직을 처리하고, Entity는 데이터를 저장하는 구조를 정의한다.

만약 제한 조건 등을 설정하려면 컨트롤러의 @Query() 안에 구문을 추가하면 된다. 하지만 조건이 늘어나고 유효성 코드를 추가하면 비즈니스 로직보다 컨트롤러 코드가 더 길고 복잡해진다.

클래스의 속성에 제약 조건(유효성 검사 규칙)을 지정할 수 있는 라이브러리인 class-validator가 있는데, 이 라이브러리는 데코레이터(Decorator) 문법을 이용한다. 이 문법을 이용해 DTO의 각 필드에 검사 규칙을 붙일 수 있다.

```java
/* 컨트롤러의 Get 을
@Get("")
	async getUser(@Query("id") id: string) {
		return this.userService.getUser(id);
	}
에서
*/
@Get("")
	async getUser(@Query() findUserDto: FindUserDto) {
		return this.userService.getUser(findUserDto);
	}
// 로 변환한다.
```

```java
/* 서비스의 getUser 를
async getUser(id: string) {
		return this.userRepository.findOneBy({ id });
	}
에서
*/
async getUser({ id, name }: FindUserDto) {
		const user = await this.userRepository.findOneBy({ id, name });
		return plainToInstance(FindUserReturnDTO, user);
	}
// 로 변환한다.
```

```java
// Request DTO
@Exclude()
export class FindUserDto {
	@Expose()
	@IsString()
	@MinLength(5)
	@MaxLength(10) // 제약 조건들 설정
	@IsNotEmpty()
	id: string;

	@Expose()
	@IsString()
	@IsNotEmpty()
	name: string;
}
```

```java
// Response DTO
@Exclude()
export class FindUserReturnDTO {
	@Expose()
	id: string;

	@Expose()
	name: string;
}
```

### Q : 요청 DTO? 이미 Entity 클래스가 있는데 굳이 DTO를 따로 만들어야 하는 이유?

```java
@Entity("user")
export class User {
	@IsString()
	@MinLength(5)
	@MaxLength(10)
	@IsNotEmpty()
	@PrimaryColumn("varchar", { length: 50, name: "id" })
	id: string;

	@Column("varchar", { nullable: false, name: "pw" })
	pw: string;

	@IsString()
	@IsNotEmpty()
	@Column("varchar", { nullable: false, length: 50, name: "name" })
	name: string;

	@CreateDateColumn()
	createdAt: Date;

	@UpdateDateColumn()
	updatedAt: Date;

	@DeleteDateColumn()
	deletedAt: Date;
}
```

이렇게 몰빵치면 DTO 두 개씩 만들 필요 없지 않을까?

### A :

코드의 목적은 id와 name을 사용해서 사용자를 찾는 GET API를 구현할 의도지만, 엔티티를 사용하게 되면 id, name 외에도 다른 모든 정보를 받게 된다. 이렇게 되면 내게 필요한 정보들을 고르느라 오랜 시간을 투자해야 함과 동시에 네트워크 오버헤드가 증가하는 문제점이 있다.

### 응답 DTO 역시 필요한 이유

응답 DTO는 안전장치에 가깝다.

요청 DTO에서의 문제와 같이 응답 DTO를 사용하지 않고 엔티티 그대로를 받으면 필요 없는 정보들을 프론트엔드에서 모두 받게 된다. 또한 비밀번호와 같은 민감한 정보를 그대로 전달하게 된다. 또한, 클라이언트에게 User 엔티티의 설계가 노출되게 된다. 프로젝트의 데이터베이스 구조가 노출돼 보안에 매우 취약해진다.