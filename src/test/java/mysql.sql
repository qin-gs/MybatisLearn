INSERT INTO mybatis_learn.blog (id, title, body, userid)
VALUES ('blog-1', '博客1', '博客1 内容', null);

INSERT INTO mybatis_learn.blog (id, title, body, userid)
VALUES ('blog-2', '博客2', '博客2 内容', null);

INSERT INTO mybatis_learn.blog (id, title, body, userid)
VALUES ('blog-3', '博客3', '博客3 内容', null);

INSERT INTO mybatis_learn.blog (id, title, body, userid)
VALUES ('blog-4', '博客4', '博客4 内容', null);

INSERT INTO mybatis_learn.blog (id, title, body, userid)
VALUES ('blog-5', '博客5', '博客5 内容', null);

show create table mybatis_learn.blog;


alter table mybatis_learn.blog
    convert to character set utf8mb4;
alter table mybatis_learn.comment
    convert to character set utf8mb4;
alter table mybatis_learn.user
    convert to character set utf8mb4;