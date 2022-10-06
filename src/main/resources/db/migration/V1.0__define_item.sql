create table item
(
    id            bigint not null auto_increment,
    creation_time timestamp default now(),
    name          varchar(50),
    image_url     varchar(5000),
    count         int,
    weight        int,
    width         varchar(50),
    height        varchar(50),
    primary key (id)
);