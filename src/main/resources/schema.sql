create table if not exists films (
id integer primary key,
name varchar(200),
description varchar(500),
releaseDate date,
duration integer,
rating_id integer
);

create table if not exists genre (
id integer primary key,
val varchar(50),
comm varchar(200)
);

create table if not exists genres (
id integer primary key,
film_id integer,
genre_id integer
);

create table if not exists rating (
id integer primary key,
val varchar,
comm varchar
);

create table if not exists likes (
id integer primary key,
film_id integer,
user_id integer
);

create table if not exists users (
id integer primary key,
email varchar(100),
login varchar(200),
name varchar(100),
birthday date
);

create table if not exists friends (
id integer primary key,
user_id integer,
friend_id integer,
is_accepted boolean
);

