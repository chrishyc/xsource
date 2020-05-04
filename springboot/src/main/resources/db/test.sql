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


create DATABASE blog_system;
DROP TABLE IF EXISTS t_article;
CREATE TABLE t_article
(
    id            int(11)     NOT NULL AUTO_INCREMENT,
    title         varchar(50) NOT NULL COMMENT '文章标题',
    content       longtext COMMENT '文章具体内容',
    created       date        NOT NULL COMMENT '发表时间',
    modified      date                 DEFAULT NULL COMMENT '修改时间',
    categories    varchar(200)         DEFAULT '默认分类' COMMENT '文章分类',
    tags          varchar(200)         DEFAULT NULL COMMENT '文章标签',
    allow_comment tinyint(1)  NOT NULL DEFAULT '1' COMMENT '是否允许评论',
    thumbnail     varchar(200)         DEFAULT NULL COMMENT '文章缩略图',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO t_article
VALUES ('1', '2019新版Java学习路线图', 'Java学习路线图具体内容具体内容具体内容具体内容具体内容具体内容具体内容', '2019-10-10', null, '默认分类', '‘2019,Java,学习路线图',
        '1', null);
INSERT INTO t_article
VALUES ('2', '2019新版Python学习线路图',
        '据悉，Python已经入驻小学生教材，未来不学Python不仅知识会脱节，可能与小朋友都没有了共同话题~~所以，从今天起不要再找借口，不要再说想学Python却没有资源，赶快行动起来，Python等你来探索',
        '2019-10-10', null, '默认分类', '‘2019,Java,学习路线图', '1', null);
INSERT INTO t_article
VALUES ('3', 'JDK 8——Lambda表达式介绍',
        ' Lambda表达式是JDK 8中一个重要的新特性，它使用一个清晰简洁的表达式来表达一个接口，同时Lambda表达式也简化了对集合以及数组数据的遍历、过滤和提取等操作。下面，本篇文章就对Lambda表达式进行简要介绍，并进行演示说明',
        '2019-10-10', null, '默认分类', '‘2019,Java,学习路线图', '1', null);
INSERT INTO t_article
VALUES ('4', '函数式接口',
        '虽然Lambda表达式可以实现匿名内部类的功能，但在使用时却有一个局限，即接口中有且只有一个抽象方法时才能使用Lamdba表达式代替匿名内部类。这是因为Lamdba表达式是基于函数式接口实现的，所谓函数式接口是指有且仅有一个抽象方法的接口，Lambda表达式就是Java中函数式编程的体现，只有确保接口中有且仅有一个抽象方法，Lambda表达式才能顺利地推导出所实现的这个接口中的方法',
        '2019-10-10', null, '默认分类', '‘2019,Java,学习路线图', '1', null);
INSERT INTO t_article
VALUES ('5', '虚拟化容器技术——Docker运行机制介绍',
        'Docker是一个开源的应用容器引擎，它基于go语言开发，并遵从Apache2.0开源协议。使用Docker可以让开发者封装他们的应用以及依赖包到一个可移植的容器中，然后发布到任意的Linux机器上，也可以实现虚拟化。Docker容器完全使用沙箱机制，相互之间不会有任何接口，这保证了容器之间的安全性',
        '2019-10-10', null, '默认分类', '‘2019,Java,学习路线图', '1', null);
