#告警没有数据,grafana查看有数据
flink积压,es收集不到数据,grafana报警
##flinke为啥积压
checkpoint执行了30分钟
```asp
2021-12-23 15:11:08.734 INFO  org.apache.flink.runtime.checkpoint.CheckpointCoordinator    - Completed checkpoint 51364 for job 804dfe28b17fad5d38c48f9b3761f6ed (56504274 bytes in 316593 ms).
2021-12-23 15:11:38.748 INFO  org.apache.flink.runtime.checkpoint.CheckpointCoordinator    - Triggering checkpoint 51365 @ 1640243498733 for job 804dfe28b17fad5d38c48f9b3761f6ed.
```
es后端抖动,flink失败不重试,flink一直故障重启,导致消息积压
后端opentsdb抖动的时候，是会报500这个code
[](https://www.infoq.cn/article/hriwi6jdrsxombp4vgde)
#flink积压太多

#状态管理
```asp
本地性: Flink 状态是存储在使用它的机器本地的，并且可以以内存访问速度来获取
持久性: Flink 状态是容错的，例如，它可以自动按一定的时间间隔产生 checkpoint，并且在任务失败后进行恢复
纵向可扩展性: Flink 状态可以存储在集成的 RocksDB 实例中，这种方式下可以通过增加本地磁盘来扩展空间
横向可扩展性: Flink 状态可以随着集群的扩缩容重新分布
可查询性: Flink 状态可以通过使用 状态查询 API 从外部进行查询。
```
##
##状态快照
状态持久化与恢复
![](.z_05_flink_01_核心功能_有状态计算_精确一次语义_checkpoint_images/8523876f.png)
所有这些 state backends 都能够异步执行快照，这意味着它们可以在不妨碍正在进行的流处理的情况下执行快照

快照 – 是 Flink 作业状态全局一致镜像的通用术语。快照包括指向每个数据源的指针（例如，到文件或 Kafka 分区的偏移量）
以及每个作业的有状态运算符的状态副本，该状态副本是处理了 sources 偏移位置之前所有的事件后而生成的状态。

##checkpoint 快照
Checkpoint – 一种由 Flink 自动执行的快照，其目的是能够从故障中恢复。Checkpoints 可以是增量的，并为快速恢复进行了优化。
##快照工作原理

##精确一次（exactly once）
##端到端精确一次
