##DataTree
存储所有的zookeeper路径和数据内容
![](.z_03_分布式_服务注册中心_02_zookeeper_04_重要数据结构_images/6e38bd45.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_04_重要数据结构_images/601a1529.png)
##DataNode
节点信息的最小单位,包括数据内容，ACL列表,节点状态
![](.z_03_分布式_服务注册中心_02_zookeeper_04_重要数据结构_images/03c24385.png)

##ZKDatabase
负责管理会话,DataTree快照存储，和事务持久化
![](.z_03_分布式_服务注册中心_02_zookeeper_04_重要数据结构_images/a0b81fdc.png)
##FileTxnLog
![](.z_03_分布式_服务注册中心_02_zookeeper_04_重要数据结构_images/d1b0d93c.png)
