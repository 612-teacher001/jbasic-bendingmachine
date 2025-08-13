DROP TABLE IF EXISTS products;

CREATE TABLE products(
		id       SERIAL,
		name     VARCHAR(20) NOT NULL,
		price    INTEGER     NOT NULL,
		quantity INTEGER     NOT NULL DEFAULT 10
);

ALTER TABLE products ADD CONSTRAINT IDX_products_PK PRIMARY KEY (id);

