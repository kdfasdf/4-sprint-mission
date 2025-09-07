
[![codecov](https://codecov.io/github/kdfasdf/4-sprint-mission/branch/sprint8/graph/badge.svg?token=Z4ZWRVXB5J)](https://codecov.io/github/kdfasdf/4-sprint-mission)
### REST API 설계원칙 준수
- 리소스 중심의 URI 설계
  - GET /api/users/{userId}
- 명확한 상태코드 응답 제공
  - request : DELETE /api/users/{userId}
  - response : ResponseEntity.noContent().build()
- 필터링, 정렬, 페이징에 쿼리 파라미터 사용
  - request : /channels?userId=d3ee2929-212b-4077-af84-694a0e69b8e1

### 예외 처리
- Global
  - GlobalHandlerExceptionResolver를 통한 전역 예외 처리
    - ErrorResponse(String message, String code, ins status, List\<FieldError> errors, List\<ConstraintViolationError> violationErrors) 형식으로 응답형식 통일
  - 커스텀 예외를 발생시키는 경우 상황에 맞는 오류 메시지를 전달하여 서버 내부 오류 메시지 노출 최소화
- Controller
  - bean validation을 통한 요청 검증
- Service
  - 도메인과 비즈니스 로직 상황에 맞는 커스텀 예외 발생

### OOP
- 무분별한 @Setter 방지를 통한 캡슐화 원칙 준수
  - 수정 가능한 필드에 대해서 비즈니스적인 의미를 가지는 setter 메서드 구현

### Test Code
- 슬라이스 테스트
  - mockito 라이브러리를 사용하여 controller, repository계층 슬라이스 테스트, 서비스 계층 단위테스트를 통한 계층 별 동작 검증
- 통합 테스트
  - controller 계층 통합테스트를 진행하여 프로덕션 코드 안정성 보장

### Logging
- MDC Interceptor
  - 요청별 고유 ID(requestId) 요청 생성 및 HTTP 메서드, 요청 경로 정보 자동 수집
  - afterCompletion에서 MDC 정리를 통한 메모리 누수 방지
- AOP
  - MethodLoggingAspect를 통한 비즈니스 로직 요청 응답 추적
  - LogParameterFormatter를 통한 로그 출력 형식 표준화
    - 리플렉션을 통한 요청 필드 상세 분석
    - 민감정보 마스킹

### Deploy
- docker
  - 멀티 스테이지 빌드를 통한 이미지 경량화

### CI/CD
  - 

