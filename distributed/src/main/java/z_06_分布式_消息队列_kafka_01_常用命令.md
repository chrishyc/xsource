#kafka集群搭建
[](https://zhuanlan.zhihu.com/p/278252465)
[深入理解kafka 核心设计与实践原理1.2]
[/Users/chris/workspace/kafka-cluster]
```asp
3台broker,每台更改这些配置
broker.id=0
listeners=PLAINTEXT://localhost:9092
zookeeper.connect=localhost:2181,localhost:2182,localhost2183/kafka
log.dirs=/tmp/kafka-logs/broker0
```
```asp
zkCli
查看/kafka目录
[zk: localhost:2181(CONNECTED) 0] ls /kafka
[cluster, controller_epoch, controller, brokers, admin, isr_change_notification, consumers, log_dir_event_notification, latest_producer_id_block, config]
```
```asp
创建topic
kafka-topics --zookeeper localhost:2181/kafka --create --topic topic-demo --replication-factor 3 --partitions 3
查看topic分区
kafka-topics --zookeeper localhost:2181/kafka --describe --topic topic-demo

Topic:topic-demo	PartitionCount:3	ReplicationFactor:3	Configs:
	Topic: topic-demo	Partition: 0	Leader: 1	Replicas: 1,0,2	Isr: 1,0,2
	Topic: topic-demo	Partition: 1	Leader: 2	Replicas: 2,1,0	Isr: 2,1,0
	Topic: topic-demo	Partition: 2	Leader: 0	Replicas: 0,2,1	Isr: 0,2,1
```

```asp
kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-demo --group demo
kafka-console-consumer --bootstrap-server localhost:9092 --topic topic-demo
```
![](.z_06_分布式_消息队列_kafka_01_常用命令_images/26933c5b.png)
