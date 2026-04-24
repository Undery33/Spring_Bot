# 프로젝트 소개
서버 관리와 이벤트 관리, 편의성 기능을 제공하는 디스코드 내 봇 입니다.

# 기술 스택
Java 21 - 장기 유지보수와 배포 안정성 측면에서 선택
최신 버전(Java 26)을 사용하지 않은 이유는 라이브러리나 배포 환경 호환성 문제가 생길 수 있다고 판단(안정성 고려)

Spring Boot - 구조 정리, 설정 관리, 의존성 주입, 테스트, 모니터링, 배포를 포함한 구조를 위해 선택
JDA 단독으로 사용하면 봇 로직은 만들 수 있지만 설정 관리, 서비스 계층 분리, Bean 관리 등 운영 구조를 구성해야 하기 때문.
Spring Boot를 사용하면 Discord 이벤트 처리 로직을 Services, Listener, Command 계층 분리 용이하고
향후 DB, Redis, 모니터링을 붙히기 쉽다고 판단.

Gradle - 의존성 관리와 빌드 자동화, CI/CD 연동

JDA - Discord Gateway 이벤트와 REST API를 다루기 위해 사용
Discord 봇은 사용자의 메세지와 Slash Command 같은 이벤트에 반응하는 구조이다.
이러한 이벤트를 Listener 방식으로 처리할 수 있어서 Spring의 Service 계층과 연결하기 좋다.

Lombok - DTO, 설정 클래스, Entity에서 반복되는 getter, constructor, builder 코드를 줄이기 위해 사용

Validation - 명령어 입력 값이나 설정 값이 잘못 들어오는 것을 서비스 로직 전에 검증하기 위해 사용
잘못된 데이터나 비즈니스 로직까지 들어오지 않게 막는 것이 목적

Actuator - 운영 상태를 확인하기 위해 사용, health check와 metrics를 통해 어플리케이션이 정상 동작하는지 확인 가능함.
AWS EC2나 Docker 환경에 배포했을 때 /actuator/health를 통해 봇 서버 상태를 확인할 수 있음.
추후 CloudWatch나 외부 모니터링 도구와 연결하기도 좋음.

# 주요 기능
- 관리자의 손쉬운 역할 추가
- TTS 사용
- GPT를 이용한 챗봇
- 추가 예정
