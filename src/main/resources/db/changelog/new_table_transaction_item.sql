CREATE TABLE transaction_item (
	id varchar(255),
	tx_id varchar(255) REFERENCES transaction ON DELETE CASCADE,
	product_id varchar(255) REFERENCES product ON DELETE CASCADE,
	amt int DEFAULT 0,
	created_by varchar(255),
	created_date timestamp,
	updated_by varchar(255),
	updated_date timestamp,
	primary key(id),
	UNIQUE(tx_id, product_id)
);
