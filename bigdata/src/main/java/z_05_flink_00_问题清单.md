#告警没有数据
flink积压,es收集不到数据,grafana报警
##flinke为啥积压
###网络抖动
checkpoint执行了30分钟
```asp
2021-12-23 15:11:08.734 INFO  org.apache.flink.runtime.checkpoint.CheckpointCoordinator    - Completed checkpoint 51364 for job 804dfe28b17fad5d38c48f9b3761f6ed (56504274 bytes in 316593 ms).
2021-12-23 15:11:38.748 INFO  org.apache.flink.runtime.checkpoint.CheckpointCoordinator    - Triggering checkpoint 51365 @ 1640243498733 for job 804dfe28b17fad5d38c48f9b3761f6ed.
```
grafana积压严重
flink container id业务日志,写入opentsdb超时失败
时序数据库中台技术排查是hbase网络抖动,后端opentsdb网络抖动的时候，是会报500这个code
es后端抖动,flink job失败不重试,flink一直进行故障恢复checkpoint,又写tsdb,tsdb又网络抖动,超时,维持了十几分钟,导致消息积压
不直接通过http请求打入tsdb,通过消息队列打入,如果后续还是抖动厉害，建议通过talos打平流量(按需拉取数据,不是主动http push)，通过talos页面上的OpenTSDBSink来写入
[](https://www.infoq.cn/article/hriwi6jdrsxombp4vgde)
这个 502 应该是OpenTSDB节点被写流量打卡了，建议把23:00 的写流量峰值打平下？  比如batch=100 单线程写下去？
在Sink时候，可以重试下就行
###hbase重启
c3 区域hbase 上午升级，会对c3区域opentsdb有短时间影响
##flink资源抢占问题


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

#某个subtask积压问题
