#拓扑
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/8a77e2fe.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/fc7b7740.png)
#领域术语
##AR=ISR+OSR
分区中的所有副本统称为AR(Assigned Repllicas)。
###ISR

所有与leader副本保持一定程度同步的副本(包括Leader)组成ISR(In-Sync Replicas)，ISR集合是AR集合中 的一个子集。消息会先发送到leader副本，
然后follower副本才能从leader副本中拉取消息进行同步，同步期间内 follower副本相对于leader副本而言会有一定程度的滞后。前面所说的“一定程度”是指可以忍受的滞后范围，
这个范围 可以通过参数进行配置。

###OSR
与leader副本同步滞后过多的副本(不包括leader)副本，组成OSR(Out-Sync Relipcas)。在正常情况下，所有 的follower副本都应该与leader副本保持一定程度的同步，
即AR=ISR,OSR集合为空。
##HW(一致性,lead 副本同步策略决定)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/1e1e9835.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/037cbf0c.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/5f2b1eb0.png)
HW是High Watermak的缩写， 俗称高水位，它表示了一个特定消息的偏移量(offset)，消费之只能拉取到这 个offset之前的消息。
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/21ed9386.png)
Leader如何更新自己的HW值?Leader broker上保存了一套Follower副本的LEO以及自己的LEO。当尝试确定分
区HW时，它会选出所有满足条件的副本，比较它们的LEO(包括Leader的LEO)，并选择最小的LEO值作为HW值。
[z_06_分布式_消息队列_kafka_04_高可用_controller_副本选举_副本同步_可靠性确保_HW_LEO_分区重分配_分区调优_paxos.md]
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/70d92938.png)
##LEO(写入日志就更新)
LEO是Log End Offset的缩写，它表示了当前日志文件中下一条待写入消息的offset。
##LSO：Log Stable Offset
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/98be5105.png)
Log Stable Offset。这是 Kafka 事务的概念。如果你没有使用到事务，那么这个值不存在（其实也不是不存在，只是设置成一个无意义的值）。该值控制了事务型消费者
能够看到的消息范围。它经常与 Log Start Offset，即日志起始位移值相混淆，因为有些人将后者缩写成 LSO，这是不对的。在 Kafka 中，LSO 就是指代 Log Stable Offset。
#Zookeeper VS KRaft
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/c2d343ba.png)
[](https://blog.51cto.com/u_15352876/3797318)
[](https://blog.csdn.net/HuoqilinHeiqiji/article/details/103072042)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/d634d035.png)
选举controller
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/9039e649.png)
[](https://cloud.tencent.com/developer/article/1840424)
[](https://cloud.tencent.com/developer/article/1840424)
[](https://blog.csdn.net/nazeniwaresakini/article/details/116085573)
控制器的选举
纪元信息
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/4c49398d.png)
broker,topic,partition
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/d11fd954.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/c50ed20e.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/c094f1d3.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/adbbdfbc.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/8042bb00.png)
#broker(controller)
```asp
broker 为消费者提供服务，对读取分区的请求作出响应，返回已经提交到磁盘上的消息。
1. 如果某topic有N个partition，集群有N个broker，那么每个broker存储该topic的一个partition。
2. 如果某topic有N个partition，集群有(N+M)个broker，那么其中有N个broker存储该topic的一个partition，
剩下的M个broker不存储该topic的partition数据。
3. 如果某topic有N个partition，集群中broker数目少于N个，那么一个broker存储该topic的一个或多个
partition。在实际生产环境中，尽量避免这种情况的发生，这种情况容易导致Kafka集群数据不均衡。
```
##controller
每个集群都有一个broker是集群控制器(自动从集群的活跃成员中选举出来,zookeeper选举)
控制器负责管理工作，包括将分区分配给broker 和监控broker。负责分区的选举
控制器除了一般broker的功能，还负责Leader分区的选举。zookeeper压力太大,分出大部分工作给controller,分区选举
保留少部分topic-分区路由元信息
##controller vs zookeeper
[](https://cloud.tencent.com/developer/article/1688442)
#topic 、partition(分区)、replication(副本因子)
[深入理解kafka]
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/4d108b7c.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_Isr_group_producer_consumer_images/f1ea833d.png)
可伸缩性,水平扩展,多副本冗余扩展
分区多个副本
Kafka集群中按照主题分类管理，一个主题可以有多个分区，一个分区可以有多个副本分区
##partition(分区)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/5fa079e0.png)
每个集群都有一个broker是集群控制器(自动从集群的活跃成员中选举出来)。 控制器负责管理工作:
将分区分配给broker 监控broker
集群中一个分区属于一个broker，该broker称为分区首领。 一个分区可以分配给多个broker，此时会发生分区复制。 
分区的复制提供了消息冗余，高可用。副本分区不负责处理消息的读写
```asp
1. 主题可以被分为若干个分区，一个分区就是一个提交日志。
2. 消息以追加的方式写入分区，然后以先入先出的顺序读取。
3. 无法在整个主题范围内保证消息的顺序，但可以保证消息在单个分区内的顺序。 4. Kafka 通过分区来实现数据冗余和伸缩性。
5. 在需要严格保证消息的消费顺序的场景下，需要将partition数目设为1。
```
###leader 分区
leader 处理 partition 的所有读写请求
###follower
定期地去复制leader上的数据
###offset
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/c5bf6830.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/c3853f42.png)
1.消息写入的时候，每一个分区都有一个offset，这个offset就是生产者的offset，同时也是这个分区的最新最大的 offset。
2.这是某一个分区的offset情况，生产者写入的offset是最新最大的值是12，而当Consumer A进行消费时，从0开 始消费，一直消费到了9，
消费者的offset就记录在9，Consumer B就纪录在了11。等下一次他们再来消费时，他们可 以选择接着上一次的位置消费，当然也可以选择从头消费，
或者跳到最近的记录并从“现在”开始消费
###segment

#生产者
生产者在默认情况下把消息均衡地分布到主题的所有分区上:
1. 直接指定消息的分区
2. 根据消息的key散列取模得出分区 
3. 轮询指定分区
#消费者(偏移量,rebalance)
消费者通过偏移量来区分已经读过的消息，从而消费消息
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/3cf96467.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/2e91b2b1.png)
![](.z_06_分布式_消息队列_kafka_01_关系拓扑_broker_controller_topic_partition_zookeeper_Isr_group_producer_consumer_images/392b522a.png)
##consumer
##consumer group
#消息&批次(压缩)
每个记录由一个键，一个值和一个时间戳组成
为了提高效率，消息被分批写入Kafka。批次就是一组消息，这些消息属于同一个主题和分区。
  把消息分成批次可以减少网络开销。批次越大，单位时间内处理的消息就越多，单个消息的传输时间就越⻓。批
次数据会被压缩，这样可以提升数据的传输和存储能力，但是需要更多的计算处理。
#序列号模式
