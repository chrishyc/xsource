##临界知识
缓存页增删改查
行记录变长长度记录,null记录
更小的类型,磁盘/内存/cpu缓存占用更少,cpu指令周期也更少
简单更好,整型>字符类型,整型比字符操作代价更低，因为字符集和校对规则是字符比较比整型比较更复杂
字符集类型优化
##参考
[](z_0_mysql_Mysql调优模型.xmind)
##避免使用null
[](https://segmentfault.com/a/1190000038495801)
null占记录的null字段空间
###null仍会使用索引
```asp
 (root@localhost mysql3306.sock)[sysbench]>explain select id,k from sbtest1 where k is null;
 +----+-------------+---------+------------+------+---------------+------+---------+-------+------+----------+--------------------------+
 | id | select_type | table   | partitions | type | possible_keys | key  | key_len | ref   | rows | filtered | Extra                    |
 +----+-------------+---------+------------+------+---------------+------+---------+-------+------+----------+--------------------------+
 |  1 | SIMPLE      | sbtest1 | NULL       | ref  | k_1           | k_1  | 5       | const |    1 |   100.00 | Using where; Using index |
 +----+-------------+---------+------------+------+---------------+------+---------+-------+------+----------+--------------------------+
 1 row in set, 1 warning (0.00 sec)
```
###null在==,<==>,is ,group中表现很混乱
```asp
1.select NULL=NULL的结果为false,但是在我们使用distinct,group by,order by时,NULL又被认为是相同值.

 (root@localhost mysql3306.sock)[zlm]>select * from test_null;
 +----+------+
 | id | name |
 +----+------+
 |  1 | zlm  |
 |  2 | NULL |
 +----+------+
 2 rows in set (0.00 sec)
 // -------------------------------------->这个很有代表性<----------------------
 (root@localhost mysql3306.sock)[zlm]>select * from test_null where name=null;
 Empty set (0.00 sec)

 (root@localhost mysql3306.sock)[zlm]>select * from test_null where name is null;
 +----+------+
 | id | name |
 +----+------+
 |  2 | NULL |
 +----+------+
 1 row in set (0.00 sec)
 
 null<=>null always return true,it's equal to "where 1=1".  

2.count(*) 或者 count(null column)结果不同,count(null column)<=count(*).

(root@localhost mysql3306.sock)[zlm]>select count(*),count(name) from test_null;
  +----------+-------------+
  | count(*) | count(name) |
  +----------+-------------+
  |        2 |           1 |
  +----------+-------------+
  1 row in set (0.00 sec)
  
  //count(*) returns all rows ignore the null while count(name) returns the non-null rows in column "name".
 
3.干扰排序，分组,去重结果,有时候还会严重托慢系统的性能(count计算时要考虑null会拖慢性能).(默认值为''时参与分组,排序,null也参与)

4.使用NULL值容易引发不受控制的事情发生,有时候还会严重托慢系统的性能
,我们并不推荐在列中设置NULL作为列的默认值,你可以使用NOT NULL消除默认设置,使用0或者''空字符串来代替NULL

```
##bigint vs DECIMAL vs FLOAT DOUBLE
公司货币使用bigint(20),*100,精确到分
```asp
对小数进行精确计算的时候才是用DECIMAL类型
数据量比较大的时候，可以考虑使用BIGINT类型代替DECIMAL类型，将需要存储的货币单位根据小数的位数乘以相应的倍数即可


```
##int(10) vs int(11)
只会限制终端的展示长度,存储和计算无影响
##char(10) vs varchar(10),varchar(10) vs varchar(100)
```asp
char固定长度,行记录无长度项,长度固定
varchar变长,行记录有长度项,varchar需要得到长度
因此相同的内容,char检索效率、写效率 会比varchar高
```
```asp
varchar(10)和varchar(100)磁盘内容一样,占用空间一样
但内存空间占用不同，是指定的大小.
内存缓存页用于增删改,需要考虑varchar字符长度变化

```
##时间 TIMESTAMP & DATETIME
使用mysql自建类型而不是字符串来存储日期和时间
整型比字符操作代价更低，因为字符集和校对规则是字符比较比整型比较更复杂
```asp
TIMESTAMP:4字节存储时间类型,1970-2038,(精确到秒)
DATETIME:1001->9999,使用8个字节存储时间类型(整型,精确到秒)
BITINIT:8字节,毫秒
```
##ip
使用整型存储ip
整型比字符操作代价更低，因为字符集和校对规则是字符比较比整型比较更复杂
##枚举(非重点)
修改枚举,需要修改默认表结构，这，往往是得不偿失的。一般使用tiny类型代替
##代理主键id vs自然主键
使用代理主键而不是自然主键
代理主键不与业务耦合，可以使用整型,而自然主键往往是身份证,账号id是字符串
索引效率和空间占用比较大
##字符集
```asp
MySQL的数据类型可以精确到字段，所以当我们需要大型数据库中存放多字节数据的时候，可以通过对不同表不同字段使用不同的数据类型来较大程度减小数据存储量，进而降低 IO 操作次数并提高缓存命中率
如果我们可以确定不需要存放多种语言，就没必要非得使用UTF8或者其他UNICODE字符类型，这回造成大量的存储空间浪费
```
