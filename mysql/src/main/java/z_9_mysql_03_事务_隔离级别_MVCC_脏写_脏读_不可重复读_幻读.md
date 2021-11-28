#事务属性ACID
对象:一个系统,系统涉及好几个成员
过程:系统可以同时并行执行
a原子性:一个系统的多个成员的状态(a1,a2,a3)必须一起转移到(b1,b2,b3)状态,不能只有部分转移,部分转移则回退到原始状态
c一致性:一个系统的多个成员的状态(a1,a2,a3)必须一起转移到(c1,c2,c3)的有效状态
i隔离性:多个系统并发执行时,互相不影响
d持久性:执行结果持久化
#事务&内存&磁盘
##事务刷盘时机
##commit
##rollback
#事务并发问题
##脏写
一个事务修改了另一个未提交事务修改过的数据
这是因为脏写这个问题太严重了，不论是哪种隔离级别，都不允许脏 写的情况发生
##脏读
一个事务读到了另一个未提交事务修改过的数据
##不可重复读
两次读的不一样
##幻读(Phantom)
读到了不应该读到的列
#事务隔离级别
![](.z_9_mysql_03_事务_隔离级别_MVCC_脏写_脏读_不可重复读_幻读_images/a4583e4b.png)
##READ UNCOMMITTED 
读到未提交的
##READ COMMITTED
读到已提交的
##REPEATABLE READ
可重复性去读，MySQL在REPEATABLE READ隔离级别下，是可以禁止幻读问题的发生的
##SERIALIZABLE
序列化

#事务状态
![](.z_10_mysql_事务_隔离级别_images/bd0b7817.png)
#read view
[](https://github.com/twitter-forks/mysql/blob/master/storage/innobase/include/read0read.h#L124)
