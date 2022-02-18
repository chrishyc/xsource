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
#exactly once
#内存管理机制
#spark stream
##mini batch
##continuous

