#临界知识
redis两大功能:缓存,数据库
redis模块化module组件概念
redis缓存一致性更新cache还是淘汰cache:读多还是写多
redis缓存一致性速度:redis查询更新<1ms,mysql查询更新<100ms
每台主机线程数5W,32核,128GB,1T
#redis缓存一致性(Cache Aside 模式 vs Read/Write Through 模式)
缓存旁路(cache aside):旁路缓存,更新mysql时删除redis缓存
读写穿透(read/write through):更新mysql时更新redis缓存
异步写入(Write behind):只同步更新缓存，异步更新数据库(InnoDB Buffer Pool机制)

[](https://juejin.cn/post/6964531365643550751)
[](https://time.geekbang.org/column/article/213230)
性能,缓存利用率、并发、缓存 + 数据库
![](.z_04_分布式_redis_01_缓存问题_缓存一致性_缓存穿透_缓存击穿_缓存雪崩_预热_布隆过滤器&优化_热key处理_images/67c719d7.png)
##核心问题
```asp
1、先更新缓存，再更新数据库；
2、先更新数据库，再更新缓存；
3、先淘汰缓存，再更新数据库；
4、先更新数据库，再淘汰缓存
```
##更新cache还是淘汰cache
主要取决于更新缓存的复杂度
数据库写入的结果是中间值，缓存的结果是计算结果
```asp
有如下两种不适合场景 更新cache
如果你是一个写数据库场景比较多，而读数据场景比较少的业务需求，采用这种方案就会导致，数据压根还没读到，缓存就被频繁的更新，浪费性能
如果你写入数据库的值，并不是直接写入缓存的，而是要经过一系列复杂的计算再写入缓存。那么，每次写入数据库后，都再次计算写入缓存的值，无疑是也浪费性能的
```
```asp
淘汰cache
不过大部分场景下删除缓延迟删除存操作简单，并且带来的副作用只是增加了一次Cache Miss，建议作为通用的处理方式
```
```asp
线程 A 更新数据库（X = 1）
线程 B 更新数据库（X = 2）
线程 B 更新缓存（X = 2）
线程 A 更新缓存（X = 1）
最终 X 的值在缓存中是 1，在数据库中是 2，发生不一致,更新数据库删除redis不会发生此情况
```

##更新数据库再删除缓存vs删除缓存后更新数据库(缓存回读)
都可能造成数据不一致,看谁的影响最小

更新数据库再删除缓存出现脏数据概率是更小点
数据库查询更新耗时<100ms
缓存查询更新耗时<1ms

缓存和数据库不一致,一般是查旧数据库,更新回旧缓存导致,删除缓存<查库<更新数据库

```asp
请求缓存刚好失效
请求A查询数据库，得一个旧值
请求B将新值写入数据库
请求B删除缓存
请求A将查到的旧值写入缓存
```
[](https://note.dolyw.com/cache/00-DataBaseConsistency.html#%E5%85%88%E6%9B%B4%E6%96%B0%E6%95%B0%E6%8D%AE%E5%BA%93-%E5%86%8D%E5%88%A0%E9%99%A4%E7%BC%93%E5%AD%98)
##写数据库成功，第二步删除缓存失败
异步消息队列重试 延时策略 双删策略,mysql中间件canal
[](https://developer.aliyun.com/article/712285)
[](https://mp.weixin.qq.com/s/4W7vmICGx6a_WX701zxgPQ)
##可以做到强一致吗？
所以如果非要追求强一致，那必须要求所有更新操作完成之前期间，不能有「任何请求」进来。
虽然我们可以通过加「分布锁」的方式来实现，但我们要付出的代价，很可能会超过引入缓存带来的性能提升。
##消息队列 vs binlog canal(ke,nel)
![](.z_04_分布式_redis_01_缓存问题_缓存一致性_缓存穿透_缓存击穿_缓存雪崩_预热_布隆过滤器&优化_热key处理_images/0b4a571e.png)
![](.z_04_分布式_redis_01_缓存问题_缓存一致性_缓存穿透_缓存击穿_缓存雪崩_预热_布隆过滤器&优化_热key处理_images/c571c692.png)
[](https://time.geekbang.org/column/article/217593)
#缓存击穿(高并发,redis无缓存)
[](https://segmentfault.com/a/1190000039300423)
[](https://juejin.cn/post/6844903986475057165)
1.不设置过期
2.互斥锁
#缓存雪崩(整体大量过期)
[](https://minichou.github.io/2016/04/20/%E7%BC%93%E5%AD%98%E7%A9%BF%E9%80%8F%E5%8F%8A%E9%9B%AA%E5%B4%A9%E4%B9%8B%E5%B8%B8%E8%A7%81%E8%A7%A3%E5%86%B3%E6%96%B9%E6%A1%88/#%E5%A6%82%E4%BD%95%E9%98%B2%E6%AD%A2%E9%9B%AA%E5%B4%A9%E5%8F%91%E7%94%9F)
[](https://mp.weixin.qq.com/s/TBCEwLVAXdsTszRVpXhVug?)
1.数据:1.随机时间,2.热点数据不失效
2.redis:1.高可用集群模式,2.rdb恢复
3.mysql:1.线程池隔离,2.本地缓存,hystrix熔断降级
#缓存穿透(数据库无数据)
[](https://github.com/RedisBloom/RedisBloom#use-redisbloom-with-redis-cli)
1.nginx ip限流
2.服务入参合法校验
3.null缓存
4.bloom过滤器
