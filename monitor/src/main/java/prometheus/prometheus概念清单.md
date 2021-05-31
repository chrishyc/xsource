## prometheus常见概念
rate速率
[prometheus常见函数](https://prometheus.io/docs/prometheus/latest/querying/functions/#histogram_quantile)

###irate和rate区别
irate和rate都会用于计算某个指标在一定时间间隔内的变化速率。但是它们的计算方法有所不同：irate取的是在指定时间范围内的最近两个数据点来算速率，
而rate会取指定时间范围内所有数据点，算出一组速率，然后取平均值作为结果

###sum函数
指标名相同,tag不同会形成多个指标时序
将相同指标名的多个指标时序求和
[sum函数含义](https://my.oschina.net/54188zz/blog/3070582)
```
sum(metricsName{}[]) by (tag)
sum(increase(node_cpu_seconds_total{mode="idle"}[1m]))by (instance)
```

###平均值

###中位数
中位数（Median）又称中值，统计学中的专有名词，是按顺序排列的一组数据中居于中间位置的数，代表一个样本、种群或概率分布中的一个数值，其可将数值集合划分为相等的上下两部分

###长尾问题

## 直方图
[直方图本身的概念](https://zhuanlan.zhihu.com/p/32857009)
按区间划分样本的分布,计算延时使用的时间区间

##prometheus累加直方图
[prometheus累加直方图](https://cloud.tencent.com/developer/article/1495303)

```
# TYPE dubbo_invoke_seconds histogram
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.001",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.001048576",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.001398101",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.001747626",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.002097151",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.002446676",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.002796201",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.003145726",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.003495251",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.003844776",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.004194304",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.005592405",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.006990506",} 2.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.008388607",} 3.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.009786708",} 3.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.011184809",} 3.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.01258291",} 3.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.013981011",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.015379112",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.016777216",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.022369621",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.027962026",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.033554431",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.039146836",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.044739241",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.050331646",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.055924051",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.061516456",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.067108864",} 4.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.089478485",} 11.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.111848106",} 48.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.134217727",} 94.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.156587348",} 132.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.178956969",} 146.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.20132659",} 156.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.223696211",} 160.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.246065832",} 160.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.268435456",} 160.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.357913941",} 162.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.447392426",} 163.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.536870911",} 165.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.626349396",} 172.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.715827881",} 173.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.805306366",} 174.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.894784851",} 174.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="0.984263336",} 174.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="1.073741824",} 174.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="1.431655765",} 175.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="1.789569706",} 176.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="2.147483647",} 176.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="2.505397588",} 176.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="2.863311529",} 176.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="3.22122547",} 176.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="3.579139411",} 177.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="3.937053352",} 177.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="4.294967296",} 178.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="5.726623061",} 180.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="7.158278826",} 181.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="8.589934591",} 181.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="10.021590356",} 182.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="11.453246121",} 182.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="12.884901886",} 183.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="14.316557651",} 183.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="15.748213416",} 183.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="17.179869184",} 183.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="22.906492245",} 184.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="28.633115306",} 184.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="30.0",} 184.0
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",le="+Inf",} 184.0
dubbo_invoke_seconds_count{application="mifi-policy-platform-execution",} 184.0
dubbo_invoke_seconds_sum{application="mifi-policy-platform-execution",} 94.688
```

le<=28.633115306的请求有184个
请求延时为[le="22.906492245",le="28.633115306"]区间的请求有0个

prometheus会将instance等公共label附加上,最终指标类似:
dubbo_invoke_seconds_bucket{application="mifi-policy-platform-execution",instance="tj1-miui-micfc-ksc-vr1-10293-ko2v4abi4.kscn:8890",job="mifi-policy-platform-execution"}


histogram_quantile(0.95, sum by (instance,le) (rate(dubbo_invoke_seconds_bucket[5m])))

###为啥使用累加直方图
[](https://www.robustperception.io/why-are-prometheus-histograms-cumulative)
删减一些bucket,其他区间值不变,不需要重新计算

###90分位计算原理
90%数值都<=该数值


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


##prometheus expression语言
[prometheus template](https://prometheus.io/docs/prometheus/latest/configuration/template_examples/)
###rule expr、description加入变量表达式
expr: '{__name__=~"order_amount_sum_count.*"}'	
description: "http://grafana.staging.mifi.pt.xiaomi.com/d/HBrq2YlGk/temporary-dashboard?orgId=1&fullscreen&panelId=2&var-metrics={{ $labels.__name__ }}"
获取所有标签:label_names()

###prometheus record/alert rule中label丢失问题
[](https://stackoverflow.com/questions/67273594/missing-labels-in-prometheus-alerts)
验证丢失的label,使用prometheus面板
