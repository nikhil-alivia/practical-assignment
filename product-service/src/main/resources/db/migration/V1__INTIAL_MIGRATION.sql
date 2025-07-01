CREATE TABLE prices
(
    deleted    bit                    NOT NULL,
    id         bigint IDENTITY (1, 1) NOT NULL,
    created_at datetimeoffset         NOT NULL,
    updated_at datetimeoffset,
    amount     decimal(18, 0)         NOT NULL,
    is_active  bit                    NOT NULL,
    product_id bigint,
    CONSTRAINT pk_prices PRIMARY KEY (id)
)
GO

CREATE TABLE products
(
    deleted        bit                    NOT NULL,
    id             bigint IDENTITY (1, 1) NOT NULL,
    created_at     datetimeoffset         NOT NULL,
    updated_at     datetimeoffset,
    name           varchar(50)            NOT NULL,
    description    varchar(1000)          NOT NULL,
    stock_quantity bigint                 NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
)
GO

ALTER TABLE products
    ADD CONSTRAINT uc_products_name UNIQUE (name)
GO

ALTER TABLE prices
    ADD CONSTRAINT FK_PRICES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id)
GO