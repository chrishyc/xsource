##技术选型
talos+flink+opentsdb+grafana
##talos
###是什么?
###为啥选择
###和其他开源的比较
6个维度,成本,可用性,扩展性,安全性,规模,性能
###局限
###遇到的问题/案例
##flink
主要flink怎么处理的数据，以及出现的问题，比如因为数据倾斜挂掉什么的
##opentsdb
考虑因素:数据分级、压缩、异构存储、横向扩展
写入吞吐吧，现在是每秒 480w 消息/s
[](https://juejin.cn/post/6844903545427361806)
一个是时序的，和我们不断生产的监控数据需求比较吻合。另外tsdb是基于hbase的，读写效率高
[](https://www.sohu.com/a/237660940_130419)
[](https://www.jianshu.com/p/5da398ae4017)
整体架构，技术选型（flink+tsdb+MQ），为什么选flink? 为什么选tsdb? 怎么保证消息队列可靠性，顺序性等。怎么做数据聚合，数据聚合力度等
写入吞吐吧，现在是每秒 480W
这个消息量，ES应该会崩溃吧
###对比influxdb
###对比elk
##grafna
