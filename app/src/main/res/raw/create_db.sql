create table if not exists movies (
    _id integer not null constraint movies_pk primary key autoincrement,
    id integer not null unique,
    title varchar(60) not null,
    release_date varchar(10) not null,
    runtime integer not null,
    overview text not null,
    marked_as_favourite boolean not null,
    vote_average real not null,
    popularity real not null,
    poster_path text not null
);

create table if not exists reviews (
    _id integer not null constraint reviews_pk primary key autoincrement,
    id integer not null unique,
    movie_id integer not null,
    author varchar(70) not null,
    content text not null,
    constraint reviews_movies foreign key (movie_Id)
        references movies (_id)
);

create table if not exists trailers (
    _id integer not null constraint trailers_pk primary key autoincrement,
    id integer not null unique,
    movie_id integer not null,
    "key" text not null,
    constraint trailers_movies foreign key (movie_Id)
        references movies (_id)
);