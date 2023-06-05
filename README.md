## Celebring 백엔드

### 환경 설정

- Spring Boot 3.0.6
- Java 17
- Spring-Data JPA
- MariaDB

### ERD

- https://github.com/t-world-team/celebring_be/wiki/ERD

### API 명세

- http://celebring-api.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html
- http://localhost:8090/swagger-ui/index.html

### github branch 관리

- main : 항상 최신 상태, 새로운 브랜치는 main에서 만든다.
- dev : 테스트를 위한 브랜치, 테스트가 완료되면 main으로 merge 한다.
- feature : 새로운 기능을 추가하거나 버그를 해결하기 위한 브랜치, 완료되면 dev 브랜치로 merge
