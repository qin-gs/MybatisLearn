
-- auto-generated definition
create table blog
(
    id     varchar(32)   not null
        primary key,
    title  varchar(128)  null,
    body   varchar(1024) null,
    userid varchar(32)   null
)
    charset = utf8mb4;

-- auto-generated definition
create table comment
(
    id      varchar(32)  not null
        primary key,
    content varchar(512) null,
    user_id varchar(32)  null
)
    charset = utf8mb4;
-- auto-generated definition
create table user
(
    id        varchar(32) not null
        primary key,
    name      varchar(64) null,
    age       int         null,
    gender    varchar(2)  null,
    phone_num varchar(11) null
)
    charset = utf8mb4;