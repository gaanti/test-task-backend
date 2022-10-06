create table comment
(
    id            bigint not null auto_increment primary key,
    item_id       bigint,
    creation_time timestamp default now(),
    description   varchar(2000),
    constraint comment_model_fk foreign key (item_id) references item (id)
)