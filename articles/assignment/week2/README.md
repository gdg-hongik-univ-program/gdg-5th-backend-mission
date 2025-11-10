# Mission Course Week2
지난 주차에는 `Controller` 와 `DTO` 를 만들어봤습니다.  
`Controller` 는 프론트엔드와 직접적으로 통신하는 영역으로,  
이 내용을 바탕으로 API 가 구성됩니다.  

이번 주차에는 1주차 과제를 바탕으로, 가독성 있게 리팩토링을 해보려고 합니다.

### 이번 주차 학습 목차
* `Service` 계층을 공부하고, 1주차 과제에 적용하기
* 객체지향적으로 코드 작성하기
* `JavaDoc` 의 도입을 통한 문서화하기

## Service Layer

### 개요
`Controller` 의 역할은 HTTP 프로토콜을 통해  
프론트엔드로부터 Request 를 받고, 이를 Response 로서 응답하는 것입니다.  
이 요청에 대해 올바른 요청인지, 올바른 응답을 보낼 수 있는지 등을 담당하죠.  

하지만, `Controller` 만으로는 1주차의 명세를 모두 구현하기 어렵거나,  
너무 길다는 생각이 들었을 겁니다.  
 
리팩토링을 통해 과도한 책임을 지고 있는 `Controller` 를 나눠봅시다.  
`Service` 계층입니다.

`Controller` 에 있는 통신 관련 코드를 제외하고서는 모두 `Service` 에 옮기고자 합니다.  
즉, `Controller` 는 `Service` 에서 전달해 준 최종 데이터를 그대로 전달하는 역할을 합니다.  
`Controller` 는 요청하고, `Service` 는 이에 응답합니다.  

예를 들어 보게습니다.  
1주차의 재고 검색을 떠올려보면, 크게 다음의 과정으로 나눠볼 수 있습니다.
* 프론트엔드로부터 재고 검색 요청
* `Controller`가 이를 수신
* ???
* `Controller` 가 결과를 송신

기존의 `Controller` 만 구현된 코드는 `???` 까지 모두 처리합니다.  
이를 좀 더 분리해 봅시다.  

재고 검색을 요청할 때, `물건의 이름`을 함께 전달받습니다.  
이 이름을 통해서, 데이터를 검색하고, 그 정보를 가공해서 전달해 줍니다.
* `물건의 이름` 을 바탕으로 데이터 검색 요청
* 데이터를 검색, 결과에 따라 다른 결과값 반환

아하, 이름을 바탕으로 검색하는 기능을 Service 계층이 담당해 주면 될 것 같습니다.  

방금 저희는 "관심사의 분리" 라는 행위를 하였습니다.  
기존의 과한 책임을 맡고 있는, 구체적으로 "요청 처리"와 "데이터 처리" 를 맡고 있는 `Controller class` 를  
`Controller` 와 `Service` 로 나눴습니다. (다음에 Repository 도 같은 관점으로 분리하게 됩니다.)

### 미션 내용
`Controller` 에 모두 담긴 로직을 좀 세분화해보고자 합니다.  
이를 위해서 다음의 키워드를 바탕으로 개념을 공부해봅시다.  
키워드와 함께 링크도 첨부했습니다. 그 외로도 스스로 찾아서 공부해봅시다!
* [관심사의 분리](https://ko.wikipedia.org/wiki/%EA%B4%80%EC%8B%AC%EC%82%AC_%EB%B6%84%EB%A6%AC)
* [서비스 계층](https://tjdtls690.github.io/studycontents/java/2023-04-24-service_layer/)
* [Layered Architecture](https://xxeol.tistory.com/26)
  * 결합도와 응집도
* [MVC 패턴](https://www.youtube.com/watch?v=Yzx-z6kCD2A)

공부가 완료되고 나서는 1주차의 코드를 고쳐봅시다.

#### TODO
* [ ] Service 계층에 대해 공부하기
* [ ] Service 계층을 1주차 코드에 적용해보기

#### 심화 내용
서비스 계층은 흔히 "비지니스 로직이 담기는 곳" 으로 인식되곤 합니다.  
정말 그런가요?  

> "Indicates that an annotated class is a "Service",  
> originally defined by Domain-Driven Design (Evans, 2003) as  
> "an operation offered as an interface that stands alone in the model, with no encapsulated state."

Service annotation 에 적힌 공식 문서의 일부입니다. 여기서 이야기하고 싶은 것은  
DDD 라는 디자인에서 온 annotation 이라는 말이 존재합니다.  

이에 대해서 조금 더 살펴보면, Domain Service 와 Application Service 라는 개념이 등장합니다.  
[이 자료](https://www.borntodare.me/ddd/domain_service_vs_app_service)를 한번 참고해 보세요.  

[카카오페이 블로그](https://tech.kakaopay.com/post/backend-domain-driven-design/)에서 실무에 어떻게 적용했는지도 살펴보세요.

## 객체지향적 코드 작성하기
### 개요
프로젝트가 커질수록 가독성은 중요해집니다.   
읽기 어려운 코드는 유지보수하기도, 기능을 구현하기도 어려워집니다.

가독성을 위한 첫걸음이 객체지향적으로 코드를 작성하고자 하는 것입니다.  
객체지향은 현실 세계의 물건에 비유하여 작성하는 방법으로 물건 간의 협력으로 문제를 해결하는 방법입니다.  

제가 [예전 미션코스에 작성해둔 글](https://github.com/gdg-hongik-univ-program/gdg-4th-backend-mission/blob/main/articles/assignment_spec/week2/articles/object.md)
를 한번 참고해 주세요.  
위의 글을 간단히 요약하자면 다음과 같습니다.
* 독립적인 객체가 협력하여, 문제를 해결한다.
* 객체에는 책임, 역할이 존재하며, 객체는 메세지를 통해 소통한다.

이러한 객체지향은 `결합도와 응집도` 를 개선하는 하나의 방법이기도 합니다.  
결합도가 낮고, 응집도가 높은 코드는 추가 기능 구현과 리팩토링을 쉽게 만듭니다.

### 미션 내용
객체지향에 대해 키워드를 찾아보고 공부해봅시다.  
따로 키워드는 제공하지 않겠습니다.  
이유는 이론보다는 실전이 더 중요한 부분이기 때문에 개념만 익히는 정도로 넘어가면 됩니다.  
[이 자료](https://inpa.tistory.com/entry/OOP-%F0%9F%92%A0-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%EC%84%A4%EA%B3%84%EC%9D%98-5%EA%B0%80%EC%A7%80-%EC%9B%90%EC%B9%99-SOLID)
 를 참고해보세요.  

실습으로 1주차 과제에 대해서 객체로 나눠보는 연습을 해보겠습니다.  
1주차 명세서를 보고 다음을 고민해서 WIL 에 작성해주세요. 별개의 문서로 작성해주시면 됩니다!
* 어떤 객체로 나누면 좋을지
* 어떤 책임을 가지면 좋을지
* 어떻게 협력하여 문제를 해결할지

#### TODO
- [ ] 객체지향에 대해 공부하기
- [ ] 1주차 과제를 바탕으로 객체 분석하고, 결과를 md 으로 작성하기

## JavaDoc 도입하기
### 개요
지난주에 배운 swagger 는 프론트엔드가 API 를 편리하게 참고하고자 만든 문서입니다.  
백엔드끼리도 협업과 가독성을 위해서 `javaDoc` 이라는 문서를 작성하곤 합니다.

해당 메서드가 어떤 기능인지, 어떤 매개변수를 받는지,  
어떤 효과가 일어나는지, Exception 이 언제 발생하는지 등등의 내용이 적히곤 합니다.  

이에 대해 정리한 [예전 문서](https://github.com/gdg-hongik-univ-program/gdg-4th-backend-mission/blob/main/articles/assignment_spec/week3/spec_1.md#%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EC%A3%BC%EC%84%9D-javadoc-%EA%B3%BC-swagger)
를 참고해 주세요.

### 미션 내용
배운 내용을 바로 사용해봅시다.  
1주차에 작성한 메소드에 대해, 다른 팀원이 이해할 수 있도록 javaDoc 을 작성해주세요!  

#### TODO
- [ ] JavaDoc 공부하기
- [ ] 1주차 과제에 적용해보기

### TODO 정리
- [ ] 배운 내용을 WIL 으로 300자 이상 정리하기
- [ ] 서비스 계층을 공부하고, 1주차 과제 리팩토링하기
- [ ] 객체지향을 공부하고, 1주차 과제에 적용해보기
- [ ] JavaDoc 을 공부하고, 1주차 과제에 적용해보기