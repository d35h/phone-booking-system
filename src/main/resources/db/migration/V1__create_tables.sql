create sequence phone_seq;

create table phone
(
    id int default nextval ('phone_seq') primary key,
	manufacturer varchar(255) not null,
	model varchar(255) not null,
	technology varchar(255) not null,
	bands varchar(255) not null
);

create table phone_id_to_booked_by
(
    phone_id int not null,
    booked_by varchar(255) not null,
    booked_time timestamp(0),
    constraint phone_id_to_booked_by_fk foreign key (phone_id) references phone (id)
);