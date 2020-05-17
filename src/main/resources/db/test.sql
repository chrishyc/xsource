use mysql;
insert into mysql.account_test
values ('6029621011001', '李大锤的马追追', 100);
insert into mysql.account_test
values ('6029621011000', '李大大的锤', 1000);

DROP TABLE IF EXISTS `tb_resume`;
CREATE TABLE `tb_resume`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `name`    varchar(255) DEFAULT NULL,
    `phone`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
DROP TABLE IF EXISTS `DISTRIBUTE_ID`;
CREATE TABLE `DISTRIBUTE_ID`
(
    `id`         bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `createtime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into DISTRIBUTE_ID(createtime)
values (NOW());
select LAST_INSERT_ID();

DROP TABLE IF EXISTS `resume`;

CREATE TABLE `resume`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `name`      varchar(255) DEFAULT NULL,
    `sex`       varchar(255) DEFAULT NULL,
    `phone`     varchar(255) DEFAULT NULL,
    `address`   varchar(255) DEFAULT NULL,
    `education` varchar(255) DEFAULT NULL,
    `state`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1001
  DEFAULT CHARSET = utf8;
SET FOREIGN_KEY_CHECKS = 1;
