use myNewDB;

drop table authorities;
#create table users(
#username varchar(50) not null,
#password varchar(50) not null,
##enabled boolean not null default true,
#primary key(username)
#);

create table authorities(
	username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references
    users(username)
);

insert into authorities (username,authority) values ("1", "ADMIN");

select * from users;

create unique index ix_auth_username on authorities (username, authority);