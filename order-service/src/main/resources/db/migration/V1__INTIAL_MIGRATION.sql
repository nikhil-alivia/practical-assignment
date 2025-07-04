CREATE TABLE order_items
(
    deleted    bit                    NOT NULL,
    id         bigint IDENTITY (1, 1) NOT NULL,
    created_at datetimeoffset               NOT NULL,
    updated_at datetimeoffset,
    order_id   bigint,
    price_id   bigint                 NOT NULL,
    quantity   int                    NOT NULL,
    line_total decimal(18, 0)         NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id)
)
GO

CREATE TABLE orders
(
    id         bigint IDENTITY (1, 1) NOT NULL,
    created_at datetimeoffset               NOT NULL,
    updated_at datetimeoffset,
    user_id    bigint                 NOT NULL,
    status     varchar(20)            NOT NULL,
    total      decimal(18, 0)         NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
)
GO

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id)
GO