use mysql;
drop table user_test;
CREATE table user_test
(
    id       int(11),
    username varchar(50),
    password varchar(50),
    unique key (id)
) engine = innodb;
insert into user_test (id, username, password)
values (1, 'User1', '123456');
insert into user_test (id, username, password)
values (2, 'User2', '654321');

drop table order_test;
CREATE table order_test
(
    uid       int(11),
    ordertime bigint(13),
    total     int(10),
    FOREIGN KEY (uid) references user_test (id)
) engine = innodb;
insert into order_test (uid, ordertime, total)
values (1,1585473694 , 1);
