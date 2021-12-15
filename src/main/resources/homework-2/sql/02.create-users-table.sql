CREATE TABLE users (
	id serial NOT NULL,
	profile_id serial,
	role_id serial,
	login varchar NOT NULL,
	password varchar NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);
