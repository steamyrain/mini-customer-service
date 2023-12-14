CREATE TABLE product_tax (
	id varchar(255),
	product_id varchar(255) not null REFERENCES product ON DELETE CASCADE,
	tax_id varchar(255) not null REFERENCES tax ON DELETE CASCADE,
	primary key(id),
	UNIQUE(product_id, tax_id)
);
