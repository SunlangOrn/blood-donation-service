CREATE TABLE category
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    media_id    BIGINT                NULL,
    created_at  datetime              NOT NULL,
    modified_at datetime              NULL,
    deleted_at  datetime              NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE device
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    token       VARCHAR(255)          NULL,
    user_id     BIGINT                NULL,
    created_at  datetime              NOT NULL,
    modified_at datetime              NULL,
    deleted_at  datetime              NULL,
    CONSTRAINT pk_device PRIMARY KEY (id)
);

CREATE TABLE donation
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255)          NULL,
    location     VARCHAR(255)          NULL,
    phone_number VARCHAR(255)          NULL,
    quantity     INT                   NULL,
    time_expired datetime              NULL,
    note         VARCHAR(255)          NULL,
    type_blood   VARCHAR(255)          NULL,
    status       VARCHAR(255)          NULL,
    donor_id     BIGINT                NULL,
    media_id     BIGINT                NULL,
    created_at   datetime              NOT NULL,
    modified_at  datetime              NULL,
    deleted_at   datetime              NULL,
    CONSTRAINT pk_donation PRIMARY KEY (id)
);

CREATE TABLE donation_action
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    quantity     INT                   NULL,
    status       VARCHAR(255)          NULL,
    is_confirmed BIT(1)                NULL,
    donation_id  BIGINT                NULL,
    user_id      BIGINT                NULL,
    created_at   datetime              NOT NULL,
    modified_at  datetime              NULL,
    deleted_at   datetime              NULL,
    CONSTRAINT pk_donation_action PRIMARY KEY (id)
);

CREATE TABLE file_media
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    file_name   VARCHAR(255)          NULL,
    file_type   VARCHAR(255)          NULL,
    file_url    VARCHAR(255)          NULL,
    file_size   BIGINT                NULL,
    created_at  datetime              NOT NULL,
    modified_at datetime              NULL,
    deleted_at  datetime              NULL,
    CONSTRAINT pk_file_media PRIMARY KEY (id)
);

CREATE TABLE notification
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    title               VARCHAR(255)          NULL,
    message             VARCHAR(255)          NULL,
    type                VARCHAR(255)          NULL,
    reference_action_id BIGINT                NULL,
    reference_post_id   BIGINT                NULL,
    user_id             BIGINT                NULL,
    created_at          datetime              NOT NULL,
    modified_at         datetime              NULL,
    deleted_at          datetime              NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE password_reset_token
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    code        VARCHAR(255)          NULL,
    expiry_time time                  NULL,
    user_id     BIGINT                NULL,
    created_at  datetime              NOT NULL,
    modified_at datetime              NULL,
    deleted_at  datetime              NULL,
    CONSTRAINT pk_password_reset_token PRIMARY KEY (id)
);

CREATE TABLE post
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    description   VARCHAR(1000)         NULL,
    status        BIT(1)                NULL,
    user_id       BIGINT                NULL,
    category_id   BIGINT                NULL,
    media_id      BIGINT                NULL,
    public_at     datetime              NULL,
    created_at    datetime              NOT NULL,
    modified_at   datetime              NULL,
    deleted_at    datetime              NULL,
    CONSTRAINT pk_post PRIMARY KEY (id)
);

CREATE TABLE read_notification
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    read_at         datetime              NULL,
    user_id         BIGINT                NULL,
    notification_id BIGINT                NULL,
    created_at      datetime              NOT NULL,
    modified_at     datetime              NULL,
    deleted_at      datetime              NULL,
    CONSTRAINT pk_read_notification PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    code        VARCHAR(255)          NULL,
    name        VARCHAR(255)          NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    firstname      VARCHAR(255)          NULL,
    lastname       VARCHAR(255)          NULL,
    phone_number   VARCHAR(255)          NULL,
    phone_code     VARCHAR(255)          NULL,
    email          VARCHAR(255)          NULL,
    password       VARCHAR(255)          NULL,
    location       VARCHAR(255)          NULL,
    type_blood     VARCHAR(255)          NULL,
    status         BIT(1)                NULL,
    is_verify_user BIT(1)                NULL,
    is_verify_otp  BIT(1)                NULL,
    role_id        BIGINT                NULL,
    media_id       BIGINT                NULL,
    profile        VARCHAR(255)          NULL,
    created_at     datetime              NOT NULL,
    modified_at    datetime              NULL,
    deleted_at     datetime              NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_verification
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    verification_code VARCHAR(255)          NULL,
    expiry_time       time                  NULL,
    user_id           BIGINT                NULL,
    created_at        datetime              NOT NULL,
    modified_at       datetime              NULL,
    deleted_at        datetime              NULL,
    CONSTRAINT pk_user_verification PRIMARY KEY (id)
);


ALTER TABLE category ADD CONSTRAINT uc_category_media UNIQUE (media_id);
ALTER TABLE donation ADD CONSTRAINT uc_donation_media UNIQUE (media_id);
ALTER TABLE post ADD CONSTRAINT uc_post_media UNIQUE (media_id);
ALTER TABLE user ADD CONSTRAINT uc_user_media UNIQUE (media_id);



ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_media (id);

ALTER TABLE device
    ADD CONSTRAINT FK_DEVICE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE donation_action
    ADD CONSTRAINT FK_DONATION_ACTION_ON_DONATION FOREIGN KEY (donation_id) REFERENCES donation (id);

ALTER TABLE donation_action
    ADD CONSTRAINT FK_DONATION_ACTION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE donation
    ADD CONSTRAINT FK_DONATION_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_media (id);

ALTER TABLE donation
    ADD CONSTRAINT FK_DONATION_ON_USER FOREIGN KEY (donor_id) REFERENCES user (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_REFERENCE_ACTION FOREIGN KEY (reference_action_id) REFERENCES donation_action (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_REFERENCE_POST FOREIGN KEY (reference_post_id) REFERENCES post (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE password_reset_token
    ADD CONSTRAINT FK_PASSWORD_RESET_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_media (id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE read_notification
    ADD CONSTRAINT FK_READ_NOTIFICATION_ON_NOTIFICATION FOREIGN KEY (notification_id) REFERENCES notification (id);

ALTER TABLE read_notification
    ADD CONSTRAINT FK_READ_NOTIFICATION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_media (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_verification
    ADD CONSTRAINT FK_USER_VERIFICATION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);


ALTER TABLE notification ADD reference_donation_id BIGINT NULL;

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_REFERENCE_DONATION FOREIGN KEY (reference_donation_id) REFERENCES donation (id);


