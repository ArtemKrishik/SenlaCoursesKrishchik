CREATE TABLE users_events (
user_id serial NOT NULL,
event_id serial NOT NULL,
CONSTRAINT users_events_pk PRIMARY KEY ("user_id","event_id")

);