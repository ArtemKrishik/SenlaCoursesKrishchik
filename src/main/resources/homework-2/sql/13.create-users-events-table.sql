CREATE TABLE users_events (
user_id integer NOT NULL,
event_id integer NOT NULL,
CONSTRAINT users_events_pk PRIMARY KEY ("user_id","event_id")

);