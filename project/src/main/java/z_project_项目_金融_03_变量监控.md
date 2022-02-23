#redis 消息队列
#redis双写
#redis lua,事务
#redis mysql双写
#二级缓存
[](https://juejin.cn/post/6844903747160637447#heading-5)
从内存guava,redis,数据库中获取
[z_java_03_guava_cache.md],2h,groupid,processid
[redis]set,expire,1d
[mysql]get
[z_01_分布式_临界知识_多级缓存.md]
#双写缓存一致性,失效
#雪崩,击穿
[](z_java_03_guava_cache.md)
##redis分布式锁实现分布式定时任务
[z_01_分布式_临界知识_分布式锁.md]
redission, redlock
##使用Redis缓存推荐码、短链接等热数据
[z_04_分布式_redis_01_常见问题_常见应用场景_redis分布式锁_原子操作_公司集群_项目常用_简单限流.md]
HSET KEY field value
field = mifi id_url,
value = mifi id_code,
#限流
