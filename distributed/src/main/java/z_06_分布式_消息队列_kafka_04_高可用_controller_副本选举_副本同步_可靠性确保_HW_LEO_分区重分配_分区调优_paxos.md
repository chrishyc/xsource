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

#Leader选举
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
![](.z_06_分布式_消息队列_kafka_04_高可用_controller_副本选举_副本同步_可靠性确保_HW_LEO_分区重分配_分区调优_paxos_images/8a4a84e2.png)
![](.z_06_分布式_消息队列_kafka_04_高可用_controller_副本选举_副本同步_可靠性确保_HW_LEO_分区重分配_分区调优_paxos_images/0a97e8c3.png)
![](.z_06_分布式_消息队列_kafka_04_高可用_controller_副本选举_副本同步_可靠性确保_HW_LEO_分区重分配_分区调优_paxos_images/f732b301.png)
#分区同步
##OSR失效副本
![](.z_06_分布式_消息队列_kafka_04_分区管理_controller_副本选举_副本同步_HW_LEO_分区重分配_分区调优_paxos_images/354db8fd.png)

#分区重分配
##分区不平衡率
不平衡率=非优先副本的leader个数/分区总数
手动重分配

#分区性能
![](.z_06_分布式_消息队列_kafka_04_分区管理_副本选举_分区重分配_分区调优_paxos_images/bb4df5ae.png)

#分区分配策略
##RangeAssignor
![](.z_06_分布式_消息队列_kafka_04_高可用_controller_副本选举_副本同步_可靠性确保_HW_LEO_分区重分配_分区调优_paxos_images/4e1fc155.png)
RangeAssignor策略的原理是按照消费者总数和分区总数进行整除运算来获得一个跨度，然后将分区按照跨度进 行平均分配，以保证分区尽可能均匀地分配给所有的消费者。
对于每一个Topic，RangeAssignor策略会将消费组内所 有订阅这个Topic的消费者按照名称的字典序排序，然后为每个消费者划分固定的分区范围，如果不够平均分配，那么 字典序靠前的消费者会被多分配一个分区。
#zookeeper vs Kraft
#可靠性保证
#一致性保证
#_consumer_offset
