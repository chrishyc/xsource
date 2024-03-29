##临界知识
帮助文档
配置文件:[](https://raw.githubusercontent.com/redis/redis/6.0/redis.conf)
redis组合命令减少网络传输,计算向数据移动(sinter vs sinterstore)
redis慢查询问题排查

#redis help
127.0.0.1:6379> redis help
help @generic
help @string
cat /usr/local/etc/redis.conf
#当前对象类型&编码存储方式
type key
object encoding key
debug object programmings
#系统状态
info all
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/7e910cd3.png)
[redis开发与维护]
#事务
multi
incr books QUEUED
incr books QUEUED
exec
#数据类型操作
##string操作
[redis开发与运维][2.2.1]
set key value,
set(key, value, nxxx, expx, time),//nx+ex
MSET
MGET
strlen,
incr key//计数


setbit key offset 1,//offset位设置为1
bitcount key,//key中bit=1的个数
bitpos key bit start end,//start到end的字节范围中中,第一个bit的位置
bitop operation destkey key [key ...]//位运算
set key h,//批量设置位图值,h为自己计算出来对应的ascii 


bitfield w get u3 2 (integer) 5//# 从第一个位开始取 4 个位，结果是无符号数 (u)
bitfield w set u8 8 97 # 从第 8 个位开始，将接下来的 8 个位用无符号数 97 替换
##list操作
栈:lpush,lpop; rpush,rpop
队列:lpush,rpop; rpush,lpop
LRANGE key start end
LINDEX key index单播
LREM key count value,
BLPOP key [key ...] timeout，阻塞队列FIFO
BLPOP mylist 30//阻塞30s
BLPOP mylist 0//一直阻塞直到有数据
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/ab3d20cf.png)
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/2da2620e.png)
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/9acd2cb3.png)
##hash
HSET key field value [field value ...]
hget key field
hdel key field [field ...]
hexists key field
hgetall key
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/30a9204f.png) 
##set
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/5d8f7524.png)
SRANDMEMBER set 8//随机选择8个
SPOP set//随机移除一个
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/d3ef1f09.png)
sadd key element [element ...]
srem key element [element ...]
sismember key element
sinter key [key ...]
sdiff key [key ...]
sinterstore destination key [key ...] 
suionstore destination key [key ...] 
sdiffstore destination key [key ...]
##sorted_set
zadd key score member [score member ...]
zrank key member
zincrby key increment member
zrange key start end [withscores]
zrevrange key start end [withscores]
zinterstore destination numkeys key [key ...] [weights weight [weight ...]][aggregate sum|min|max]
zunionstore destination numkeys key [key ...] [weights weight [weight ...]] [aggregate sum|min|max]
zinterstore destset 2 sortedset sortedset weights 1 0.5 aggregate min
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/e0dcd3af.png)
#过期命令
```asp
EXPIRE \<key> \<ttl>：命令用于将键key的过期时间设置为ttl秒之后
PEXPIRE \<key> \<ttl>：命令用于将键key的过期时间设置为ttl毫秒之后
EXPIREAT \<key> \<timesramp>：命令用于将key的过期时间设置为timrestamp所指定的秒数时间戳
PEXPIREAT \<key> \<timesramp>：命令用于将key的过期时间设置为timrestamp所指定的毫秒数时间戳


10.38.161.13:6600> HELP PEXPIRE

  PEXPIRE key milliseconds
  summary: Set a key's time to live in milliseconds
  since: 2.6.0
  group: generic

10.38.161.13:6600> HELP EXPIRE

  EXPIRE key seconds
  summary: Set a key's time to live in seconds
  since: 1.0.0
  group: generic
```
#慢查询
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/90882bfd.png)

```asp
1)发送命令 2)命令排队 3)命令执行 4)返回结果
需要注意，慢查询只统计步骤3)的时间，所以没有慢查询并不代表客 户端没有超时问题。

```

##获取慢查询配置
```asp
127.0.0.1:6379> CONFIG get slowlog-log-slower-than
1) "slowlog-log-slower-than"
2) "10000"//10ms
```
##慢查询日志
slowlog get
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/e3601790.png)
分别是慢查询日志的标识 id、发生时间戳、命令耗时、执行命令和参数
#redis内存淘汰配置
maxmemory
#持久化(RDB/AOF)
info Persistence
/usr/local/var/db/redis,//aof,rdb路径
##rdb
[](https://github.com/sripathikrishnan/redis-rdb-tools )
bgsave,查看cat /usr/local/etc/redis.conf
显然bgsave命令是针对save阻塞问题做的优化。因此Redis内部所有的涉 及RDB的操作都采用bgsave的方式，而save命令已经废弃
```asp
pip3 install rdbtools python-lzf
rdb --command json /var/redis/6379/dump.rdb
rdb -c protocol /var/redis/6379/dump.rdb
```
##aof
```asp
rdb -c protocol appendonly.aof
bgrewriteaof
```asp

```asp
# Master-Replica replication. Use replicaof to make a Redis instance a copy of
# replicaof <masterip> <masterport>
#       At the date of writing these commands are: set setnx setex append
appendonly yes
```
```asp
# The name of the append only file (default: "appendonly.aof")
appendfilename "appendonly.aof"
# always: fsync after every write to the append only log. Slow, Safest.
# appendfsync always
appendfsync everysec
# appendfsync no
```
```asp
# the same as "appendfsync none". In practical terms, this means that it is
no-appendfsync-on-rewrite no
# Automatic rewrite of the append only file.
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb

aof-use-rdb-preamble yes

# You can pin the server/IO threads, bio threads, aof rewrite child process, and
# Set aof rewrite child process to cpu affinity 8,9,10,11:
# aof_rewrite_cpulist 8-11
```
##rdb&aof混搭
aof-use-rdb-preamble yes
#集群操作
##redis服务端
redis-server start
##redis客户端
redis-cli -h 127.0.0.1 -p 6379
##主从复制
![](.z_04_分布式_redis_01_常见命令_帮助命令_数据库操作_hash分槽_images/e6feb9f3.png)
##redis压测
redis-benchmark -q -n 100000
![](.z_04_分布式_redis_常见命令_images/d7c37f6f.png)
redis-benchmark -t set -P 2 -q,管道可以发送的命令数为2
不通过网卡请求数15w/s
通过网卡请求数6w/s
##redis切片集群
[](https://www.cnblogs.com/zackku/p/10094940.html)
[redis开发与运维10.2.3]
cluster meet 127.0.0.1 6381
cluster nodes,集群节点
cluster keyslot key,查看key对应的slot
redis-cli -h 127.0.0.1 -p 6379 cluster addslots {0..5461}
cluster replicate cfb28ef1deee4e0fa78da86abe5d24566744411e//主从设置
#db
CONFIG GET databases//数据库多少个
flushdb/flushall//清除数据库
dbsize//当前数据库
select 1 //选择数据库
