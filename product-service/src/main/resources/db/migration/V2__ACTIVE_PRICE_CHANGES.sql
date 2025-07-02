ALTER TABLE products
    ADD active_price_id bigint
GO

ALTER TABLE products
    ADD CONSTRAINT uc_products_active_price UNIQUE (active_price_id)
GO

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_ACTIVE_PRICE FOREIGN KEY (active_price_id) REFERENCES prices (id)
GO

DECLARE @sql [nvarchar](MAX)
SELECT @sql = N'ALTER TABLE prices DROP CONSTRAINT ' + QUOTENAME([df].[name])
FROM [sys].[columns] AS [c]
         INNER JOIN [sys].[default_constraints] AS [df] ON [df].[object_id] = [c].[default_object_id]
WHERE [c].[object_id] = OBJECT_ID(N'prices')
  AND [c].[name] = N'is_active'
EXEC sp_executesql @sql
GO

ALTER TABLE prices
    DROP COLUMN is_active
GO