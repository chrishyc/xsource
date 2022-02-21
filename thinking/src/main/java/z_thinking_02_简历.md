开发环境:IDEA、Maven、Git、Nginx、docker、linux

软件架构:SpringBoot + Kafka + prometheus + alertmanager + grafana + flink + opentsdb + zookeeper + docker + shiro + 飞书机器人

项目描述: 

       信贷监控系统是小米金融信贷平台的业务监控系统和指标监控系统,负责监控分析业务成功率等业务日志事件,以及异常告警、延时等实时指标

技术描述:

        使用flink流处理实时处理分析日志事件

        通过opentsdb高可用方案持久化日志事件

        使用prometheus实时监控服务异常、延时、内存、io等指标

        通过zookeeper实现prometheus服务发现

        使用docker、docker compose对外提供轻量级的监控服务

        使用alertmanager实现高可用告警和静默

责任描述:

        日志事件监控系统的开发、维护

        prometheus指标监控的设计、开发、维护

        对外输出监控系统

解决的典型问题:

        1.监控告警服务每隔几天出现服务宕机,通过jmap、arthas、MAT分析是lambda计算表达式class字节码频繁创建未回收导致, 优化lambda计算表达式的创建

       2.prometheus exporter客户端每隔几小时minor gc、major gc告警,CPU飙升,jmap、arthas、MAT分析是prometheus api使用不当,导致tag持续创建,优化api使用




---------------------------





开发环境:IDEA  、Maven、Git 、Nginx、docker、linux

软件架构:SpringBoot + Kafka + flink + Elasticsearch + Hive + HDFS + grafana + Mysql

项目描述:

       风控平台是征信、贷款等业务的决策引擎,主要包括流程管理、决策表管理、模型管理、数据源接入、变量管理、变量质量分析、链路事件分析等模块

技术描述:

       使用Kafka实时sink变量事件

       使用flink流处理实时任务分析变量多维度指标

       使用Hive批处理分析变量历史指标

       使用Elasticsearch REST Client对多维度指标进行指标聚合、桶聚合、pipeline聚合

       kibana配置Elasticsearch索引数据结构、ILM生命周期、索引模板

       使用grafana可视化Elasticsearch时序图,并内嵌风控平台

 责任描述:

       变量质量分析监控方案的设计、开发

       yarn集群环境对flink job、hive job开发部署监控

       grafana、yarn ui监控kafka、flink运行时积压状态,背压状态

       kabana dev tool开发调试Elasticsearch文档结果

解决的典型问题:

       1.Elasticsearch深度分页问题,通过时间范围细化、滚动分页机制解决深度分页问题

       2.flink数据倾斜问题,通过预聚合、rebalance分区解决数据倾斜





---------------------------





开发环境：IDEA、Maven、Git

软件架构:SpringBoot + Zookeeper + Redis + Mysql + Hbase + Swagger + RocketMq

项目描述:

       海外贷超是小米金融海外的贷款平台,主要将平台的用户流量导入第三方资金方,由征信认证、授信、贷款、还款、账号、推荐活动、金融后台、风控审批等系统组成

技术描述:

      通过ByteBuddy字节码技术对日志链路植入traceId

      使用Mysql实现推荐奖励请求的幂等性

      通过Mq+事件表以及定时任务补偿机制保证了奖励更新的柔性一致性

      实现activiti风控审批流的自由跳转

      使用短网址方案精简推荐活动URL分享

      使用Redis缓存推荐码、短链接等热数据

      使用Redis分布式锁实现第三方token的刷新

      通过Hbase统计授信支用的每日报表

责任描述:

      负责推荐活动的方案设计、开发

      负责金融后台审核功能、用户协议的开发维护

      负责风控审批activiti的开发和维护

      负责用户隐私数据的日志脱敏

解决的典型问题:

      1.日志脱敏字节码解析异常,使用arthas、jclasslib分析字节码指令,解决解析问题

      2.审批流自由跳转恢复历史快照逻辑复杂,使用责任链实现李四快照子节点的逻辑解耦

      
