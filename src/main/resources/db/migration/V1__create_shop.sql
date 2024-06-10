CREATE TABLE IF NOT EXISTS shop
(
    id                 SERIAL         PRIMARY KEY,
    name               VARCHAR(225)   NOT NULL,
    created_date       TIMESTAMP      NOT NULL DEFAULT current_timestamp,
    created_by         VARCHAR(50)    NOT NULL,
    last_modified_by   VARCHAR(50)             DEFAULT NULL,
    last_modified_date TIMESTAMP      NULL     DEFAULT NULL
);