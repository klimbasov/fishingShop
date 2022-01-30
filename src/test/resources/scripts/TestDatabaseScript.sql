drop database if exists fshop_test;
create database fShop_test;
use fShop_test;

create table types
(
    id       int         not null auto_increment primary key,
    typeName varchar(30) not null
);


create table products
(
    id       int   not null auto_increment primary key,
    name     varchar(30),
    price    float not null,
    quantity int   not null,
    typeId   int   not null,
    visible  bool  not null
);

create table users
(
    id               int         not null auto_increment primary key,
    name             varchar(30) not null,
    hashedPass       varchar(30) not null,
    roleId           tinyint     not null,
    registrationDate date        not null,
    registrationTime time        not null
);

create table usersOrders
(
    orderId      int  not null auto_increment primary key,
    userId       int  not null,
    orderingDate Date not null,
    foreign key (userId) references users (id)
);

create table ordersProducts
(
    orderId   int not null,
    productId int not null,
    quantity  int not null,
    foreign key (orderId) references usersOrders (orderId),
    foreign key (productId) references products (id)
);