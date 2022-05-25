create table clients
(
    dni     varchar(9) not null,
    nom     varchar(256) not null,
    email   varchar(256) not null,
    phone   int(9)     not null,
    address varchar(256) not null,
    constraint table_name_pk
        primary key (dni)
);

create unique index table_name_dni_uindex
    on clients (dni);

create unique index table_name_email_uindex
    on clients (email);

create unique index table_name_phone_uindex
    on clients (phone);

