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
这个时候如果JVM需要对stack和heap做一些操作该怎么办呢？
比如JVM要进行GC操作，或者要做heap dump等等，这时候如果线程都在对stack或者heap进行修改，那么将不是一个稳定的状态。
GC直接在这种情况下操作stack或者heap，会导致线程的异常。

safepoint就是一个安全点，所有的线程执行到安全点的时候就会去检查是否需要执行safepoint操作，如果需要执行，那么所有的线程都将会等待，直到所有的线程进入safepoint。


3.轻量锁升级发生在自旋一定次数后仍未获取锁.
![](https://static001.geekbang.org/resource/image/fd/f8/fd86f1b5cbac1f652bea58b039fbc8f8.jpg)
![](https://time.geekbang.org/column/article/101244)

##对象头实战
[](https://www.cnblogs.com/LemonFive/p/11246086.html)

##safepoint
[安全点排查](https://blog.csdn.net/superfjj/article/details/107855767)
[安全点实战](https://www.pianshen.com/article/36071068168/)
![安全点](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/jvm_safepoint.jpg)
-XX:+PrintSafepointStatistics

```
  0x0000000114cac061: test   %eax,-0x6cb3f67(%rip)        # 0x000000010dff8100
                                                ;   {poll_return}
```
##stop the world

##逃逸分析


##不能用string,integer,long,,null
string可能值相等但不是同一个对象,如果是同一个对象，范围是整个jvm，其他库也可能使用了string
integer,long包装类，-128~127,全局唯一,但是128是不同对象，如果每个线程都是128，其实是锁住不同线程
syn锁使用null会空指针,找不到引用的对象，monitor锁需要操作对象头
