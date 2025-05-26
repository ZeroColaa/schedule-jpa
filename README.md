
#  Schedule-jpa

##  프로젝트 개요

* 작성자(Author) + 일정(Schedule) + 댓글(Comment) 관리 API
* 로그인/로그아웃, 세션 기반 인증 처리
* RESTful 설계 및 Global Exception Handling
* 일정 페이징 조회 + 댓글 개수 포함 응답
* 기술 스택: Spring Boot, JPA, MySQL


---

##  ERD (Entity Relationship Diagram)

```
Author (1) ─── (N) Schedule (1) ─── (N) Comment
```
<img width="799" alt="image" src="https://github.com/user-attachments/assets/d0bcc74b-c4ad-4e74-8e12-93340d225ba5" />

---

##  API 명세서

| 기능         | Method | URI                                     | Request 예시                         | Response 예시                           | 상태코드           |
| ---------- | ------ | --------------------------------------- | ---------------------------------- | ------------------------------------- | -------------- |
| 회원가입       | POST   | `/authors/signup`                       | `{ userName, email, password }`    | `{ id, userName, email }`             | 201 CREATED    |
| 로그인        | POST   | `/authors/login`                        | `{ email, password }`              | `{ id, userName, email }` + 세션 저장     | 200 OK         |
| 로그아웃       | POST   | `/authors/logout`                       | 세션 삭제 및 쿠키 제거                      | -                                     | 204 No Content |
| 사용자 조회     | GET    | `/authors/{id}`                         | -                                  | `{ id, userName, email }`             | 200 OK         |
| 정보 수정      | PUT    | `/authors/{id}`                         | `{ userName, email }`              | `{ id, userName, email }`             | 200 OK         |
| 비밀번호 수정    | PUT    | `/authors/{id}/password`                | `{ currentPassword, newPassword }` | -                                     | 204 No Content |
| 일정 생성      | POST   | `/schedules`                            | `{ title, contents }`              | `{ id, title, contents, authorName }` | 201 CREATED    |
| 일정 단건 조회   | GET    | `/schedules/{id}`                       | -                                  | 일정 정보 + 작성자 이름                        | 200 OK         |
| 내 일정 목록 조회 | GET    | `/schedules/me`                         | 로그인 필요 (세션 필요)                              | 일정 목록                                 | 200 OK         |
| 전체 일정 조회   | GET    | `/schedules/all`                        | -                                  | 일정 목록                                 | 200 OK         |
| 일정 페이징 조회  | GET    | `/schedules/paged?page=0&size=10`       | 쿼리 파라미터                            | 페이지 응답 (댓글 수 포함)                      | 200 OK         |
| 일정 수정      | PUT    | `/schedules/{id}`                       | `{ title, contents }`              | 수정된 일정 정보                             | 200 OK         |
| 일정 삭제      | DELETE | `/schedules/{id}`                       | -                                  | -                                     | 204 No Content |
| 댓글 작성      | POST   | `/schedules/{scheduleId}/comments`      | `{ content }`                      | 댓글 정보                                 | 201 CREATED    |
| 댓글 목록 조회   | GET    | `/schedules/{scheduleId}/comments`      | -                                  | 댓글 리스트                                | 200 OK         |
| 댓글 수정      | PUT    | `/schedules/{scheduleId}/comments/{id}` | `{ content }`                      | 수정된 댓글 정보                             | 200 OK         |
| 댓글 삭제      | DELETE | `/schedules/{scheduleId}/comments/{id}` | -                                  | -                                     | 204 No Content |

---

## schema.sql

```
/* ---------- AUTHOR 테이블 ---------- */
CREATE TABLE author
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(30)  NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  DATETIME(6),
    modified_at DATETIME(6)
);

/* ---------- SCHEDULE 테이블 ---------- */
CREATE TABLE schedule
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    contents    LONGTEXT,
    author_id   BIGINT       NOT NULL,
    created_at  DATETIME(6),
    modified_at DATETIME(6),
    CONSTRAINT fk_schedule_author
        FOREIGN KEY (author_id)
            REFERENCES author (id)
            ON DELETE RESTRICT
);

/* ---------- COMMENT 테이블 ---------- */
CREATE TABLE comment
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    content     TEXT   NOT NULL,
    author_id   BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    created_at  DATETIME(6),
    modified_at DATETIME(6),
    CONSTRAINT fk_comment_author
        FOREIGN KEY (author_id)
            REFERENCES author (id)
            ON DELETE RESTRICT,
    CONSTRAINT fk_comment_schedule
        FOREIGN KEY (schedule_id)
            REFERENCES schedule (id)
            ON DELETE RESTRICT
);

```
---

##  실행 방법

### 1. MySQL 실행 및 DB 생성

```sql
CREATE DATABASE schedule_jpa;
```

> 테이블은 엔티티 기반으로 **애플리케이션 실행 시 자동 생성**

---

### 2. DB 연결 정보 설정

`src/main/resources/application.properties` 또는 `application-sample.properties` 에 다음과 같이 설정:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/schedule_jpa
spring.datasource.username=본인_DB_계정명
spring.datasource.password=본인_DB_비밀번호
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

>  `ddl-auto=create` 옵션을 통해 실행 시 테이블이 자동 생성됩니다.

---

### 3. 애플리케이션 실행

* IntelliJ의 `ScheduleJpaApplication` 클래스에서 Run 실행
* 또는 터미널에서 다음 명령어 입력:

  ```bash
  ./gradlew bootRun
  ```

---

### 4. Postman 또는 브라우저로 API 테스트

* 예: `POST /authors/signup`, `POST /schedules`, `GET /schedules/me` 등


---

##  전체 흐름 구조

```
Client (Postman / 웹 브라우저)
       ⇅
Controller
       ⇅
Service
       ⇅
Repository (Spring Data JPA)
       ⇅
MySQL (author, schedule, comment)
```

---

##  트러블슈팅

>  [schedule\_jpa 트러블 슈팅 기록](https://velog.io/@eggtart21/schedulejpa-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%EA%B8%B0%EB%A1%9D)


