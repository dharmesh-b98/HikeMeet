-- drop the database if exists
drop database if exists hikeDB;

-- create the database
-- create database if not exists acme;
create database hikeDB;

-- select the database
use hikeDB;

-- creating tables
select "Creating users table..." as msg;
create table users (
    username varchar(128) not null,
    password varchar(128) not null,
    role varchar(128) not null,
    telegram_username varchar(128),
    chat_id varchar(128),
    
    constraint pk_username primary key(username)
);


select "Creating hostedhikes table..." as msg;
create table hostedhikes (
    hostedhike_id int auto_increment,
    hostname varchar(128) not null,
    country varchar(128) not null,
    hikespotname varchar(128) not null,
    dateandtime varchar(128) not null,
    sunrisetime varchar(128) not null,
    sunsettime varchar(128) not null,

    constraint pk_hostedhike_id primary key(hostedhike_id),
    constraint fk_hostname foreign key (hostname) references users(username)
);


select "Creating usersjoined table..." as msg;
create table usersjoined (
    id int auto_increment,
    hostedhike_id int not null,
    username varchar(128) not null,
    constraint pk_id primary key(id),
    constraint fk_hostedhike_id foreign key (hostedhike_id) references hostedhikes(hostedhike_id)
    on delete cascade
);

-- grant fred access to the database
-- select "Granting privileges to testfella" as msg;
-- grant all privileges on hikeDB.* to 'testfella'@'%';
-- flush privileges;
