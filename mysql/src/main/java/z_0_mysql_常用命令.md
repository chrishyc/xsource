##临界知识
应用监控都可以查看线程,tcp连接,缓存
mysql官网索引预习
##性能分析
###执行延时profile
[](https://dev.mysql.com/doc/refman/8.0/en/show-profile.html)
SET profiling = 1;
SHOW PROFILES
```asp
+----------+------------+-----------------------------+
| Query_ID | Duration   | Query                       |
+----------+------------+-----------------------------+
|        1 | 0.00237600 | select * from global_grants |
+----------+------------+-----------------------------+
```
SHOW PROFILE;
```asp
mysql> SHOW PROFILE;
+--------------------------------+----------+
| Status                         | Duration |
+--------------------------------+----------+
| starting                       | 0.000406 |
| Executing hook on transaction  | 0.000050 |
| starting                       | 0.000036 |
| checking permissions           | 0.000056 |
| Opening tables                 | 0.000084 |
| init                           | 0.000295 |
| System lock                    | 0.000207 |
| optimizing                     | 0.000028 |
| statistics                     | 0.000037 |
| preparing                      | 0.000013 |
| executing                      | 0.000782 |
| end                            | 0.000010 |
| query end                      | 0.000003 |
| waiting for handler commit     | 0.000050 |
| closing tables                 | 0.000083 |
| freeing items                  | 0.000141 |
| cleaning up                    | 0.000095 |
+--------------------------------+----------+
```
查看CPU使用
```asp
mysql> SHOW PROFILE CPU FOR QUERY 1;
+--------------------------------+----------+----------+------------+
| Status                         | Duration | CPU_user | CPU_system |
+--------------------------------+----------+----------+------------+
| starting                       | 0.000406 | 0.000055 |   0.000087 |
| Executing hook on transaction  | 0.000050 | 0.000008 |   0.000016 |
| starting                       | 0.000036 | 0.000010 |   0.000008 |
| checking permissions           | 0.000056 | 0.000009 |   0.000013 |
| Opening tables                 | 0.000084 | 0.000043 |   0.000024 |
| init                           | 0.000295 | 0.000007 |   0.000061 |
| System lock                    | 0.000207 | 0.000013 |   0.000041 |
| optimizing                     | 0.000028 | 0.000011 |   0.000010 |
| statistics                     | 0.000037 | 0.000017 |   0.000011 |
| preparing                      | 0.000013 | 0.000011 |   0.000001 |
| executing                      | 0.000782 | 0.000093 |   0.000310 |
| end                            | 0.000010 | 0.000005 |   0.000004 |
| query end                      | 0.000003 | 0.000003 |   0.000001 |
| waiting for handler commit     | 0.000050 | 0.000014 |   0.000014 |
| closing tables                 | 0.000083 | 0.000022 |   0.000028 |
| freeing items                  | 0.000141 | 0.000017 |   0.000039 |
| cleaning up                    | 0.000095 | 0.000013 |   0.000006 |
+--------------------------------+----------+----------+------------+
```
```asp
type是可选的，取值范围可以如下：

ALL 显示所有性能信息
BLOCK IO 显示块IO操作的次数
CONTEXT SWITCHES 显示上下文切换次数，不管是主动还是被动
CPU 显示用户CPU时间、系统CPU时间
IPC 显示发送和接收的消息数量
MEMORY [暂未实现]
PAGE FAULTS 显示页错误数量
SOURCE 显示源码中的函数名称与位置
SWAPS 显示SWAP的次数
```
###性能分析performance
[](https://dev.mysql.com/doc/refman/8.0/en/performance-schema-quick-start.html)
SHOW VARIABLES LIKE 'performance_schema';
use performance_schema;
show tables like '%wait%';
####当前所有线程状态
select * from events_waits_current\G
###所有sql语句执行排名
###慢查询
show variables like 'slow%';
show variables like 'long%';
###预编译
##数据库连接相关
###连接超时
show variables like "%timeout%";
```asp
+-----------------------------------+----------+
| Variable_name                     | Value    |
+-----------------------------------+----------+
| connect_timeout                   | 10       |
| delayed_insert_timeout            | 300      |
| have_statement_timeout            | YES      |
| innodb_flush_log_at_timeout       | 1        |
| innodb_lock_wait_timeout          | 50       |
| innodb_rollback_on_timeout        | OFF      |
| interactive_timeout               | 28800    |
| lock_wait_timeout                 | 31536000 |
| mysqlx_connect_timeout            | 30       |
| mysqlx_idle_worker_thread_timeout | 60       |
| mysqlx_interactive_timeout        | 28800    |
| mysqlx_port_open_timeout          | 0        |
| mysqlx_read_timeout               | 30       |
| mysqlx_wait_timeout               | 28800    |
| mysqlx_write_timeout              | 60       |
| net_read_timeout                  | 30       |
| net_write_timeout                 | 60       |
| rpl_stop_slave_timeout            | 31536000 |
| slave_net_timeout                 | 60       |
| wait_timeout                      | 28800    |
+-----------------------------------+----------+
```
###连接数
```asp
mysql> show variables like '%max_connections%';
+------------------------+-------+
| Variable_name          | Value |
+------------------------+-------+
| max_connections        | 151   |
| mysqlx_max_connections | 100   |
+------------------------+-------+
```
show status like  'Threads%';
###连接详情
SHOW FULL PROCESSLIST;

