CREATE TABLE jt_instructor
(
    user_id BIGINT NOT NULL,
    batch   VARCHAR(255) NULL,
    CONSTRAINT pk_jt_instructor PRIMARY KEY (user_id)
);

CREATE TABLE jt_mentor
(
    user_id            BIGINT NOT NULL,
    number_of_sessions INT    NOT NULL,
    number_of_mentees  INT    NOT NULL,
    CONSTRAINT pk_jt_mentor PRIMARY KEY (user_id)
);

CREATE TABLE jt_ta
(
    user_id BIGINT NOT NULL,
    average_rating DOUBLE NOT NULL,
    CONSTRAINT pk_jt_ta PRIMARY KEY (user_id)
);

CREATE TABLE jt_user
(
    user_id  BIGINT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT pk_jt_user PRIMARY KEY (user_id)
);

CREATE TABLE msc_instructor
(
    user_id  BIGINT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    batch    VARCHAR(255) NULL,
    CONSTRAINT pk_msc_instructor PRIMARY KEY (user_id)
);

CREATE TABLE msc_mentor
(
    user_id            BIGINT NOT NULL,
    email              VARCHAR(255) NULL,
    password           VARCHAR(255) NULL,
    number_of_sessions INT    NOT NULL,
    number_of_mentees  INT    NOT NULL,
    CONSTRAINT pk_msc_mentor PRIMARY KEY (user_id)
);

CREATE TABLE msc_ta
(
    user_id  BIGINT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    average_rating DOUBLE NOT NULL,
    CONSTRAINT pk_msc_ta PRIMARY KEY (user_id)
);

CREATE TABLE sc_user
(
    user_id            BIGINT NOT NULL,
    user_type          INT    NOT NULL,
    email              VARCHAR(255) NULL,
    password           VARCHAR(255) NULL,
    average_rating DOUBLE NOT NULL,
    batch              VARCHAR(255) NULL,
    number_of_sessions INT    NOT NULL,
    number_of_mentees  INT    NOT NULL,
    CONSTRAINT pk_sc_user PRIMARY KEY (user_id)
);

CREATE TABLE tbc_instructor
(
    user_id  BIGINT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    batch    VARCHAR(255) NULL,
    CONSTRAINT pk_tbc_instructor PRIMARY KEY (user_id)
);

CREATE TABLE tbc_mentor
(
    user_id            BIGINT NOT NULL,
    email              VARCHAR(255) NULL,
    password           VARCHAR(255) NULL,
    number_of_sessions INT    NOT NULL,
    number_of_mentees  INT    NOT NULL,
    CONSTRAINT pk_tbc_mentor PRIMARY KEY (user_id)
);

CREATE TABLE tbc_ta
(
    user_id  BIGINT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    average_rating DOUBLE NOT NULL,
    CONSTRAINT pk_tbc_ta PRIMARY KEY (user_id)
);

CREATE TABLE tbc_user
(
    user_id  BIGINT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT pk_tbc_user PRIMARY KEY (user_id)
);

ALTER TABLE jt_instructor
    ADD CONSTRAINT FK_JT_INSTRUCTOR_ON_USER FOREIGN KEY (user_id) REFERENCES jt_user (user_id);

ALTER TABLE jt_mentor
    ADD CONSTRAINT FK_JT_MENTOR_ON_USER FOREIGN KEY (user_id) REFERENCES jt_user (user_id);

ALTER TABLE jt_ta
    ADD CONSTRAINT FK_JT_TA_ON_USER FOREIGN KEY (user_id) REFERENCES jt_user (user_id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);