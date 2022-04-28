CREATE TABLE events (
id serial NOT NULL,
event_name varchar NOT NULL,
place_id serial NOT NULL,
status varchar NOT NULL,
start_time TIMESTAMP NOT NULL,
end_time TIMESTAMP NOT NULL,
number_of_slots integer NOT NULL,
available_slots integer NOT NULL,
creator_id serial NOT NULL,
age_limit integer NOT NULL,
CONSTRAINT events_pk PRIMARY KEY ("id")

);



