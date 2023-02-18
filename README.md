# Todo 리스트 서비스

## 환경 구성
- 코드 다운로드 후 별도의 DB나 Docker 구동 없이 실행되도록 h2 in-memory DB를 사용했습니다.
- Spring Boot (3.0.2) + Java 17
- Spring Boot 3.x에선 Java 17버전이 필요합니다. 인텔리제이로 구동하려면 설정을 Java 17로 수정해야 합니다.
    - 참고자료. [인텔리제이 Java 17 버전 구성](https://medium.com/sjk5766/spring-boot-%EB%B2%84%EC%A0%84%EC%97%90-%EB%94%B0%EB%A5%B8-java-%EB%B2%84%EC%A0%84-ff15c5ba7ecb)

## 엔티티 설명
- 크게 2개의 엔티티로 구성됩니다. (User, Todo)
  - User: Todo를 관리하는 사용자입니다.
  - Todo: 사용자에 의해 추가/수정/삭제/조회 되는 Todo 입니다. 

## 디렉토리 구조 및 파일 설명
```text
src
 |--- main
    |--- common
    |  |--- BaseEntity.js (엔티티에 공통으로 적용되는 컬럼)
    |  |--- PageResponse.js (페이지네이션 응답 파일)
    |  |--- ResponseMessage.js (API 응답에 사용되는 공통 포맷)
    |--- configure
    |  |--- FilterRegistrationConfig.js (필터 관련 설정)
    |  |--- PasswordEncoder.js (암호화, 패스워드 일치 여부 제공)
    |  |--- SecurityConfig.js (스프링 시큐리티 설정 파일)
    |--- exception
    |  |--- GlobalExceptionHandler.js (Controller에 전달된 예외 처리 담당)
    |  |--- JwtAuthenticationEntryPoint.js (jwt 인증 예외 담당)
    |  |--- JwtRequestFilter.js (jwt 요청 필터)
    |--- filter
    |  |--- HealthFilter.js (헬스체크 응답 변환 필터)
    |  |--- JWTRequestFilter.js (토큰 기반 인증 필터)
    |--- health
    |  |--- dto 
    |     |--- HealthResponse (Filter에서 사용할 응답 Response)
    |--- login
    |  |--- dto 패키지 (request, response dto 구성)
    |  |--- repository 패키지(둘 이상일때 따로 repository 패키지로 구성, 하나면 파일로 둠)
    |  |--- LoginController.java
    |  |--- LoginService.java
    |--- todo
    |  |--- domain (엔티티, Enum)
    |  |--- dto 패키지 (request, response dto 구성)
    |  |--- TodoController.java
    |  |--- TodoRepository.java
    |  |--- TodoService.java
    |--- user (todo와 동일)
    |--- util (jwt 토큰 생성, validate 담당)
    |--- Application.java
 |--- test
    |--- fixture 패키지 (테스트에 필요한 엔티티, dto를 생성하는 헬퍼 함수)
    |--- health 패키지
    |--- login 패키지
      |-- dto 패키지 (dto 테스트 함수)
      |-- ProductControllerTest.js
      |-- ProductServiceTest.js
    |--- todo (위 login 패키지와 동일, domain 테스트 추가)
    |--- user (위 todo 패키지와 동일)
    |--- util (엔티티 헬퍼가 아닌 유틸성 헬퍼 함수)
```

## API 기능 설명
```
- login API 
  - POST /login (로그인)
- todo API
  - GET /todos/recent (최신 1건의 Todo 조회)
  - GET /todos        (특정 회원의 전체 Todo 조회)
  - POST /todos       (Todo 등록)
  - PATCH /todos/1    (Todo 상태 변경)
- user API 
  - GET /users     (전체 조회, ADMIN 권한만 가능)
  - POST /users    (회원 등록)
  - DELETE /users  (회원 탈퇴)
- health API
  - GET /health (헬스 체크)
```

## API 응답 포맷 예시
- 추가/수정/삭제 시
```json
  {
    "statusCode": 200,
    "message": "",
    "data": ""
  }
```

- 조회 시
```json
  {
    "statusCode": 200,
    "message": "",
    "data": {
      "id": 1,
      "title": "     asdad ",
      "status": "TODO"
    }
  }
```

- 로그인 성공
```json
  {
      "statusCode": 200,
      "message": "",
      "data": {
          "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA0ODkzMjIyOSIsImlhdCI6MTY3NTc5Mzg2MiwiZXhwIjoxNjc1ODgwMjYyfQ.FS5F_yCrmEzn05PYBCUaKv0rMbQ1k--F8b8E1GjyNDtvShTfksEz2uR5bphht47FZgpHx7UhdCO5ETxBRSBi0g"
      }
  }
```

- 로그인 실패
```json
  {
      "statusCode": 400,
      "message": "계정 정보가 일치하지 않습니다.",
      "data": ""
  }
```

- 로그인 하지 않은 상태에서 로그인이 필요한 API 요청시
```json
  {
    "statusCode": 401,
    "message": "Full authentication is required to access this resource",
    "data": ""
  }
```

- API 요청 권한이 없는 경우
```json
  {
    "statusCode": 403,
    "message": "Access Denied",
    "data": ""
  }
```

- 요청 값이 잘못되었을 경우 (회원가입 시, 패스워드가 누락된 경우)
```json
  {
    "statusCode": 400,
    "message": "{password=공백일 수 없습니다}",
    "data": ""
  }
```

- 단 건 데이터 조회 시, 데이터가 없는 경우
```json
  {
      "statusCode": 204,
      "message": "No Content",
      "data": ""
  }
```

- 존재하지 않는 api 접근 시
```json
{
    "statusCode": 404,
    "message": "No endpoint GET /api/todos/ppp.",
    "data": ""
}
```

## 테스트 결과
![img.png](images/test.PNG)

## 설계시 고려한 것들
- 정상적인 응답과 예외 발생 시, 공통적인 format 으로 응답하길 원했습니다.
- Todo가 많을 경우를 고려해 페이징해서 응답하도록 했습니다.
- 확장성을 위해 세션 대신 토큰 방식을 채택했습니다.
- 회원 Role에 따라 접근 제한이 있으면 좋겠다는 생각이 들어 전체 회원 조회 기능 개발했습니다.
