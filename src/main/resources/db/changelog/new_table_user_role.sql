CREATE TABLE users (
	id varchar(255),
	username varchar(255) not null,
	email timestamp not null,
	password varchar(255) not null,
	role_id varchar(255) not null REFERENCES role,
	created_by varchar(100),
	created_at timestamp,
	updated_by varchar(100),
	updated_at timestamp,
	primary key(id)
);
