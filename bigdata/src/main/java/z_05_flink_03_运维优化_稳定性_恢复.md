[](https://flink.apache.org/zh/flink-operations.html)
#检查点的一致性
Flink的故障恢复机制是通过建立分布式应用服务状态一致性检查点实现的，当有故障产生时，应用服务会重启后，再重新加载上一次成功备份的状态检查点信息。
结合可重放的数据源，该特性可保证精确一次（exactly-once）的状态一致性。
#高效的检查点
Flink采用异步及增量的方式构建检查点服务
#集成多种集群管理服务
Flink已与多种集群管理服务紧密集成，如 Hadoop YARN, Mesos, 以及 Kubernetes。当集群中某个流程任务失败后，一个新的流程服务会自动启动并替代它继续执行
#内置高可用服务
Flink内置了为解决单点故障问题的高可用性服务模块，此模块是基于Apache ZooKeeper 技术实现的，Apache ZooKeeper是一种可靠的、交互式的、分布式协调服务组件
