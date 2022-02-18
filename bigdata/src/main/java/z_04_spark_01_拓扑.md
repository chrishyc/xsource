#拓扑
[大数据处理架构Apache Spark设计]
##master(resouceManager)
##work(nodeManager)
##Deriver(applicationMater)
##Executor(container)
##RDD(弹性分布式数据集)
##task(线程)
##算子(map,reduce)
##逻辑执行计划
DAG逻辑处理流程
##物理执行计划
计算任务
##中间缓存&中间磁盘文件&中间计算结果cache

![](.z_04_spark_拓扑_images/c91f2f5d.png)
![](.z_04_spark_拓扑_images/73af3ac5.png)
![](.z_04_spark_拓扑_images/9a9d6399.png)
#逻辑处理流程
![](.z_04_spark_拓扑_images/e2f14ac2.png)
##RDD(弹性分布式数据集)
###宽依赖(ShuffleDependency)
parent RDD分区 部分数据流入child RDD的某一个分区
![](.z_04_spark_01_拓扑_images/424aae9f.png)
![](.z_04_spark_01_拓扑_images/91ff49ff.png)
###窄依赖(NarrowDependency)
![](.z_04_spark_拓扑_images/dbbf682b.png)
![](.z_04_spark_01_拓扑_images/566b8cd0.png)
parent RDD分区 一个/多个分区的数据流入child RDD的一个/多个分区
![](.z_04_spark_01_拓扑_images/e8e2d500.png)
![](.z_04_spark_01_拓扑_images/98da815b.png)
###依赖关系作用
NarrowDependency可以在同一个流水线执行,
ShuffleDependency需要进行shuffle
##数据操作
transform:filter,map,reduce
action:count,sum,max
##结果处理
driver
##分区
1.水平划分,block,常用输入数据的分区
2.hash划分,常用于shuffle

#物理执行计划
[大数据处理架构Apache Spark设计]
逻辑处理流程
![](.z_04_spark_01_拓扑_images/143ff867.png)
![](.z_04_spark_01_拓扑_images/0994a37b.png)
##物理执行计划生成
1.根据action操作将应用划分为多个job，action:job=1:1
![](.z_04_spark_01_拓扑_images/5c42f7fd.png)
2.根据ShuffleDependency依赖关系将job划分为执行阶段stage,遇到NarrowDependency将parentRDD纳入
![](.z_04_spark_01_拓扑_images/29e6b015.png)
3.根据分区将各阶段划分为计算任务
![](.z_04_spark_01_拓扑_images/3746de15.png)
![](.z_04_spark_01_拓扑_images/4d81515b.png)
##阶段拆分
根据ShuffleDependency拆分阶段
##pipeline优化
根据NarrowDependency合并中间过程
![](.z_04_spark_01_拓扑_images/8a288308.png)
![](.z_04_spark_01_拓扑_images/e8d991d8.png)
#shuffle机制(上下游数据传递)
[](https://time.geekbang.org/column/article/420399)
![](.z_04_spark_01_拓扑_images/b242ee83.png)
##shuffle write
数据分区问题,
##shuffle read
##combine
![](.z_04_spark_01_拓扑_images/b9d386e2.png)
##spill溢写
##sort
##中间数据存储
#数据缓存机制
![](.z_04_spark_01_拓扑_images/f14d9015.png)
#错误容忍机制
checkpoint
#内存管理机制
#spark stream
##mini batch(吞吐大,延迟高)
![](.z_04_spark_01_拓扑_images/13547a8c.png)
微批
###Batch mode 容错(checkpoint/WAL 日志)
利用 Checkpoint 机制来实现容错。在实际处理数据流中的 Micro-batch 之前，Checkpoint 机制会把该 Micro-batch 的元信息全部存储到开发者指定的文件系统路径，
比如 HDFS 或是 Amazon S3。这样一来，当出现作业或是任务失败时，引擎只需要读取这些事先记录好的元信息，就可以恢复数据流的“断点续传”。
![](.z_04_spark_01_拓扑_images/edd2cf1d.png)
![](.z_04_spark_01_拓扑_images/203da839.png)
```asp

df.writeStream
// 指定Sink为终端（Console）
.format("console")
 
// 指定输出选项
.option("truncate", false)
 
// 指定Checkpoint存储地址
.option("checkpointLocation", "path/to/HDFS")
 
// 指定输出模式
.outputMode("complete")
//.outputMode("update")
 
// 启动流处理应用
.start()
// 等待中断指令
.awaitTermination()
```
###job
由于每个 Micro-batch 都会触发一个 Spark 作业，我们知道，作业与任务的频繁调度会引入计算开销，因此也会带来不同程度的延迟。在运行模式与容错机制的双重加持下，
Batch mode 的延迟水平往往维持在秒这个量级，在最好的情况下能达到几百毫秒左右。
##continuous(吞吐小,延迟低)
![](.z_04_spark_01_拓扑_images/08c07e34.png)
流处理,在 Continuous mode 下，Structured Streaming 使用一个常驻作业（Long running job）来处理数据流（或者说服务）中的每一条消息
Batch mode 吞吐量大、延迟高（秒级），而 Continuous mode 吞吐量低、延迟也更低（毫秒级）。吞吐量指的是单位时间引擎处理的消息数量，
批量数据能够更好地利用 Spark 分布式计算引擎的优势，因此 Batch mode 在吞吐量自然更胜一筹
###Epoch Marker
每当遇到 Epoch Marker 的时候，引擎都会把对应 Epoch 中最后一条消息的 Offset 写入日志，从而实现容错。需要指出的是，日志的写入是异步的，
因此这个过程不会对数据的处理造成延迟
![](.z_04_spark_01_拓扑_images/8169b181.png)
###聚合不支持
在 Continuous mode 下，Structured Streaming 仅支持非聚合（Aggregation）类操作，比如 map、filter、flatMap，等等。而聚合类的操作，
比如“流动的 Word Count”中的分组计数，Continuous mode 暂时是不支持的，这一点难免会限制 Continuous mode 的应用范围，需要你特别注意
##容错机制(Exactly once)
交付且仅交付一次，数据不重不漏，
指的是数据从 Source 到 Sink 的整个过程。对于同一条数据，它可能会被引擎处理一次或（在有作业或是任务失败的情况下）多次，
但根据容错能力的不同，计算结果最终可能会交付给 Sink 零次、一次或是多次

结合幂等的 Sink，Structured Streaming 能够提供 Exactly once 的容错能力
###At least once
在数据处理上，结合容错机制，Structured Streaming 本身能够提供“At least once”的处理能力
###幂等Sink
而结合幂等的 Sink，Structured Streaming 可以实现端到端的“Exactly once”容错水平
应用广泛的 Kafka，在 Producer 级别提供跨会话、跨分区的幂等性。结合 Kafka 这样的 Sink，在端到端的处理过程中，
Structured Streaming 可以实现“Exactly once”，保证数据的不重不漏
