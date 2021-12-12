#临界知识
滚动式自动扩容和 缩容功能
#deployment
##扩容
Replicas(副本数量)
Selector(选择器)
Pod Template(Pod模板)
strategy(更新策略)
##滚动更新(金丝雀发布)
```asp
Deployment控制器支持自定义控制更新过程中的滚动节奏，如“暂停(pause)”或“继续(resume)”更新操 作。比如等待第一批新的Pod资源创建完成后立即暂停更新过程，
此时，仅存在一部分新版本的应用， 主体部分还是旧的版本。然后，再筛选一小部分的用户请求路由到新版本的Pod应用，继续观察能否稳 定地按期望的方式运行。
确定没问题之后再继续完成余下的Pod资源滚动更新，否则立即回滚更新操 作。这就是所谓的金丝雀发布(Canary Release)
```
[滚动数目控制](https://cloud.tencent.com/developer/article/1450306)
[滚动数目控制](https://kubernetes.io/zh/docs/concepts/workloads/controllers/deployment/)
```asp
  strategy:
    rollingUpdate:
      maxSurge: 1
```
##回滚

#Daemonset
#StatefulSet
```asp
在kubernetes系统中，Pod的管理对象RC，Deployment，DaemonSet和Job都面向无状态的服务，但 现实中有很多服务时有状态的，
比如一些集群服务，例如mysql集群，集群一般都会有这四个特点
```
