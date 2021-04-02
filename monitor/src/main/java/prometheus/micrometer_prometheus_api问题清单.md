##什么是Distribution Summary
它的记录形式与Timer十分相似，但是记录的内容不依赖于时间单位，可以是任意数值，比如在监测范围内各个Http请求的响应内容大小时就可以使用Distribution Summary
##prometheus java客户端如何收集数据?

###方案:
1.如何收集客户端数据?
1.收集的客户端数据放在哪?
2.暴露http接口
[prometheus客户端实现方案](https://prometheus.io/docs/instrumenting/writing_clientlibs/)
四种类型收集必须实现的方法
指标命名
参数提供

###prometheus simpleclient_httpserver
```
this.server.createContext("/", mHandler);
this.server.createContext("/metrics", mHandler);
this.server.createContext("/-/healthy", mHandler);
```

Collector:counter,gauge,histogram,summary

CollectorRegistry


prometheus有全局defaultRegistry,所有的静态打点默认注册到 defaultRegistry


##micrometer如何收集数据
Collector(prome):MicrometerCollector(micro):child->PrometheusMeterRegistry(micro),CollectorRegistry

micrometer注册到一个实例化CollectorRegistry,具体注入逻辑在PrometheusMetricsExportAutoConfiguration

micrometer也支持全局CompositeMeterRegistry(micro自己的),会将PrometheusMeterRegistry添加入CompositeMeterRegistry(micro自己的)中

##micrometer 使用
counter
gauge
Timer延时打点

histogram默认bucket数?区间数?

1-min
2-min
5-min

##什么是Distribution Summary
它的记录形式与Timer十分相似，但是记录的内容不依赖于时间单位，可以是任意数值，比如在监测范围内各个Http请求的响应内容大小时就可以使用Distribution Summary
##prometheus java客户端如何收集数据?
1.如何收集客户端数据?
1.收集的客户端数据放在哪?
2.暴露http接口
[prometheus客户端实现方案](https://prometheus.io/docs/instrumenting/writing_clientlibs/)
四种类型收集必须实现的方法
指标命名
参数提供

##micrometer如何收集数据
