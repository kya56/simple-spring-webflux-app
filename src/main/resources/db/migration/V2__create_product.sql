CREATE TABLE IF NOT EXISTS product
(
    id                 SERIAL         PRIMARY KEY,
    name               VARCHAR(225)   NOT NULL,
    shop_id           INTEGER        NOT NULL,
    price              DECIMAL(13, 2) NOT NULL DEFAULT 0,
    quantity           INTEGER        NOT NULL DEFAULT 0,
    created_date       TIMESTAMP      NOT NULL DEFAULT current_timestamp,
    created_by         VARCHAR(50)    NOT NULL,
    last_modified_by   VARCHAR(50)             DEFAULT NULL,
    last_modified_date TIMESTAMP      NULL     DEFAULT NULL,
    CONSTRAINT fk_shop FOREIGN KEY(shop_id) REFERENCES shop(id)
);