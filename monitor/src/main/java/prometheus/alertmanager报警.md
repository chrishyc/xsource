
##告警需求
```
Go Template 渲染警报内容；
管理警报的重复提醒时机与消除后消除通知的发送；
根据标签定义警报路由，实现警报的优先级、接收人划分，并针对不同的优先级和接收人定制不同的发送策略；
将同类型警报打包成一条通知发送出去，降低警报通知的频率；
支持静默规则: 用户可以定义一条静默规则，在一段时间内停止发送部分特定的警报，比如已经确认是搜索集群问题，在修复搜索集群时，先静默掉搜索集群相关警报；
支持"抑制"规则(Inhibition Rule): 用户可以定义一条"抑制"规则，规定在某种警报发生时，不发送另一种警报，比如在"A 机房网络故障"这条警报发生时，不发送所有"A 机房中的警报"；
```

[告警原理](https://zhuanlan.zhihu.com/p/63270049)
[告警原理](https://prometheus.io/docs/alerting/latest/configuration/)

##告警问题
告警次数
告警误报,使用rate,95—percent等统计方案
告警延迟
告警抑制

##报警语法
```
groups:
- name: example
  rules:
  - alert: HighRequestLatency
    expr: job:request_latency_seconds:mean5m{job="myjob"} > 0.5
    for: 10m
    labels:
      severity: page
    annotations:
      summary: High request latency
```
报警规则分类,主要方便查看:groups[](https://www.yxingxing.net/archives/alertmanager-20191218-config#groups)

###报警评估语法
expr触发条件
for 10m:10分钟后仍触发条件则报警
[for](https://prometheus.io/docs/prometheus/latest/configuration/alerting_rules/)

报警附加信息:label,annotations
###报警评估模板化语法
[$labels.<labelname>](https://prometheus.io/docs/prometheus/latest/configuration/alerting_rules/)

###报警评估时序序列


###报警评估UT
[](https://prometheus.io/docs/prometheus/latest/configuration/unit_testing_rules/)

###报警评估持续策略
we'll receive the same grouped alert notification again next time the rules are evaluated.

###报警通知语法
```
route:
  group_by: ['alertname']
  receiver: 'web.hook'
receivers:
- name: 'web.hook'
  webhook_configs:
  - url: 'http://127.0.0.1:5001/
```


###报警通知消失条件
b.[resolve_timeout](https://yunlzheng.gitbook.io/prometheus-book/parti-prometheus-ji-chu/alert/alert-manager-config)
在报警恢复的时候不是立马发送的，在接下来的这个时间内，如果没有此报警信息触发，才发送报警恢复消息。 默认值为5m。
c.Inhibition
d.Silences,通过alertmanager web ui

###报警通知状态
inactive、pending 或者 firing

1. pending：警报被激活，但是低于配置的持续时间。这里的持续时间即rule里的FOR字段设置的时间。改状态下不发送报警。
2. firing：警报已被激活，而且超出设置的持续时间。该状态下发送报警。
3. inactive：既不是pending也不是firing的时候状态变为inactive

只有在评估周期期间，警报才会从当前状态转移到另一个状态


###相同报警通知触发策略
报警触发后间隔repeat_interval时间再次触发

###重要参数
scrape_interval:从各种 metrics 接口抓取指标数据的时间间隔
evaluation_interval:Prometheus 对报警规则进行评估计算的时间间隔

rules.for:评估等待时间,持续指定时间后才生成报警
Because for requires that your alerting rule return the same time series for a period of time, your for state can be reset if a single rule evaluation does not contain a given time series. For example, if you are using a gauge metric that comes directly from a target, if one of the scrapes fails, then the for state will be reset if you had an alerting rule such as:

route.group_by:分组机制可以将详细的告警信息合并成一个通知
route.group_wait:新报警初始化等待时间当一个新的报警分组被创建后，需要等待至少 group_wait 时间来初始化告警。
route.group_interval:已存在报警等待时间,在等待 group_interval 时长后，然后再将触发的告警以及已解决的告警发送给 receiver,最快多久执行一次 Notification
route.repeat_interval:配置告警信息已经发送成功后，再次被触发发送的时间间隔
[参考](https://www.qikqiak.com/post/alertmanager-when-alert/)
[参考](https://www.robustperception.io/whats-the-difference-between-group_interval-group_wait-and-repeat_interval)
[参考](http://code2life.top/2020/02/28/0048-prometheus-in-action-start/)

##集群环境配置
[service discovery](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#alertmanager_config)
##集群环境报警触发策略
Grouping:集群
##报警数据存储
##集群数据同步
##动态部署
kill -SIGHUP alertmanager_pid
##rule中无法获取指标名__name__
[__name__ remove](https://www.robustperception.io/whats-in-a-__name__)
##使用record优化指标计算

