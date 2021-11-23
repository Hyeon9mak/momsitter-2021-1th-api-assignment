CREATE TABLE member
(
    number        BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    date_of_birth date                  NULL,
    gender        VARCHAR(255)          NULL,
    id            VARCHAR(255)          NULL,
    password      VARCHAR(255)          NULL,
    email         VARCHAR(255)          NULL,
    CONSTRAINT pk_member PRIMARY KEY (number)
);

CREATE TABLE sitter
(
    number        BIGINT AUTO_INCREMENT NOT NULL,
    min_care_age  INT                   NULL,
    max_care_age  INT                   NULL,
    introduction  VARCHAR(255)          NULL,
    member_number BIGINT                NULL,
    CONSTRAINT pk_sitter PRIMARY KEY (number)
);

ALTER TABLE sitter
    ADD CONSTRAINT FK_SITTER_ON_MEMBER_NUMBER FOREIGN KEY (member_number) REFERENCES member (number);
