CREATE TABLE child
(
    number        BIGINT AUTO_INCREMENT NOT NULL,
    gender        VARCHAR(255)          NULL,
    parent_number BIGINT                NULL,
    date_of_birth date                  NULL,
    CONSTRAINT pk_child PRIMARY KEY (number)
);

CREATE TABLE parent
(
    number        BIGINT AUTO_INCREMENT NOT NULL,
    member_number BIGINT                NULL,
    request_info  VARCHAR(255)          NULL,
    CONSTRAINT pk_parent PRIMARY KEY (number)
);

ALTER TABLE child
    ADD CONSTRAINT FK_CHILD_ON_PARENT_NUMBER FOREIGN KEY (parent_number) REFERENCES parent (number);

ALTER TABLE parent
    ADD CONSTRAINT FK_PARENT_ON_MEMBER_NUMBER FOREIGN KEY (member_number) REFERENCES member (number);
