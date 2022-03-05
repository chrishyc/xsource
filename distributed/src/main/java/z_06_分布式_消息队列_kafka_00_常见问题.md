#临界知识
是什么->定位问题
[](https://github.com/Snailclimb/JavaGuide/blob/main/docs/high-performance/message-queue/kafka%E7%9F%A5%E8%AF%86%E7%82%B9&%E9%9D%A2%E8%AF%95%E9%A2%98%E6%80%BB%E7%BB%93.md)
#Kafka 是什么(定位问题)?主要应用场景有哪些？
kafka是消息引擎系统，也是[分布式流处理平台](https://blog.csdn.net/weixin_48185778/article/details/111321994?ops_request_misc=&request_id=&biz_id=102&utm_term=kafka%2520stream%25E4%25B8%258Econsumer&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-2-111321994.first_rank_v2_pc_rank_v29#11_Kafka_Stream_6)
LinkedIn 最开始有强烈的数据强实时处理方面的需求，其内部的诸多子系统要执行多种类型的数据处理与分析，
主要包括业务系统和应用程序性能监控，以及用户行为数据处理等
Kafka 系统的写性能很强
```asp
提供一套 API 实现生产者和消费者；
降低网络传输和磁盘存储开销；
实现高伸缩性架构
```
#Kafka的优势在哪里？
#kafka为什么是主读主写?
[](https://time.geekbang.org/column/article/246934)
#kafka中如何实现端到端的Exactly Once
#kafka的事务
#项目中的问题
##公司kafka吞吐
单机百万消息/s
单个broker可以轻松处 理数千个分区以及每秒百万级的消息量
##公司的生产者消费者比例
##公司生产者配置
#kafka为啥快?
Kafka速度快是因为:
1. partition顺序读写，充分利用磁盘特性，这是基础;
2. Producer生产的数据持久化到broker，采用mmap文件映射，实现顺序的快速写入;
3. Customer从broker读取数据，采用sendfile，将磁盘文件读到OS内核缓冲区后，直接转到socket buffer进
行网络发送。
4. 生产者批量写,使用压缩算法gzip,消费端批量读,解压
