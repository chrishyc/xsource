为啥对外需要使用另一套监控系统?
生态不一样,扩展性需要更高

##0.技术选型
###指标收集
a.Spring Boot Actuator
b.jmx
c.Micrometer  
[micrometer](https://micrometer.io/docs)
[micrometer指标](https://www.cnblogs.com/throwable/p/13257557.html)

###监控
b.Prometheus
[Prometheus使用以及主要概念](https://www.cnblogs.com/chenqionghe/p/10494868.html)
[prometheus 指标与标签](https://prometheus.io/docs/concepts/data_model/)
使用Prometheus的原因?

对比open-falcon vs prometheus?
[open-falcon vs prometheus](https://blog.csdn.net/u012118189/article/details/103062590)

e.时序数据库
Influx,Datadog

###监控视图
c.Grafana
[grafana tuotial](https://grafana.com/tutorials/grafana-fundamentals/)
[grafana api使用](https://grafana.com/docs/grafana/latest/http_api/)
[grafana变量模板化](https://yunlzheng.gitbook.io/prometheus-book/part-ii-prometheus-jin-jie/grafana/templating)
[grafana link tuotial](https://grafana.com/docs/grafana/latest/linking/dashboard-links/)
Grafana是否有临时dashborad?

###容器化
f.docker容器化
g.k8s技术

##1.监控概念
counter,gauge

##2.方案选择？
有几种方案?各方案的优缺点
a.公司集群:数据暴露在公网,数据消耗带宽
b.自建:集群需要自行管理,时序数据库需要管理,安全需要自行考虑,性能/扩展/可用性自行考虑

选自建,可以用公司的自建Prometheus吗?

##3.Prometheus使用
prometheus --config.file=/usr/local/etc/prometheus.yml
http://localhost:9090/graph

###docker启动方式

###Prometheus组件架构
####Prometheus Server
a.Service Discovery的方式动态管理监控目标


b.Prometheus Server的联邦集群能力可以使其从其他的Prometheus Server实例中获取数据

####Exporters

####AlertManager
[webhook](https://segmentfault.com/a/1190000020249988)
[特性](https://prometheus.io/docs/alerting/latest/alertmanager/)

##4.grafana使用
// start
brew services start grafana
// stop
brew services stop grafana
http://127.0.0.1:3000/

##5.grafana alter和prometheus报警区别
###使用
grafana:plane配置，面板管理，sqlite
使用grafana报警
优点:视图ui,ha
缺点:不可使用变量,分组

prometheus:分组，抑制，Silences
使用prometheus，报警
优点:分组，变量
缺点:无视图

方案

##6.报警需求分析
a.[报警触发条件](https://grafana.com/docs/grafana/latest/alerting/)

b.报警监控周期和延迟时间
c.报警停止时间
d.报警持久化地方
e.[HA](https://grafana.com/docs/grafana/latest/administration/set-up-for-high-availability/)
[grafana database](https://grafana.com/docs/grafana/latest/administration/configuration/#database)
[grafana sqlite](https://groups.io/g/grafana/topic/where_does_grafana_2_6_store/1895528)



##7.Prometheus原理
##8.时序数据库
##9.micrometer



4.扩展性,falcon/普罗米修斯,
5.可用性,docker
6.性能
##10.安全性
spring Boot Actuator[安全漏洞](https://github.com/LandGrey/SpringBootVulExploit#0x04jolokia-logback-jndi-rce)
[Spring Security原理](https://www.cnblogs.com/zyly/p/12286285.html)
[actuator prometheus 安全配置](https://prometheus.io/docs/prometheus/latest/configuration/configuration/)
[prometheus nginx安全配置](https://prometheus.io/docs/guides/basic-auth/)
[prometheus nginx安全配置](https://www.jianshu.com/p/edd9c17d8c8b)
[macos端口禁用](https://www.cnblogs.com/easonjim/p/7819478.html)

##11.k8s学习
