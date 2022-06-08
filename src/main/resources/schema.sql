create table if not exists genre (
id integer primary key,
val varchar(50),
comm varchar(200)
);

create table if not exists rating (
id integer primary key,
val varchar,
comm varchar
);

create table if not exists users (
id integer primary key,
email varchar(100),
login varchar(200),
name varchar(100),
birthday date
);

create table if not exists films (
id integer primary key,
name varchar(200),
description varchar(500),
releaseDate date,
duration integer,
rating_id integer references rating (id)
);

create table if not exists genres (
id integer primary key,
film_id integer references films (id) on delete cascade,
genre_id integer references genre (id)
);

create table if not exists likes (
id integer primary key,
film_id integer references films (id) on delete cascade,
user_id integer references users (id) on delete cascade
);

create table if not exists friends (
id integer primary key,
user_id integer references users (id) on delete cascade,
friend_id integer references users (id) on delete cascade,
is_accepted boolean
);

