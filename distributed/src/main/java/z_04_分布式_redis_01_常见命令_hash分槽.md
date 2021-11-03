##redis服务端
redis-server start
##redis客户端
redis-cli -h 127.0.0.1 -p 6379
##redis压测
redis-benchmark -q -n 100000
![](.z_04_分布式_redis_常见命令_images/d7c37f6f.png)
redis-benchmark -t set -P 2 -q,管道可以发送的命令数为2
##redis切片集群
[](https://www.cnblogs.com/zackku/p/10094940.html)
[redis开发与运维10.2.3]
cluster meet 127.0.0.1 6381
cluster nodes,集群节点
cluster keyslot key,查看key对应的slot
redis-cli -h 127.0.0.1 -p 6379 cluster addslots {0...5461}
cluster replicate cfb28ef1deee4e0fa78da86abe5d24566744411e
