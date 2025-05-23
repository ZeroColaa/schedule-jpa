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
