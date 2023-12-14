CREATE TABLE customer (
	id varchar(255),
	name varchar(255) not null,
	birthdate timestamp not null,
	birthplace varchar(255) not null,
	created_by varchar(100),
	created_at timestamp,
	updated_by varchar(100),
	updated_at timestamp,
	primary key(id)
);
