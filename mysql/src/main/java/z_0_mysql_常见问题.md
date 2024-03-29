##临界知识
mysql连接session状态需要消耗内存/cpu/io,因此mysql连接受限于计算机硬件资源
mysql 常用于OLTP（on-line transaction processing),需要BIO访问
mysql bio成本与性能的权衡
mysql行存储原因:方便随机对数据增删改查
回答问题结合操作系统机制io,page cache,生产环境使用,运维经验
说说mysql的索引:需求,选型对比,结论;数据结构依托于操作系统页缓存,磁盘读写特点
##sql语句练习
##描述一下数据库事务隔离级别？
画图,原理
[z_9_mysql_02_undo_事务id_事务原子性_回滚段.md]
[z_9_mysql_03_事务_隔离级别_MVCC_锁_锁释放_readview视图_readview生成时机_脏写_脏读_不可重复读_幻读.md]
```asp
ACID:
A:原子性,定义+原理(undo log)
C:
I:隔离性,四个隔离级别,(mvcc版本并发控制,undo log,readview,三个隐藏字段
D:redo log,二阶段提交(binlog + redo log 
```
##mysql幻读怎么解决？
涉及当前读时RR级别产生幻读,使用gap 锁
[z_9_mysql_03_事务_隔离级别_MVCC_锁_锁释放_readview视图_readview生成时机_脏写_脏读_不可重复读_幻读.md]
##说说mysql的join原理?
[z_3_mysql_查询优化_03_join优化_连接优化_joinbuffer_Index-Nested_block-Nested_semi-join.md]
##索引的数据结构/原理?
画图+体系
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/0ee78bc6.png)
io(磁盘读取次数少)+数据结构(io读取量少)
1.io,程序满足局部性原理,磁盘预读,操作系统以4k为单位读到page cache缓存,mysql读取16k
2.二叉树->平衡二叉树->b树->b+树,避免深度过深,减少io读取次数,有序读取,为了加快索引,16k页尽可能多读索引,所以使用b+

```asp
b树 vs b+
b+叶子节点包含所有数据,叶子节点是双向链表
b树,叶子节点不是链表,不包含所有数据
```
[z_8_mysql_数据结构_B+树.md]
##谈谈mysql索引?
mysql使用b+树,加快数据访问,提高查询效率
mysql使用行存解决OLTP问题,需要解决key-value形式的查询,范围查询,排序
我们的查询数据结构有hashmap,二叉树,B树,B+树
选型对比
B树和B+树的对比(局部性原型,空间局部性,时间局部性->磁盘预读,page cache 4K, mysql每次读取16k,每次预读16k,尽可能少产生io)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/0542e2cb.png)
hash
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/be4a54b5.png)
二叉树,BST,AVL,红黑树
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/7d17bbad.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/409061d2.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/23289d95.png)
B树
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/94d0a232.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/54ef590b.png)
B+树
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/980dc4ca.png)
一般情况b+树多少层:3层,4层可以支持千万级别
索引选择使用int/long(21亿),insert buffer,2、3层索引尽可能少占用空间支持数据量大
索引的执行计划,优化
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/db2ce8b0.png)

##什么时候索引会失效?如何避免?
[z_3_mysql_查询优化_00_聚集索引_二级索引_覆盖索引_全表扫描_回表_范围区间_多个单索引_索引合并.md]
##mysql如何做分库分表的？集群方案，如何做数据迁移?
[z_10_mysql_集群架构_架构类型_高可用方案_双主被动_keepalive虚拟ip_热备.md]
0.主从复制,读写分离
1.集群架构:MMM,MHA,MGR
2.中间件:shardingsphere(催收账务表)
3.水平分库，水平分表，垂直分库，垂直分表

1.分片算法
2.一致性算法减少数据迁移
3.HAProxy
##哪些存储引擎？myisam vs innodb
[](https://segmentfault.com/a/1190000022206424)
show engines;
MEMORY:临时表
PERFORMANCE_SCHEMA:监控性能
InnoDB:事务,
```asp
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| Engine             | Support | Comment                                                        | Transactions | XA   | Savepoints |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| InnoDB             | DEFAULT | Supports transactions, row-level locking, and foreign keys     | YES          | YES  | YES        |
| MRG_MYISAM         | YES     | Collection of identical MyISAM tables                          | NO           | NO   | NO         |
| MEMORY             | YES     | Hash based, stored in memory, useful for temporary tables,临时表      | NO           | NO   | NO         |
| BLACKHOLE          | YES     | /dev/null storage engine (anything you write to it disappears) | NO           | NO   | NO         |
| MyISAM             | YES     | MyISAM storage engine                                          | NO           | NO   | NO         |
| CSV                | YES     | CSV storage engine                                             | NO           | NO   | NO         |
| ARCHIVE            | YES     | Archive storage engine                                         | NO           | NO   | NO         |
| PERFORMANCE_SCHEMA | YES     | Performance Schema   (监控)                                          | NO           | NO   | NO         |
| FEDERATED          | NO      | Federated MySQL storage engine                                 | NULL         | NULL | NULL       |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
```
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/4941b502.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/cbe8ffdb.png)
##聚簇索引和非聚簇索引的区别?
跟数据绑定在一起的索引我们称之为聚簇索引，没有跟数据绑定在一起的索引我们称之为非聚簇索引
myisam都是非聚簇索引
innodb,一个聚簇索引，多个非聚簇索引

##描述一下mysql主从复制的机制的原理？mysql主从复制主要有几种模式？
[z_10_mysql_集群架构_架构类型_高可用方案_双主被动_keepalive虚拟ip_热备.md]
[z_10_mysql_集群架构_binlog_relaylog_主从复制_异步复制_半同步复制_复制优化.md]
##如何解决主从复制延时问题?
从库sql多线程,组提交,GTID
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/f4d537c2.png)

##如何优化sql，查询计划的结果中看哪些些关键数据？
[z_3_mysql_查询优化_00_explain_优化过程查看_profile_查询优化_查询成本.md]
[z_3_mysql_查询优化_00_成本估算.md]
保证type在range以上
##mysql一条数据的select读流程
[z_1_mysql_sql读写执行过程.md]
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/994d5240.png)
[z_3_mysql_查询优化_00_成本估算.md]
[](http://mysql.taobao.org/monthly/2017/01/10/)
[](https://time.geekbang.org/column/article/68319)
```asp
1.连接器,mysql客户端和服务器端进行TCP连接,客户端如果太长时间没动静，连接器就会自动将它断开。这个时间是由参数 wait_timeout 控制的，默认值是 8 小时
2.分析器,词法分析语法分析,使用AST抽象语法树分解出token
3.优化器,执行计划生成,成本估计,计算cpu和io成本,选定索引,优化器是在表里面有多个索引的时候，决定使用哪个索引；或者在一个语句有多表关联（join）的时候，决定各个表的连接顺序
4.执行器,server层会把索引信息传给引擎，在引擎层遍历索引去读取数据
5.读取的数据页缓存在Buffer Pool中,提供以后读取,缓存命中率,如果是
```
##mysql一条数据的update写流程
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/9b378d2a.png)
##mysql一条数据的insert写流程
[z_0_mysql_innodb三大特性.md]
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/9b378d2a.png)
[](http://mysql.taobao.org/monthly/2017/09/10/)
```asp
1.连接器,mysql客户端和服务器端进行TCP连接,客户端如果太长时间没动静，连接器就会自动将它断开。这个时间是由参数 wait_timeout 控制的，默认值是 8 小时
2.分析器,词法分析语法分析,使用AST抽象语法树分解出token
3.优化器,执行计划生成,成本估计,计算cpu和io成本,选定索引,优化器是在表里面有多个索引的时候，决定使用哪个索引；或者在一个语句有多表关联（join）的时候，决定各个表的连接顺序
4.执行器,操作引擎,返回结果
5.引擎,插入记录时,innodb默认使用RR隔离级别,形成事务视图read view,每条记录写入undo log,也就是MVCC,事务提交时依次写入binlog redolog,两阶段提交
6.如果写入失败,undolog 回滚,如果断电重启时根据binlog redolog的事务日志来决定提交或者回滚
7.如果表中有非聚簇索引,会写入insert buffer
8.这里持久化刷盘决策,fsync,如果是1,刷盘成功后返回结果
9.如果是主从异步,binglog dump线程定期复制到从库,半同步,在返回结果前将结果复制到从库,然后返回结果
```
##mysql一条数据的delete写流程
[](https://time.geekbang.org/column/article/68633)
##两阶段提交redolog + binlog
##项目中的问题
###公司mysql架构&吞吐指标
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/756e03b3.png)
MHA两主互备,多从库，主库信息
qps 20000+,峰值:26000+, IO_read:15M/S
tps  300+,峰值:1000+,IO_write:10M/s
current_connected 1387
流量:10M,流量峰值:48.54 MB/s
双机房,mysql,innodb缓存池72G
swap禁用
数据库磁盘使用量750GB
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/05ee4c11.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/94982c08.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/a2b6ee6d.png)

![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/e54c4424.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/fff86bbc.png)
###慢查询
redis 100纳秒,mysql 100ms,
1.group by 分案类型,日志,300多万行的数据,extra using temporary,using filesort,耗时53秒,使用组合索引,对日期和类型建立组合索引,最终使用using,3秒
2.普通索引走全表扫描;1百多万行,普通索引=,耗时32秒,执行计划发现TYPE=ALL走了全表扫描,最后分析代码,使用同等逻辑的唯一索引进行查询,优化到1s
3.explain select * from instruction where create_time>current-600s and scene=1 and pkg_id=1;
9千万建索引3小时以上,600s大概1000条记录数据量,每个回表查耗时100ms*1000=100s,建立联合索引,pkgid+scene+时间

##聊聊WAL
1.write ahead log,主要是为了加快持久化进度,落盘是随机写需要寻址,为了加快持久化先顺序写日志,不需要寻址
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/48de4302.png)
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/33a03407.png)
##为啥mysql没有使用nio,而是bio
[](https://www.zhihu.com/question/23084473)
```asp
对DB来说，关键是要限制连接的数目。这个要求无论是DB连接池还是NIO的连接管理都能做到
在一个连接中，SQL语句的执行必须是串行、同步的。这是由于对于每一个Session，DB都要维护一组状态来支持查询，比如事务隔离级别，当前Session的变量
维护这些状态需要耗费内存，同时也会消耗CPU和磁盘IO。这样，限制对DB的连接数，就是在限制对DB资源的消耗

可以实现用IO多路复用来访问DB。实际上很多其他语言/框架里都是这么干的。比如Nodejs
有大量场景是需要BIO的DB查询支持的。批处理数据分析代码都是这样的场景。这样的程序写成NIO就会得不偿失
```
##为啥mysql设计成行存储
[](https://www.zhihu.com/question/24110442/answer/851671343)
行存储,列数固定,每行即使没有数据也会占用磁盘空间,方便对该列数据的增删改查
无法从根本上解决查询性能和维护成本等问题
```asp
1、适合随机的增删改查操作;

2、需要在行中选取所有属性的查询操作;

3、需要频繁插入或更新的操作，其操作与索引和行的大小更为相关。
```
##表很大,数据很多时为啥性能会下降?
表大,数据量很大,用户数增加,TPS增大,
同时访问mysql的连接增多,mysql使用bio,内存缓存页替换频繁fan,
磁盘寻址变多,磁盘吞吐量有上限1G/s

##索引下推
##索引合并
##索引覆盖
[](z_3_mysql_查询优化_00_索引_索引失效.md)
##分表分库
shardingsphere
1.热点查询语句,跨表查询,常用查询
2.数据特点,是否有冷热数据
![](.z_0_mysql_常见问题_mysqlNIO_行存储_列存储_读写性能上限分析_images/06544305.png)
###为什么要分表？
查询性能:就以我的经验来看，单表到几百万的时候，性能就会相对差一些了，你就得分表了。
###为什么分库?
并发:而且一个健康的单库并发值你最好保持在每秒 1000 左右，不要太大。那么你可以将一个库的数据拆分到多个库中，访问的时候就访问一个库好了
###用过哪些分库分表中间件？不同的分库分表中间件都有什么优点和缺点？
mycat:需要部署，自己运维一套中间件，运维成本高，但是好处在于对于各个项目是透明的，如果遇到升级之类的都是自己中间件那里搞就行了
sharding-jdbc:不用部署，运维成本低，不需要代理层的二次转发请求，性能很高
###分表分库问题
主键冲突问题:sharding-jdbc雪花算法
事务问题：TCC(Seata)
跨库跨表的join问题:冗余字段 广播表 NOSQL汇总
分库分表后二次扩容问题:一致性Hash思想，增加虚拟节点，减少迁移数据量
数据迁移问题:[](https://wylong.top/%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8/08-%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E5%90%8E%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98%E5%92%8C%E8%A7%A3%E5%86%B3%E6%96%B9%E6%A1%88.html)
[](https://cloud.tencent.com/developer/article/1441250)
[hash+range解决迁移问题](https://www.cnblogs.com/javafirst0/p/11388768.html)
###你们具体是如何对数据库如何进行垂直拆分或水平拆分的？
```asp
一种是按照 range 来分，就是每个库一段连续的数据，这个一般是按比如时间范围来的，但是这种一般较少用，因为很容易产生热点问题，大量的流量都打在最新的数据上了。

或者是按照某个字段 hash 一下均匀分散，这个较为常用。

range 来分，好处在于说，扩容的时候很简单，因为你只要预备好，给每个月都准备一个库就可以了，到了一个新的月份的时候，自然而然，就会写新的库了；缺点，但是大部分的请求，都是访问最新的数据。实际生产用 range，要看场景。

hash 分发，好处在于说，可以平均分配每个库的数据量和请求压力；坏处在于说扩容起来比较麻烦，会有一个数据迁移的过程，之前的数据需要重新计算 hash 值重新分配到不同的库或表
```
###分库分表之后，id 主键如何处理？
[雪花算法](https://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247486846&idx=1&sn=a2de9e617899df46e9e5352b1c2d7af9&chksm=fe795ca6c90ed5b0991a770fa1d43f504140821503cccbfa720b1791a27f40a16704ca43cd4e&scene=21#wechat_redirect)
###shardingsphere
[](https://segmentfault.com/a/1190000038241298)
##慢事务
##explain重要字段
##数据存储引擎有哪些？
##行锁、表锁、意向锁、表锁
间隙锁也只有在RR可重复读隔离级别下才会存在，如果是在RC读已提交隔离级别下，是没有间隙锁的存在的

InnoDB行锁是通过给索引上的索引项加锁来实现的，这一点MySQL与Oracle不同，后者是通过在数据块中对相应数据行加锁来实现的。InnoDB这种行锁实现特点意味着：只有通过索引条件检索数据，InnoDB才使用行级锁，否则，InnoDB将使用表锁！
[](https://www.modb.pro/db/74024)
[msb 面试突击0322 mysql的加锁情况.md]
[](z_9_mysql_03_锁机制.md)
![](.z_0_mysql_常见问题_images/955b8105.png)
0、where没有索引会使用表锁
1、二级索引加锁,也会在聚集索引上相应位置加锁
2、第二种情况，当我们查询的记录不存在，没有命中任何一个 record，无论是用等值查询还是范围查询的时候，它使用的都是间隙锁
3、只有访问到的对象才会加锁，这个查询使用覆盖索引，并不需要访问主键索引，所以主键索引上没有加任何锁
4、间隙锁包含(二级索引,主键索引)
![](.z_0_mysql_常见问题_images/e567800d.png)
![](.z_0_mysql_常见问题_images/e64fc598.png)
![](.z_0_mysql_常见问题_images/7ea2826c.png)
[](https://www.modb.pro/db/72155)
[](https://time.geekbang.org/column/article/75659)
##死锁排查
##mysql原子性和持久性是怎么保证的？
##mysql性能优化
1.数据类型,int,null,varchar,char
2.合理使用范式和反范式,冗余字段避免连接
3.索引优化,覆盖索引,最左匹配,索引下推,索引排序,尽量使用主键查询,使用in半连接优化
4.执行计划分析慢查询,避免索引失效,避免函数,字符串转换,避免不满足最左,避免范围右边失效
5.performance监控
6.分表分库,读写分离
##mysql主从同步延时分析
[msb mysql主从复制原理.md]
```asp
由于binlog是顺序写，所以效率很高，slave的sql thread线程将主库的DDL和DML操作事件在slave中重放。DML和DDL的IO操作是随机的，不是顺序，
所以成本要高很多，另一方面，由于sql thread也是单线程的，当主库的并发较高时，产生的DML数量超过slave的SQL thread所能处理的速度，或者当slave中有大型query语句产生了锁等待，那么延时就产生
```
##索引优化分析案例
##范围分区
##Mysql机器线上cpu很高，如何定位、解决
##现在有个比较复杂的sql查询，比较吃性能，但又非常重要，有什么好的方案（假设sql已经优化过了）
##有张表，数据量比较大，此时要加索引，由于会锁表，导致其他sql阻塞着，有什么方案吗？
