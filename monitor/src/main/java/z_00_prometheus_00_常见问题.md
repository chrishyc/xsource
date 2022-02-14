##正则
###类似
[](https://prometheus.io/docs/prometheus/latest/querying/basics/)
!~
~=
###不区分大小写
[](https://stackoverflow.com/questions/53312007/prometheus-query-and-case-sensitivity)
(?i:model)
##prometheus是什么?
##项目中问题?
###常用功能
1.延时histogram,成功率rate,同环比 - ,更改label,sum by
2.
###优化
降低采样率,预查询,高基降,缩小范围
###公司的高可用
prometheus分片采集数据，然后remote write到远端存储，victoriametrics
###项目监控项目的数据量

###我们的prometheus是使用动态分片+一致性hash算法+victoriametrics远程存储吗?
###prometheus从exporter pull数据,即使是分片方式,每个prometheus也是拉取的全量exporter?会占用太多网络带宽?
###victoriametrics每次拉取数据需要合并，也是从所有分片拉取数据吗？
