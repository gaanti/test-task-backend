create table item
(
    id              bigint not null auto_increment,
    creation_time   timestamp default now(),
    name            varchar(50),
    image_url       TEXT,
    count           int,
    weight_in_grams int,
    width           varchar(50),
    height          varchar(50),
    primary key (id)
);