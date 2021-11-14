CREATE TABLE events (
id serial NOT NULL,
event_name varchar NOT NULL,
place_id serial NOT NULL,
status varchar NOT NULL,
date DATE ,
start_time DATE ,
number_of_people integer NOT NULL,
creator_id serial NOT NULL,
age_limit integer NOT NULL,
CONSTRAINT events_pk PRIMARY KEY ("id")

);



