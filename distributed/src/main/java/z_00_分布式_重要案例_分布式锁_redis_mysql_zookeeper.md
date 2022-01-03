##redis分布式锁
[redisson](https://www.cnblogs.com/jackson0714/p/redisson.html)
[](https://github.com/redis/redis-doc/blob/master/topics/distlock.md)
[](https://time.geekbang.org/column/article/301092)
[](http://zhangtielei.com/posts/blog-redlock-reasoning.html)

setnx,del
###应用挂了
过期时间5s

###A超时误删B的锁
```asp
Redis 的分布式锁不能解决超时问题，如果在加锁和释放锁之间的逻辑执行的太长，以至 于超出了锁的超时限制，就会出现问题。
因为这时候锁过期了，第二个线程重新持有了这把锁， 但是紧接着第一个线程执行完了业务逻辑，就把锁给释放了，第三个线程就会在第二个线程逻 辑执行完之间拿到了锁

tag = random.nextint() # 随机数 if redis.set(key, tag, nx=True, ex=5)://setnx,SET if Not eXists
do_something()
redis.delifequals(key, tag) # 假象的 delifequals 指令
有一个更加安全的方案是为 set 指令的 value 参数设置为一个随机数，释放锁时先匹配 随机数是否一致，然后再删除 key。但是匹配 value 和删除 key 不是一个原子操作，
Redis 也 没有提供类似于 delifequals 这样的指令，这就需要使用 Lua 脚本来处理了，因为 Lua 脚本可 以保证连续多个指令的原子性执行。

# delifequals
if redis.call("get",KEYS[1]) == ARGV[1] then
return redis.call("del",KEYS[1])
else
return 0
end
```
###可重入
Redis 分 布式锁如果要支持可重入，需要对客户端的 set 方法进行包装，使用线程的 Threadlocal 变量 存储当前持有锁的计数。

###redis服务挂了(分布式锁redlock**)
```asp
在 Sentinel 集群中，主节点挂掉时，从节点会取而代之，客户端上却并没有明显感 知。原先第一个客户端在主节点中申请成功了一把锁，
但是这把锁还没有来得及同步到从节 点，主节点突然挂掉了。然后从节点变成了主节点，这个新的节点内部没有这个锁，所以当 另一个客户端过来请求加锁时，
立即就批准了。这样就会导致系统中同样一把锁被两个客户 端同时持有，不安全性由此产生

使用redlock
会向过半节点发送 set(key, value, nx=True, ex=xxx) 指令，只要过半节点 set 成功，那就认为加锁成功。释放锁时，需要向所有节点发送 del 指令。
不过 Redlock 算法还 需要考虑出错重试、时钟漂移等很多细节问题，同时因为 Redlock 需要向多个节点进行读 写，意味着相比单实例 Redis 性能会下降一些
```
![](.z_04_分布式_redis_01_常见问题_常见应用场景_redis分布式锁_原子操作_公司集群_项目常用_images/34e951ee.png)
