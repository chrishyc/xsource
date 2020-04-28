CREATE DATABASE springbootdata;
USE springbootdata;
DROP TABLE IF EXISTS t_article;
CREATE TABLE t_article
(
    id      int(20) NOT NULL AUTO_INCREMENT COMMENT '文章id',
    title   varchar(200) DEFAULT NULL COMMENT '文章评论',
    content longtext COMMENT '文章内容',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
INSERT INTO t_article
VALUES ('1', 'Spring Boot', 'Boot');
INSERT INTO t_article
VALUES ('2', 'Spring Cloud', 'cloud');

DROP TABLE IF EXISTS t_comment;
CREATE TABLE t_comment
(
    id      int(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
    content longtext COMMENT '评论内容',
    author  varchar(200) DEFAULT NULL COMMENT '评论作者',
    a_id    int(20)      DEFAULT NULL COMMENT 'id关联文章的id',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
INSERT INTO t_comment
VALUES ('1', 'good', 'luccy', '1');
INSERT INTO t_comment
VALUES ('2', 'good', 'tom', '1');
INSERT INTO t_comment
VALUES ('3', 'good', 'eric', '1');
INSERT INTO t_comment
VALUES ('4', 'good', 'chris', '1');
INSERT INTO t_comment
VALUES ('5', 'good', 'device', '2');
