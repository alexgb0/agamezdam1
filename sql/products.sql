create table products
(
    code  int            not null,
    name  varchar(256)   not null,
    price decimal        not null,
    iva   int default 24 not null,
    constraint products_pk
        primary key (code)
);

create unique index products_code_uindex
    on products (code);