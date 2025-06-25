create table reservations (
	id bigint not null auto_increment,
    guest_name varchar(60) not null,
    guest_document varchar(255) not null,
    room_id bigint not null,
    check_in datetime not null,
    check_out datetime not null,
    
    primary key (id)
);

alter table reservations add constraint fk_reservations_rooms
foreign key (room_id) references rooms (id);