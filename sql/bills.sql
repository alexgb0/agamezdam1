create table bills
(
    id         int auto_increment,
    client_id  varchar(256) not null,
    product_id int not null,
    constraint bills_pk
        primary key (id),
    constraint bills_clients_dni_fk
        foreign key (client_id) references clients (dni),
    constraint bills_products_code_fk
        foreign key (product_id) references products (code)
);

create unique index bills_id_uindex
    on bills (id);

