#临界知识
连接池
#连接池
连接池本身一般是线程安全的，可复用。每次使用需要从连接池获取连接，使用后归还，归还的工作由使用者负责
看jedis源码，会池化很多连接，如果连接超时，会随机选择一个节点重连
#客户端连接从节点
拒绝被写入
#redission
[](https://github.com/redisson/redisson/)
Redisson使用非阻塞的I/O和基于Netty框架的事件驱动的通信层，其方法调用是异步的。Redisson的API是线程安全的，所以可以操作单个Redisson连接来完成各种操作。


