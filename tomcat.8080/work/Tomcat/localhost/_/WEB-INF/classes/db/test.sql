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
