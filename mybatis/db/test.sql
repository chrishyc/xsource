use mysql;
CREATE table user_test
(
    id       int(11) ,
    username varchar(50),
    password varchar(50)
) engine = innodb;
insert into user_test (id, username,password) values(1, 'User1','123456');
