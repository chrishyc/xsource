#临界知识
冷热分离,存储异构
#写性能优化
```asp
1.增加refresh_interval的参数值，目的是减少segment文件的创建，减少segment的merge次数，merge是发生在jvm中的，有可能导致full GC，增加refresh会降低搜索的实时性
2.增加flush时间间隔，目的是减小数据写入磁盘的频率，减小磁盘IO
3.增加Buffer大小，本质也是减小refresh的时间间隔，因为导致segment文件创建的原因不仅有时间阈值，还有buffer空间大小，写满了也会创建。默认最小值 48MB< 默认值 堆空间的10% < 默认最大无限制
4.大批量的数据写入尽量控制在低检索请求的时间段，大批量的写入请求越集中越好，减小读写之间的资源抢占，读写分离
5.Lucene的数据的fsync是发生在OS cache的，要给OS cache预留足够的内从大小
6.通用最小化算法，能用更小的字段类型就用更小的，keyword类型比int更快
7.调整_source字段，通过include和exclude过滤
8.关闭Norms字段：计算评分用的，如果你确定当前字段将来不需要计算评分，设置false可以节省大量的磁盘空间，有助于提升性能。常见的比如filter和agg字段，都可以设为关闭

```
#搜索性能优化
[](https://zhuanlan.zhihu.com/p/406264041)
```asp
1.禁用swap
2.使用filter代替query，query使用评分
3.避免深度分页，避免单页数据过大，可以参考百度的做法(每次滚动10页缓存下来)。es提供两种解决方案scroll search和search after
4.冷热分离的架构设计
5.开启自适应副本选择（ARS）
6.enabled：是否创建倒排索引,doc_values索引
7.close 索引
```
[](https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-close.html)
#分片优化 & 索引优化
#存储异构
热温冷节点,ssd hhd,数据迁移
[](https://www.elastic.co/cn/blog/implementing-hot-warm-cold-in-elasticsearch-with-index-lifecycle-management)
[](https://www.cnblogs.com/iiiiher/p/9268832.html)

#通用调优思路
##通用法则
通用最小化算法：对于搜索引擎级的大数据检索，每个bit尤为珍贵
业务分离：聚合和搜索分离
##数据结构Mapping优化
优化字段的类型，关闭对业务无用的字段
##硬件优化
###jvm heap分配
```asp
Jvm heap大小不要超过物理内存的50%，最大也不要超过32GB（compressed oop），它可用于其内部缓存的内存就越多，但可供操作系统用于文件系统缓存
的内存就越少，heap过大会导致GC时间过长
```
###磁盘
```asp
ES应用可能要面临不间断的大量的数据读取和写入。生产环境可以考虑把节点冷热分离，“热节点”使用SSD做存储，可以大幅提高系统性能；冷数据存储在机械硬盘中，
降低成本。
```
###CPU
```asp
绑定cpu,后台进程绑定一个cpu,不去消耗读写线程
```
###网络

###集群
