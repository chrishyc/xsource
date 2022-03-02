[rocketmq面试问题](https://github.com/Snailclimb/JavaGuide/blob/main/docs/high-performance/message-queue/rocketmq-questions.md)
#项目
##warden
#kafka vs rocketmq
#项目
##公司吞吐
写入性能达到90万+的TPS
目前的高性能磁盘，顺序写速度可以达到600MB/s， 超过了一般网卡的传输速度
但是磁盘随机写的速度只有大概100KB/s，和顺序写的性能相差6000倍!
##消息积压怎么办?
![](.z_06_分布式_消息队列_rocketmq_00_重点问题_消息丢失_消息堆积_消息重复_顺序消息_images/3aa82bc7.png)
#什么是流量控制
#如何提高写入性能?

#rebalance怎么办?
#rocketmq高性能体现在哪里?
##顺序写
1) 消息存储
目前的高性能磁盘，顺序写速度可以达到600MB/s， 超过了一般网卡的传输速度。 
但是磁盘随机写的速度只有大概100KB/s，和顺序写的性能相差6000倍! 
因为有如此巨大的速度差别，好的消息队列系统会比普通的消息队列系统速度快多个数量级。 RocketMQ的消息用顺序写,保证了消息存储的速度。
##存储结构(ConsumeQueue,CommitLog)
RocketMQ消息的存储是由ConsumeQueue和CommitLog配合完成 的，消息真正的物理存储文件 是CommitLog，ConsumeQueue是消息的逻辑队列，
类似数据库的索引文件，存储的是指向物理存储 的地址。每 个Topic下的每个Message Queue都有一个对应的ConsumeQueue文件。
![](.z_06_分布式_消息队列_rocketmq_02_集群模型_集群拓扑_nameserver_broker_topic_QUEUE_producer_consumer_images/411ec14a.png)

