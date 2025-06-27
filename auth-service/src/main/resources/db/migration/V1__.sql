CREATE TABLE roles
(
    id   bigint IDENTITY (1, 1) NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
)
    GO

CREATE TABLE user_roles
(
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
)
    GO

CREATE TABLE users
(
    id                     bigint IDENTITY (1, 1)            NOT NULL,
    username               varchar(50)  NOT NULL,
    email                  varchar(255) NOT NULL,
    password               varchar(255) NOT NULL,
    first_name             varchar(255),
    last_name              varchar(255),
    is_enabled             bit
        CONSTRAINT DF_users_is_enabled DEFAULT 1             NOT NULL,
    is_account_expired     bit
        CONSTRAINT DF_users_is_account_expired DEFAULT 0     NOT NULL,
    is_account_locked      bit
        CONSTRAINT DF_users_is_account_locked DEFAULT 0      NOT NULL,
    is_credentials_expired bit
        CONSTRAINT DF_users_is_credentials_expired DEFAULT 0 NOT NULL,
    created_at             datetime     NOT NULL,
    updated_at             datetime     NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
)
    GO

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name)
    GO

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email)
    GO

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username)
    GO

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id)
    GO

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id)
    GO