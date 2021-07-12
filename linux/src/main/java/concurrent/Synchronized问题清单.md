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
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized对象模型.jpg)


wait
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized对象模型_2.jpg)
        
singal
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/synchronized对象模型_1.jpg)

signalAll()方法，相当于对等待队列中的每个节点均执行一次signal()方法，
效 果就是将等待队列中所有节点全部移动到同步队列中，并唤醒每个节点的线程
