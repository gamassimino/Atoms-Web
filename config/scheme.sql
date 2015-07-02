create database if not exists atoms;
use atoms;

drop table if exists ranks;
create table ranks(
	id integer not null auto_increment /*unique*/,
	user_id integer not null,
	games_won integer not null,
	constraint ranks_id primary key (id)
);