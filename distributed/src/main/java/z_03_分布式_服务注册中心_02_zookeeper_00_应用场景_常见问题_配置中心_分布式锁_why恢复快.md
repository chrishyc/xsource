##临界知识
读写分离(leader写,follow读+选举,observer读)
##prometheus服务发现
服务启动自动上报ip,monitor服务监控zk变化,然后写入prometheus文件
prometheus监听target(ip)文件的变化
##dubbo
zk注册中心
##zookeeper可以用作数据库吗?
1.所有的数据都在内存维护,znode数据为1M
2.最终一致性,要求过半节点应答才返回,吞吐量小
3.顺序写,无数据结构索引
4.树状目录是逻辑结构,本质都是在map中的key
5.zookeeper定位是分布式协调框架
##zookeeper
##分布式锁
使用session会话
[](https://blog.csdn.net/crazymakercircle/article/details/85956246)
[](http://git.mashibing.com/bjmashibing/InternetArchitect/blob/master/10%20Zookeeper/src/main/java/com/msb/zookeeper/locks/WatchCallBack.java)
1.服务宕机,zookeeper宕机
2.zookeeper宕机,会有多个线程持有的问题吗?
3.
###惊群
##重入锁

##zookeeper读写锁
[](https://juejin.cn/post/6844903710510809096)
1.服务如何判断文件是自己?
2.读写共存问题
```asp
读锁：又称共享锁，如果前面没有写节点，可以直接上锁；当前面有写节点时，则等待距离自己最近的写节点释放（ 删除 ）。
                     
写锁：如果前面没有节点，可以直接上锁；如果前面有节点，则等待前一个节点释放（ 删除 ）如果两个写节点之间有读节点，必需等待读节点释放之后再进行写节点请求，否则会有不可重复读的问题。                                         
```
##zookeeper为啥leader宕机恢复快?
选举快,leader+follow节点少,observer节点抗读压力,且不参与选举减少参与选举的节点
