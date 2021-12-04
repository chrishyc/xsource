#主从复制
[深入浅出mysql 31章]
##线程
主库:binlog dump线程
从库:IO线程,sql线程
异步复制,存在延时
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_images/adc85d98.png)
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_images/bcf90fc7.png)
##主库推送
##binlog
##relaylog
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_images/bc502b2e.png)
![](.z_0_mysql_常用命令_字符集_存储引擎_连接_行格式_启动配置_bufferpool_sortbuffer_joinbuffer_Temporary_images/d702b7ca.png)

##异步复制
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_异步复制_半同步复制_images/aed8161d.png)
##半同步复制
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_异步复制_半同步复制_images/fab19984.png)
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_异步复制_半同步复制_images/d88b44ae.png)
##复制优化
[深入浅出mysql]
主库多线程写,从库单线程sql
###从库写入部分数据库
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_异步复制_半同步复制_提高复制效率_images/4027d7e8.png)
###从库sql多线程
![](.z_10_mysql_集群架构_binlog_relaylog_主从复制_异步复制_半同步复制_提高复制效率_images/159b1613.png)
