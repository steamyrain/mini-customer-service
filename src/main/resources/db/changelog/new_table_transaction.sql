CREATE TABLE transaction (
	id varchar(255),
	nett_amt_paid numeric(11,2),
	total_amt_paid numeric(11,2),
	total_tax_paid numeric(11,2),
	tx_time timestamp not null,
	payment_status varchar(100),
	payment_method varchar(100),
	customer_id varchar(255) REFERENCES customer ON DELETE CASCADE,
	created_by varchar(255),
	created_date timestamp,
	updated_by varchar(255),
	updated_date timestamp,
	primary key(id)
);
