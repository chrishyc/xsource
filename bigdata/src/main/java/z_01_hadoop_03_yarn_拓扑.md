#yarn集群拓扑
![](.z_01_hadoop_03_yarn_拓扑_images/682269ca.png)
##ResourceManager
处理客户端请求、启动/监控ApplicationMaster、监控NodeManager、资源分配与调度;
##NodeManager
单个节点上的资源管理、处理来自ResourceManager的命令、处理来自ApplicationMaster的命令;
##ApplicationMaster
数据切分、为应用程序申请资源，并分配给内部任务、任务监控与容错
