CREATE TABLE profiles (
id serial NOT NULL,
name varchar,
age bigint,
phone_number bigint,
CONSTRAINT profiles_pk PRIMARY KEY ("id")

);