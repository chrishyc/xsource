1.synchronized原理，mutex
2.synchronized对象模型
3.几种实现
2.synchronized优化偏向锁，自旋锁
3.内核底层原理
4.cpu锁总线，锁缓存
5.cache总线

6.无锁

https://www.cnblogs.com/aspirant/p/11470858.html

https://cloud.tencent.com/developer/article/1082708


##synchronized对象模型
![](https://juejin.cn/post/6844903735265771527)

![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized对象模型.jpg)


wait
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized对象模型_2.jpg)
        
singal
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized对象模型_1.jpg)

signalAll()方法，相当于对等待队列中的每个节点均执行一次signal()方法，
效 果就是将等待队列中所有节点全部移动到同步队列中，并唤醒每个节点的线程


##synchronized锁升级模型
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized锁升级.jpg)
1.第二个线程仍是偏向锁,是在第一个线程释放偏向锁之后,第二个线程CAS无锁->偏向锁
2.偏向锁升级/取消偏向锁,是第二个线程CAS无锁—>偏向锁 失败,需要在safepoint处对相关线程stop the world,因为要获取每个线程使用锁的状态以及运行状态
3.轻量锁升级发生在自旋一定次数后仍未获取锁.
![](https://static001.geekbang.org/resource/image/fd/f8/fd86f1b5cbac1f652bea58b039fbc8f8.jpg)
![](https://time.geekbang.org/column/article/101244)

##对象头实战
[](https://www.cnblogs.com/LemonFive/p/11246086.html)

##safepoint
[安全点排查](https://blog.csdn.net/superfjj/article/details/107855767)
[安全点实战](https://www.pianshen.com/article/36071068168/)
-XX:+PrintSafepointStatistics
##stop the world

##逃逸分析
