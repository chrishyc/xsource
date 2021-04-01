##prometheus常见概念
rate速率
[prometheus常见函数](https://prometheus.io/docs/prometheus/latest/querying/functions/#histogram_quantile)
##直方图
[直方图本身的概念](https://zhuanlan.zhihu.com/p/32857009)
##prometheus累加直方图
[prometheus累加直方图](https://cloud.tencent.com/developer/article/1495303)

##histogram和summary概念
promQL语法了解
直方图统计概念了解:统计区间,bucket区间,纵坐标指标,归一化
[日志案例](https://www.robustperception.io/how-does-a-prometheus-summary-work)
[summary](https://www.robustperception.io/how-does-a-prometheus-summary-work)
[histogram](https://www.robustperception.io/how-does-a-prometheus-histogram-work)
[histogram日志](https://blog.csdn.net/wtan825/article/details/94616813)

histogram计算
```
histogram_quantile(0.9, rate(http_request_duration_seconds_bucket[10m]))
```
##百分位概念
[百分位案例](https://disksing.com/histogram-quantile/)
##prometheus histogram vs summary
[histogram&summary优缺点](https://blog.csdn.net/wtan825/article/details/94616813)

##prometheus histogram配置
1.使用客户端or服务端
2.区间段
3.bucket数量
4.期望最大最小数
[micrometer中使用histogram](https://micrometer.io/docs/concepts#_histograms_and_percentiles)