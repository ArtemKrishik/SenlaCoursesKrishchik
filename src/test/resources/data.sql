insert into roles ( name) values ( 'ROLE_ADMIN');
insert into roles ( name) values ( 'ROLE_USER');

insert into profiles ( name, age, phone_number) values ('name', 11, 1111);
insert into profiles ( name, age, phone_number) values ('name', 11, 1111);
insert into profiles ( name, age, phone_number) values ('name', 11, 1111);

insert into places ( place_name, capacity, price)
values ( 'place', 10, 100);


insert into users ( profile_id, role_id, login, password)
values ( 1, 1, 'artem', '$2a$04$/37BokSgKBfktDPXmDnye.FLXOqdFBQnSLmL/mEvHlCIl9dTOK6.S');
insert into users ( profile_id, role_id, login, password)
values ( 2, 2, 'andrei', '$2a$04$/37BokSgKBfktDPXmDnye.FLXOqdFBQnSLmL/mEvHlCIl9dTOK6.S');
insert into users ( profile_id, role_id, login, password)
values ( 3, 2, 'nikita', '$2a$04$/37BokSgKBfktDPXmDnye.FLXOqdFBQnSLmL/mEvHlCIl9dTOK6.S');

insert into events ( event_name, place_id, status, start_time, end_time, number_of_slots, available_slots, creator_id, age_limit)
values( 'event', 1, 'COMPLITED', '2021-12-29 15:21:30.135', '2021-12-29 15:22:30.135', 120, 120, 1, 12);




