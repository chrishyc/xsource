[花费3元](https://gitbook.cn/books/5d80f062a55d761a1bdc56eb/index.html#-14)
#消息轨迹(单台机器,RMQ_SYS_TRACE_TOPIC队列,异步队列,批量发送,钩子)
默认情况下消息轨迹是存储在RMQ_SYS_TRACE_TOPIC，此外消息轨迹还可以存储在用户自定义的topic中

通常为了避免消息轨迹的数据与正常的业务数据混合在一起，官方建议，在Broker集群中，新增加一台机器，只在这台机器上开启消息轨迹跟踪，
这样该集群内的消息轨迹数据只会发送到这一台Broker服务器上，并不会增加集群内原先业务Broker的负载压力
[](https://www.modb.pro/db/222701)
