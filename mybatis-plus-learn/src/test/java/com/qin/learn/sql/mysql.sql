create database mybatis_plus;

use mybatis_plus;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id varchar(32) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

alter table user add column create_time datetime;
alter table user add column update_time datetime;
