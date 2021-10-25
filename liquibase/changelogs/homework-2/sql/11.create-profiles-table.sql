CREATE TABLE profiles (
id serial NOT NULL,
name varchar NOT NULL,
age bigint NOT NULL,
phone_number bigint NOT NULL,
CONSTRAINT profiles_pk PRIMARY KEY ("id")

);