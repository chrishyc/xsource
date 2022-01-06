#定位
```asp
Apache Flink is a framework and distributed processing engine for stateful computations over unbounded and bounded data streams.
 Flink has been designed to run in all common cluster environments, perform computations at in-memory speed and at any scale
 
 对 Flink 而言，其所要处理的主要场景就是流数据，批数据只是流数据的一个极限特例而已，所以 Flink 也是一款真正的流批统一的计算引擎
```
#选型
1、同时支持高吞吐、低延迟、高性能
2、支持事件时间(Event Time)概念，结合Watermark处理乱序数据 
3、支持有状态计算，并且支持多种状态 内存、文件、RocksDB 
4、支持高度灵活的窗口(Window)操作 time、count、session 
5、基于轻量级分布式快照(CheckPoint)实现的容错 保证exactly-once语义 
6、基于JVM实现独立的内存管理
7、Save Points(保存点)

#flink vs spark vs storm
流计算
