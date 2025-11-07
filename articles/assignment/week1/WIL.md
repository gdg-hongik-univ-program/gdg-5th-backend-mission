# Restful Api
## Rest API의 구성
- 자원(URI)
- 행위(HTTP METHOD)
- 표현

## Rest의 특징
- Uniform Interface
- Stateless
- Cacheable
- Self-descriptiveness
- Client-Server
- 계층형 구조

## Rest API 디자인 방법
- URI는 정보의 자원을 표현
- 자원에 대한 행위는 HTTP METHOD로 표현

## HTTP METHOD
- Post: 리소스 생성
- Get: 리소스 조회
- Put: 리소스 수정
- Delete: 리소스 삭제

## HTTP 응답 상태코드
- 200: 정상 수행
- 400: 클라이언트의 요청이 부적절
- 301: 클라리언트가 요청한 리소스에 대한 URI가 변경
- 500: 서버에 문제가 있음

# DTO

## DTO란?
프로세스간에 데이터를 전달하는 객체

## Request DTO 의 필요성
- 만일 Request 요청에 DTO를 사용하지 않고 Entity를 그대로 사용하게 되면 불필요한 정보까지 요청에 담겨버리는 상황이 발생
- 또한, Entity에 변경사항이 생기는 순간 엔티티를 사용한 다른 모든 API에 영항이 가게 됨

## Response DTO의 필요성
- 만일 Reponse에 Entity를 그대로 사용하면 불필요한 정보가 프론트엔드로 전송됨
- 또한, Entity의 설계가 그대로 노출돼 보안 사고의 위험성이 증가

