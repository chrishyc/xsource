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
