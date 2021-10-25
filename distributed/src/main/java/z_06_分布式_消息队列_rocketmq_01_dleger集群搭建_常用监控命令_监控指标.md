##Dleger集群搭建
![](.z_06_分布式_消息队列_rocketmq_01_dleger集群搭建_常用监控命令_监控指标_images/afdd2036.png)
集群目录:/Users/chris/workspace/rocketmq-cluster
配置文件:/Users/chris/workspace/rocketmq-cluster/rocketmq-4.9.1/conf/dledger
start/stop脚本:/Users/chris/workspace/rocketmq-cluster/rocketmq-4.9.1/bin/dledger/fast-try.sh
[](https://juejin.cn/post/6844904199805730824#heading-10)
[](https://blog.51cto.com/u_15281317/3008349#4_DlegerrokcetMQ_586)
[四种集群模式](https://segmentfault.com/a/1190000038318572)
##常用命令
###查看集群选举情况
sh bin/mqadmin clusterList -n 127.0.0.1:9876
brokerId=0代表leader
###查看集群启动日志
tail -f ~/logs/rocketmqlogs/namesrv.log
tail -f ~/logs/rocketmqlogs/broker.log
