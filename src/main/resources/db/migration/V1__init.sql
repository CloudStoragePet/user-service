CREATE SEQUENCE IF NOT EXISTS user_id_sequence START WITH 1 INCREMENT BY 50;
CREATE TABLE users
(
    id              BIGINT       NOT NULL,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    email_confirmed BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

CREATE SEQUENCE IF NOT EXISTS role_id_sequence START WITH 1 INCREMENT BY 50;
CREATE TABLE roles
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);
ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');


