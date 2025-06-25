create table rooms (
    id bigint not null auto_increment,
    room_number varchar(100) not null,
    room_description varchar(300) not null,

    primary key(id)
);