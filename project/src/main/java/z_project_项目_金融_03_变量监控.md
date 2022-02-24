#redis 消息队列
#redis双写
#redis lua,事务
#redis mysql双写
#二级缓存
理清楚二者过期时间,二者执行顺序,guava原理,缓存一致性
从内存guava,redis,数据库中获取
[z_java_03_guava_cache.md],1d,groupid,processid,version
[redis]set,expire,1d
[mysql]get
[z_01_分布式_临界知识_多级缓存.md]
#双写缓存一致性,guava缓存失效
zookeeper+Cache.invalidate
#雪崩,击穿
[](z_java_03_guava_cache.md)
##redis分布式锁实现分布式定时任务
[z_01_分布式_临界知识_分布式锁.md]
redission, redlock
##使用Redis缓存推荐码、短链接，加载热点数据等热数据,常用redis操作
[z_04_分布式_redis_01_常见问题_常见应用场景_redis分布式锁_原子操作_公司集群_项目常用_简单限流.md]五大操作
HSET KEY field value
field = mifi id_url,
value = mifi id_code,



#延时队列
[z_04_分布式_redis_01_常见问题_常见应用场景_redis分布式锁_原子操作_公司集群_项目常用_简单限流.md]sorted_set
#id-generator冲突
雪花算法,两个服务都是一套
冲突了
timestamp,31
machineTag,不一样,我们machineTag给了5位，但是我们从100开始,部分和id冲突了
step不一样
冲突
#限流
