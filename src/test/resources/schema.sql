CREATE TABLE profiles (
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar NOT NULL,
age bigint NOT NULL,
phone_number bigint NOT NULL
);

CREATE TABLE places (
id INT AUTO_INCREMENT PRIMARY KEY,
place_name varchar NOT NULL,
capacity INT NOT NULL,
price FLOAT NOT NULL
);

CREATE TABLE roles (
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(255) NOT NULL
);

CREATE TABLE users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	profile_id BIGINT,
	role_id BIGINT NOT NULL,
    login varchar NOT NULL,
	password varchar NOT NULL,
	UNIQUE (login),
    foreign key (role_id) references roles(id),
    foreign key (profile_id) references profiles(id)

);
CREATE TABLE events (
id int AUTO_INCREMENT PRIMARY KEY,
event_name varchar NOT NULL,
place_id INT NOT NULL,
status varchar NOT NULL,
start_time TIMESTAMP NOT NULL,
end_time TIMESTAMP NOT NULL,
number_of_slots INT NOT NULL,
available_slots INT NOT NULL,
creator_id INT NOT NULL,
age_limit INT NOT NULL,

foreign key (creator_id) references users(id),
foreign key (place_id) references places(id)

);

CREATE TABLE users_events (
user_id int NOT NULL,
event_id int NOT NULL,
 CONSTRAINT users_events PRIMARY KEY (
    user_id,event_id
 ),
 foreign key (user_id) references users(id),
 foreign key (event_id) references events(id)
);





