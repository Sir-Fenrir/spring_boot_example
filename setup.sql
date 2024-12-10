create database if not exists messages;
use messages;

create table if not exists authors (
    id bigint auto_increment primary key,
    name varchar(255) not null
);

create table if not exists messages (
    id int auto_increment primary key,
    title varchar(64) not null,
    content varchar(255) not null,
    author_id bigint not null,
    constraint message_author_id_fk foreign key (author_id) references author (id)
);