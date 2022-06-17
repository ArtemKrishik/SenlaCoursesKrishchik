CREATE TABLE users (
	id serial NOT NULL,
	profile_id BIGINT,
	role_id BIGINT NOT NULL,
    login varchar NOT NULL,
	password varchar NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	UNIQUE (login)
);
