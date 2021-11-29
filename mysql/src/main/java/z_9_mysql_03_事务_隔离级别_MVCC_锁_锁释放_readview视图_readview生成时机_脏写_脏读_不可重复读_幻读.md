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
#MVCC
[](https://github.com/twitter-forks/mysql/blob/master/storage/innobase/include/read0read.h#L124)
```asp
trx_id:每次一个事务对某条聚簇索引记录进行改动时，都会把该事务的 事务id 赋值给 trx_id 隐藏列
roll_pointer:每次对某条聚簇索引记录进行改动时，都会把旧的版本写入到 undo日志 中，然后这个隐藏 列就相当于一个指针，
可以通过它来找到该记录修改前的信息

实际上insert undo只在事务回滚时起作用，当事务提交后，该类型的undo日志就没用了，它占用的Und o Log Segment也会被系统回收(也就是该undo
日志占用的Undo页面链表要么被重用，要么被释放)。 虽然真正的insert undo日志占用的存储空间被释放了，但是roll_pointer的值并不会被清除，
roll_po inter属性占用7个字节，第一个比特位就标记着它指向的undo日志的类型，如果该比特位的值为1时， 就代表着它zhi向的undo日志类型为insert undo。
所以我们之后在画图时都会把insert undo给去掉， 大家留意一下就好了

每次对记录进行改动，都会记录一条 undo日志 ，每条 undo日志 也都有一个 roll_pointer 属性( INSERT 操作 对应的 undo日志 没有该属性，
因为该记录并没有更早的版本)，可以将这些 undo日志 都连起来，串成一个链 表，

```
##版本链
![](.z_9_mysql_03_事务_隔离级别_MVCC_锁_锁释放_readview视图_脏写_脏读_不可重复读_幻读_images/0d5c9836.png)
所有的版本都会被 roll_pointer 属性连接成一个链表，我们把这个链表称之为 版本链 ，版本链的头节点就是当 前记录最新的值。
另外，每个版本中还包含生成该版本时对应的 事务id
[mysql是怎么运行的]

##read view
核心问题就是:需要判断一下版本链中的哪个版本是当前事务可见的。为 此，设计 InnoDB 的大叔提出了一个 ReadView 的概念
read view在创建后就不会改变了
```asp
m_ids :表示在生成 ReadView 时当前系统中活跃的读写事务的 事务id 列表。
min_trx_id :表示在生成 ReadView 时当前系统中活跃的读写事务中最小的 事务id ，也就是 m_ids 中的最 小值。
max_trx_id :表示生成 ReadView 时系统中应该分配给下一个事务的 id 值。
小贴士:
注意max_trx_id并不是m_ids中的最大值，事务id是递增分配的。比方说现在有id为1，2，3这三 个事务，之后id为3的事务提交了。
那么一个新的读事务在生成ReadView时，m_ids就包括1和2，mi n_trx_id的值就是1，max_trx_id的值就是4。
creator_trx_id :表示生成该 ReadView 的事务的 事务id 

我们之前说执行DELETE语句或者更新主键的UPDATE语句并不会立即把对应的记录完全从页面中删除，而 是执行一个所谓的delete mark操作，
相当于只是对记录打上了一个删除标志位，这主要就是为MVCC服 务的，大家可以对比上边举的例子自己试想一下怎么使用。 
另外，所谓的MVCC只是在我们进行普通的SEELCT查询时才生效
```
![](.z_9_mysql_03_事务_隔离级别_MVCC_锁_锁释放_readview视图_脏写_脏读_不可重复读_幻读_images/af8e4b70.png)
###READ COMMITTED生成read view时机
每次读取数据前都生成一个ReadView,使用READ COMMITTED隔离级别的事务在每次查询开始时都会生成一个独立的ReadView
例子,[mysql是怎么运行的,24.3.2]
###REPEATABLE READ生成read view时机
对于使用 REPEATABLE READ 隔离级别的事务来说，只会在第一次执行查询语句时生成一个 ReadView ，之后的查 询就不会重复生成了。
例子,[mysql是怎么运行的,24.3.2]
#事务锁
##锁释放时机
InnoDB使用锁来保证不会有脏写情况的发生，也就是在第一个事务更新了某 条记录后，就会给这条记录加锁，另一个事务再次更新时就需要等待第一个事务提交了
把锁释放之后才可以继续更新
