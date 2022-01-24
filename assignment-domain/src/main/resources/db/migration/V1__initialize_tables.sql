CREATE TABLE member
(
    number        BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    date_of_birth date                  NOT NULL,
    gender        VARCHAR(255)          NOT NULL,
    id            VARCHAR(255)          NOT NULL UNIQUE,
    password      VARCHAR(255)          NOT NULL,
    email         VARCHAR(255)          NOT NULL UNIQUE,
    CONSTRAINT pk_member PRIMARY KEY (number)
);

CREATE TABLE sitter
(
    number        BIGINT AUTO_INCREMENT NOT NULL,
    min_care_age  INT                   NOT NULL,
    max_care_age  INT                   NOT NULL,
    introduction  VARCHAR(255)          NOT NULL,
    member_number BIGINT                NOT NULL,
    CONSTRAINT pk_sitter PRIMARY KEY (number)
);

ALTER TABLE sitter
    ADD CONSTRAINT FK_SITTER_ON_MEMBER_NUMBER FOREIGN KEY (member_number) REFERENCES member (number);
