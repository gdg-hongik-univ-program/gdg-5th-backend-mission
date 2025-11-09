## RESTful API, DTO 학습 WIL

## RESTful API

- REST API 구성 요소
    - 자원 - URI
        - URI - 모든 자원(웹사이트 페이지, 이미지 파일, 동영상, PDF 문서 등)을 고유하게 식별(Identify)하는 문자열 주소
            - URL (위치, Locator): URI의 한 종류, 자원이 '어디에 있는지(Location)' 그 위치를 구체적으로 알려줌
    - 행위 - HTTP METHOD
    - 표현
- REST의 특징
    - Uniform Interface - URI로 지정한 리소스에 대한 조작을 통일되고 한정적인 인터페이스로 수행하는 아키텍쳐 스타일
    - Stateless(무상태성) - 작업을 위한 상태 정보(세션/쿠키 정보)를 따로 저장하고 관리하지 않음
        - 무상태성 때문에 서버는 들어온느 요청을 단순하게 처리만 하여 서비스의 자유도가 높아지고 서버에서 불필요한 정보를 관리하진 않음으로써 구현이 단순해짐
    - Cacheable - REST는 HTTP를 그대로 사용하기 때문에, 웹에서 사용하는 기존 인프라를 그대로 활용함 → HTTP가 가진 캐싱 기능 적용 가능
        - 캐시(Cache) - 나중에 더 빨리 접근하기 위해 데이터를 임시로 저장해 두는 장소(임시 저장소)
        - 한 번 저장해두면, 다음에 같은 사이트를 방문할 때 인터넷에서 다시 다운로드하지 않고 저장된 파일을 바로 읽어오기 때문에 웹사이트 로딩 속도가 매우 빨라짐
    - Self-descriptiveness - REST API의 메시지만 보고도 이를 쉽게 이해 할 수 있는 자체 표현 구조로 되어있음
    - Client-Server 구조 - Client와 Server의 역할이 확실히 구분되어 개발 내용이 명확해지고, 서로간 의존성이 줄어듦
        - Server - API 제공
        - Client - 사용자 인증이나 컨텍스트(세션, 로그인 정보) 등을 직접 관리
    - 계층형 구조 - REST 서버는 다중 계층으로 구성
- REST API 디자인
    - URI는 정보의 자원을 표현(리소스 명으로 동사보다는 명사를 사용 )
        - GET /members/delete/1 처럼 행위에 대한 표현이 들어가서는 안됨
        - 자원을 표현하는데 중점을 두어야함
        - DELETE /members/1로 수정
    - 자원에 대한 행위는 HTTP Method(GET, POST, PUT, DELETE)로 표현
        - GET - 해당 리소스 조회하고 해당 document에 대한 자세한 정보를 가져옴
        - POST - 해당 URI를 요청하면 리소스를 생성
        - PUT - 해당 리소스를 수정
        - DELETE - 리소스를 삭제
- Document - 문서, 객체
- Collection - 문서들의 집합, 객체들의 집합
    - http:// [restapi.example.com/sports/soccer](http://restapi.example.com/sports/soccer)/players/13에서 sports와 players collection과 soccer와 13이라는 document로 표현
    - collection은 복수로 사용
- 상태코드

| 상태코드 |  |
| --- | --- |
| 200 | 클라이언트의 요청을 정상적으로 수행 |
| 201 | 클라이언트가 어떠한 리소스 생성을 요청, 해당 리소스가 성공적으로 실행됨(POST 수행 시) |
| 400 | 클라이언트의 요청이 부적절 할 경우 사용하는 응답코드 |
| 401 | 클라이언트가 인증되지 않은 상태에서  보호된 리소스를 요청했을 때 사용 |
| 403 | 유저 인증 상태와 관계 없이 응답하고 싶지 않은 리소스를 클라이언트가 요청했을 때 사용 → 리소스는 존재 함  |
| 405 | 클라이언트가 요청한 리소스에서는 사용 불가능한 Method를 이용했을 경우 사용 |
| 301 | 클라이언트가 요청한 리소스에 대한 URI가 변경 되었을 때 사용하는 응답코드 |
| 500 | 서버에 문제가 있을 경우 사용 |

## DTO

- DTO(데이터 전송 객체) - 프로세스 간에 데이터를 전달하는 객체
    - DTO 안에는 순수하게 전달하고 싶은 데이터만 담겨있음
- DTO를 왜 사용할까?
    - 필요한 데이터만 골라서 담기
    - 보안 및 데이터 은닉
    - API 명세(규격)를 명확하게 관리
- Request DTO(NestJS)
    
    <img width="926" height="692" alt="Image" src="https://github.com/user-attachments/assets/5410ec96-a00e-4b79-a0a0-cd6ece5cacca" />
    
    - `FindUserDto`라는 '틀'을 만들어, 클라이언트로부터 `id`와 `name`이라는 데이터를 받을 때 사용할 규칙을 정의
    - **`@Exclude()`** : 이 클래스의 모든 속성(property)은 **기본적으로 무시**
    - **`@Expose()`**: 속성(property) 위에 붙어있음 → 기본 규칙(Exclude)과 달리, `id` 속성은 **특별히 노출(포함)**
    - `@Exclude()`와 `@Expose()`를 조합하면, 내가 `@Expose()`로 명시적으로 선택한 속성만 DTO에 포함시키는 **'화이트리스트(Whitelist)' 방식**이 되어 매우 안전
- Response DTO(NestJS)
    
    <img width="820" height="416" alt="Image" src="https://github.com/user-attachments/assets/023d6dc6-34a9-478b-9bc3-59acf34a7320" />
    
    - 서버가 클라이언트에게 응답을 보낼 때 `id`와 `name` 속성만 노출(포함)시키고, 그 외의 모든 것은 제외하도록 강제하는 설계도
    - 응답 DTO를 사용하면 필요한 데이터만 깔끔하게 골라서 보낼 수 있고, 민감한 정보가 실수로 노출되는 것을 완벽하게 방지
- DTO 대신 엔티티를 사용하면 기존에 받을 데이터보다 더 많은(큰) 데이터를 받게되어 오버헤드가 발생할 수 있음
