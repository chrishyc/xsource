#binlog作用
```asp
在实际应用中， binlog 的主要使用场景有两个，分别是 主从复制 和 数据恢复 。

主从复制 ：在 Master 端开启 binlog ，然后将 binlog 发送到各个 Slave 端， Slave 端重放 binlog 从而达到主从数据一致。
数据恢复 ：通过使用 mysqlbinlog 工具来恢复数据。
```
#binlog刷盘时机
```asp
0：不去强制要求，由系统自行判断何时写入磁盘；
1：每次 commit 的时候都要将 binlog 写入磁盘；
N：每N个事务，才会将 binlog 写入磁盘。
```
#binlog日志格式
##statement
##row

#为啥有binlog还需要redo？binlog不能直接恢复吗?
```asp
为啥有了binlog 还需要redo?binlog不是用在了主从复制上了吗?不要说binlog是逻辑执行，为啥主从复制就可以接受逻辑语句，
事务恢复就不能接受了？redis的aof也是逻辑语句，咋不像mysql 的事务恢复这么多事
```
[](https://spongecaptain.cool/post/database/logicalandphicallog/#21-%E4%BA%8B%E5%8A%A1%E5%B9%B6%E5%8F%91%E6%8E%A7%E5%88%B6)
[](https://time.geekbang.org/column/article/73161)
##事务并发控制
```asp
我们需要使用事务并发控制的原因基于以下事实（以 MySQL 为语境解释）：

事务由 SQL 语句构成，每一个 SQL 语句可分解为多个不可分隔的读/写操作；
事务的执行实际上是一连串不可分割读写操作的执行；
事务调度器负责调度不可分割读写操作的执行顺序，它们可能来自于不同事务；
事务并发控制的一个目标就是实现并行化事务；
逻辑日志很难实现一致的事务并发控制。由于逻辑日志难以携带并发执行顺序的信息，当同时有多个事务产生更新操作时，数据库内部会将这些操作调度为串行化序列执行，需要机制来保障每次回放操作的执行顺序与调度产生的顺序一致。

另一方面，物理日志本身就是存储就是基于不可分隔的更新操作，因此其存储先后顺序就代表了执行器的调度顺序。而且由于很容易判断两个 page 是否是同一个 page，如果不是，完全可以安全并行地并行执行。

为了实现宕机前后事务并发控制的一致性，数据库选择使用 Physical Logging 作为其 Redo Log。
```
##幂等性
```asp
物理日志能够做的幂等性，因为其本质是对状态机某一个字段在更新前后状态的记录，无论执行多少次，最终得到的状态总是相同的。
逻辑日志并不能够提供幂等性的语义，因为某一个更新操作本身不具备幂等性

binlog是逻辑日志，可能包含多个page，假设记录了一条逻辑日志update table set a=a+1;多次重放后结果一定是有问题的。如果是redo log，
它和具体的page挂钩，“page 111， table, a, old value 1, new value 5"，像这条物理日志不论重放多少次，a的值结果不会有问题
```
##数据量大小
逻辑也不是一无是处，其在日志数据量上占优。

##日志重放效率

#relay log

#binlog vs redo 
```asp
a）层次不同。redo/undo log是innodb层维护的，而binlog是mysql server层维护的，跟采用何种引擎没有关系，记录的是所有引擎的更新操作的日志记录。

b）记录内容不同。redo/undo日志记录的是每个页的修改情况，属于物理日志+逻辑日志结合的方式（redo log物理到页，页内采用逻辑日志，undo log采用的是逻辑日志），
目的是保证数据的一致性。binlog记录的都是事务操作内容，比如一条语句DELETE FROM TABLE WHERE i > 1之类的，不管采用的是什么引擎，当然格式是二进制的，
要解析日志内容可以用这个命令mysqlbinlog -vv BINLOG。

c）记录时机不同。redo/undo日志在事务执行过程中会不断的写入;而binlog仅仅在事务提交后才写入到日志，之前描述有误，binlog是在事务最终commit前写入的，
多谢anti-semicolon 指出。当然，binlog什么时候刷新到磁盘跟参数sync_binlog相关

```
