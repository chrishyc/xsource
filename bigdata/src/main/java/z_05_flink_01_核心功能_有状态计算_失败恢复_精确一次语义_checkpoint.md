#状态管理
```asp
本地性: Flink 状态是存储在使用它的机器本地的，并且可以以内存访问速度来获取
持久性: Flink 状态是容错的，例如，它可以自动按一定的时间间隔产生 checkpoint，并且在任务失败后进行恢复
纵向可扩展性: Flink 状态可以存储在集成的 RocksDB 实例中，这种方式下可以通过增加本地磁盘来扩展空间
横向可扩展性: Flink 状态可以随着集群的扩缩容重新分布
可查询性: Flink 状态可以通过使用 状态查询 API 从外部进行查询。
```
##Flink State
```asp
Flink是一个有状态的流式计算引擎，所以会将中间计算结果(状态)进行保存，默认保存到TaskManager 的堆内存中，但是当task挂掉，
那么这个task所对应的状态都会被清空，造成了数据丢失，无法保证结 果的正确性，哪怕想要得到正确结果，所有数据都要重新计算一遍，效率很低。
想要保证At -least- once和Exactly-once，需要把数据状态持久化到更安全的存储介质中，Flink提供了堆内内存、堆外内 存、HDFS、RocksDB等存储介质
```
##状态快照
状态持久化与恢复
![](.z_05_flink_01_核心功能_有状态计算_精确一次语义_checkpoint_images/8523876f.png)
所有这些 state backends 都能够异步执行快照，这意味着它们可以在不妨碍正在进行的流处理的情况下执行快照

快照 – 是 Flink 作业状态全局一致镜像的通用术语。快照包括指向每个数据源的指针（例如，到文件或 Kafka 分区的偏移量）
以及每个作业的有状态运算符的状态副本，该状态副本是处理了 sources 偏移位置之前所有的事件后而生成的状态。

##checkpoint 快照
Checkpoint – 一种由 Flink 自动执行的快照，其目的是能够从故障中恢复。Checkpoints 可以是增量的，并为快速恢复进行了优化。
 --checkpointin
##快照工作原理
##至少一次
##精确一次（exactly once）
##端到端精确一次

#失败恢复
##失败
```asp
由于 TaskManager 提供的 TaskSlots 资源不够用，Job 的所有任务都不能成功转为 RUNNING 状态，直到有新的 TaskManager 可用。在此之前，该 Job 将经历一个取消和重新提交 不断循环的过程。

与此同时，数据生成器 (data generator) 一直不断地往 input topic 中生成 ClickEvent 事件，在生产环境中也经常出现这种 Job 挂掉但源头还在不断产生数据的情况
```
##恢复
```asp
JobManager 就会将处于 SCHEDULED 状态的所有任务调度到该 TaskManager 的可用 TaskSlots 中运行，
此时所有的任务将会从失败前最近一次成功的 checkpoint 进行恢复， 一旦恢复成功，它们的状态将转变为 RUNNING
由于我们使用的是 FlinkKafkaProducer “至少一次"模式，因此你可能会看到一些记录重复输出多次
```
##升级与扩容
[](https://nightlies.apache.org/flink/flink-docs-release-1.14/zh/docs/try-flink/flink-operations-playground/#%E8%8E%B7%E5%8F%96%E6%89%80%E6%9C%89%E8%BF%90%E8%A1%8C%E4%B8%AD%E7%9A%84-job)
```asp
升级 Flink 作业一般都需要两步：第一，使用 Savepoint 优雅地停止 Flink Job。 Savepoint 是整个应用程序状态的一次快照（类似于 checkpoint ），
该快照是在一个明确定义的、全局一致的时间点生成的。第二，从 Savepoint 恢复启动待升级的 Flink Job。 在此，“升级”包含如下几种含义：

配置升级（比如 Job 并行度修改）
Job 拓扑升级（比如添加或者删除算子）
Job 的用户自定义函数升级
```
