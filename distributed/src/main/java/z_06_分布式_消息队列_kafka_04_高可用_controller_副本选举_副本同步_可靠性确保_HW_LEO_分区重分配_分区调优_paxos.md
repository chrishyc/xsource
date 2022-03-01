#临界知识
controller选举leader副本
分区同步
#控制器controller
![](.z_06_分布式_消息队列_kafka_04_分区管理_controller_副本选举_分区重分配_分区调优_paxos_images/39b9989b.png)
```asp
[zk: localhost:2181(CONNECTED) 2] get /kafka/controller
{"version":1,"brokerid":0,"timestamp":"1638705506265"}
cZxid = 0x1300000016
ctime = Sun Dec 05 19:58:26 CST 2021
mZxid = 0x1300000016
mtime = Sun Dec 05 19:58:26 CST 2021
pZxid = 0x1300000016
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x2000ec33d6b0000
dataLength = 54
numChildren = 0
```
![](.z_06_分布式_消息队列_kafka_04_分区管理_controller_副本选举_分区重分配_分区调优_paxos_images/fec72baf.png)

#副本选举
[深入理解kafka4.3]
leader对外读写,follow内部消息同步
![](.z_06_分布式_消息队列_kafka_04_分区管理_副本选举_分区重分配_分区调优_paxos_images/9736093d.png)
```asp
kafka-topics --zookeeper localhost:2181/kafka --describe --topic topic-demo2
Topic:topic-demo2	PartitionCount:3	ReplicationFactor:3	Configs:
	Topic: topic-demo2	Partition: 0	Leader: 0	Replicas: 0,1,2	Isr: 0,1,2
	Topic: topic-demo2	Partition: 1	Leader: 1	Replicas: 1,2,0	Isr: 1,2,0
	Topic: topic-demo2	Partition: 2	Leader: 2	Replicas: 2,0,1	Isr: 2,0,1
```
broker2宕机
```asp
kafka-topics --zookeeper localhost:2181/kafka --describe --topic topic-demo2
Topic:topic-demo2	PartitionCount:3	ReplicationFactor:3	Configs:
	Topic: topic-demo2	Partition: 0	Leader: 0	Replicas: 0,1,2	Isr: 0,2
	Topic: topic-demo2	Partition: 1	Leader: 2	Replicas: 1,2,0	Isr: 2,0
	Topic: topic-demo2	Partition: 2	Leader: 2	Replicas: 2,0,1	Isr: 2,0
```

![](.z_06_分布式_消息队列_kafka_04_分区管理_副本选举_分区重分配_分区调优_paxos_images/f5815b99.png)
![](.z_06_分布式_消息队列_kafka_04_分区管理_controller_副本选举_分区重分配_分区调优_paxos_images/5fd3041e.png)

#分区同步
##OSR失效副本
![](.z_06_分布式_消息队列_kafka_04_分区管理_controller_副本选举_副本同步_HW_LEO_分区重分配_分区调优_paxos_images/354db8fd.png)

#分区重分配
##分区不平衡率
不平衡率=非优先副本的leader个数/分区总数
手动重分配

#分区性能
![](.z_06_分布式_消息队列_kafka_04_分区管理_副本选举_分区重分配_分区调优_paxos_images/bb4df5ae.png)
#zookeeper作用
控制器的选举
纪元信息
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/4c49398d.png)
broker,topic,partition
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/d11fd954.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/c50ed20e.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/c094f1d3.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/adbbdfbc.png)
![](.z_06_分布式_消息队列_kafka_06_zookeeper_images/8042bb00.png)
