- REST: Representational State Transfer의 약자
- REST 구성
    1. 자원(Resource) - URI
    2. 행위(Verb) - HTTP method
    3. 표현(Representations)

---

- REST의 특징
    1. Uniform(유니폼 인터페이스)
    : URI로 지정한 리소스에 대한 조작을 통일되고 한정적인 인터페이스로 수행하는 스타일
    2. Stateless(무상태성)
    : 작업을 위한 상태정보를 따로 저장하고 관리하지 않음 → 쿠키, 세션이 필요 
    : API 서버는 들어오는 요청만 단순히 처리하면됨 → 구현 단순, 서비스 자유도 상승
    3. Cacheable(캐시가능)
    : HTTP 웹 표준을 그대로 사용 → 웹에서 사용하는 기존 인프라 그대로 활용가능
    ⇒ 캐싱..기능..???
    4. Self-descriptiveness(자체 표현 구조)
    : REST API메시지만 보고도 이해 할 수 있음
    5. Client -Server 구조
    : 서버 = API제공 / 클라이언트 = 인증, 컨텍스트(세션, 로그인 정도)등을 직접 관리
    : 각각의 역할이 확실히 구분됨 → 각자 개발해야 할 내용이 명확해지고 의존성 감소
    6. 계층형 구조
    : 다중 층으로 구성가능
    : 보안, 로드 밸런싱, 암호화 계층 추가 가능 → 유연성
    : PROXY, 게이트웨이 같은 네트워크 기반 매체 사용 가능

---

- REST API 디자인 가이드
    - URI = 정보의 자원표현해야함 (어디에 있는 무언가를 요청?)
    - 자원의 행위 → HTTP Method(GET, POST, PUT, DELETE)로 표현 (어떻게 할까?)
    
    | **Resource** |  |  |
    | --- | --- | --- |
    | Create | POST | URI를 요청하면 리소스 생성 |
    | Read | GET | 리소스를 조회, 해당 도큐먼트에 대한 정보 가져옴 |
    | Update | PUT | 리소스 수정 |
    | Delete | DELETE | 리소스 삭제 |

---

- API 중심규칙
    1. URI는 정보의 자원을 표현 → 리소스명은 (명사 > 동사)
    GET / members/ delete/1 = REST를 제대로 적용하지 않음
    → URI는 자원 표현에 중점, 행위의 표현이 들어가면 안됨 
    2. 자원에 대한 행위는 HTTP Method로 표현
    DELETE / members/1 = HTTP method로 표현 
    
    ---
    

---

- 회원정보를 가져오는 URI
    - GET/members/show/1 → URI에서 행위를 나타내고 있어서 안됨
    - GET/members/1 (적합)
- 회원을 추가할 때
    - GET/members/insert/2 → get은 생성하는게 아님
    - POST/members/2 (적합,.. 생성하려면 POST임)
- URI 설계 시 주의할 점
    1. 슬래시 구분자(/)는 계층관계를 나타내는데 사용
    2. URI 마지막 문자로 슬래시를 쓰면 안됨
    : 분명한 URI를 만들어 통신해야해 혼동을 주지 않도록
    3. 하이픈(-)은 URI 가독성을 높이는데 사용
    4. 밑줄(_)은 사용하지 않는다 
    5. URI 경로에는 소문자가 적합
    : 대소문자에 따라 다른 리소스로 인식하게 되기 때문
    6. 파일 확장자는 URI에 포함시키지 않는다
    ex) jpg, png .. → 대신 Accept header 사용

---

- 리소스 간의 (연관)관계를 표현하는 방법
    
    `/리소스명/리소스ID/관계가 있는 다른 리소스명` 
    → GET :/users/{userid}/devices (일반적으로 소유 has관계표현)
    → GET :/users/{userid}/likes/devices (관계명 애매하거나 구체적 표현이 필요할때 서브 리소스에 명시적으로 표현)
    

---

- 자원을 표현하는 Collection과 Document
    - Document: 한 객체(문서)
    - Collection: 객체(문서)들의 집합
    
    → 모두 리소스,URI에 표현됨
    
    ---
    
    `http://restapi.example.com/sports/soccer/players/13`
    
    : sports, players 컬렉션, soccer, 13번을 의미하는 도큐먼트로 URI이어짐
    : 컬렉션을 복수로 사용하고 있음
    

---

- HTTP 응답 상태 코드	
200	클라이언트의 요청을 정상적으로 수행함
201	클라이언트가 어떠한 리소스 생성을 요청, 해당 리소스가 성공적으로 생성됨(POST를 통한 생성작업)
400	클라이언트의 요청이 부적절 할 경우
401	클라이언트가 인증되지 않은 상태에서 보호된 리소스를 요청할 경우(로그인 하지 않은 유저가 로그인시 요청가능한 리소스 요청)
402	유저 인증상태와 관계 없이 응답하고 싶지 않은 리소스를 요청했을때 (403보다는 400, 404 사용 권고)
405	클라이언트가 요청한 리소스에서는 사용불가능한 method이용
301	클라이언트가 요청한 리소스에 대한 URI가 변경 되었을때
500	서버에 문제가 있을경우