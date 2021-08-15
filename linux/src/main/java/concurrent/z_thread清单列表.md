##线程状态
[](https://stackoverflow.com/questions/41300520/what-is-locked-ownable-synchronizers-in-thread-dump)
```
死锁，Deadlock（重点关注） 
执行中，Runnable   
等待资源，Waiting on condition（重点关注） 
等待获取监视器，Waiting on monitor entry（重点关注）
暂停，Suspended
对象等待中，Object.wait() 或 TIMED_WAITING
阻塞，Blocked（重点关注）  
停止，Parked
```
```
1、locked<地址>目标：使用synchronized申请对象锁成功，监视器的拥有者；
2、waiting to lock<地址>目标：使用synchronized申请对象锁未成功，在进入区等待；
3、waiting on<地址>目标：使用synchronized申请对象锁成功后，调用了wait方法，进入对象的等待区等待。在调用栈顶出线，线程状态为WAITING或TIMED_WAITING；
4、parking to wait for<地址>目标：park是基本的线程阻塞原语，不通过监视器在对象上阻塞。随concurrent包出现的新的机制，与synchronized体系不同
```
[例子](https://www.cnblogs.com/kabi/p/5073706.html)
![](https://images.cnblogs.com/cnblogs_com/zhengyun_ustc/255879/o_clipboard%20-%20%E5%89%AF%E6%9C%AC039.png)

## 公共参数
tid指Java Thread id。nid指native线程的id。prio是线程优先级。[0x00007fd4f8684000]是线程栈起始地址。
```
"RMI TCP Connection(267865)-172.16.5.25" daemon prio=10 tid=0x00007fd508371000 nid=0x55ae waiting for monitor entry [0x00007fd4f8684000]
   java.lang.Thread.State: BLOCKED (on object monitor)
at org.apache.log4j.Category.callAppenders(Category.java:201)

```
## 线程状态

创建,停止,运行,sleep(time)
等待锁,获取到锁运行,获取到锁且sleep,等待队列wait
```
NEW,未启动的。不会出现在Dump中。

RUNNABLE,在虚拟机内执行的。运行中状态，可能里面还能看到locked字样，表明它获得了某把锁。

BLOCKED,受阻塞并等待监视器锁。被某个锁(synchronizers)給block住了。

WATING,无限期等待另一个线程执行特定操作。等待某个condition或monitor发生，一般停留在park(), wait(), sleep(),join() 等语句里。

TIMED_WATING,有时限的等待另一个线程的特定操作。和WAITING的区别是wait() 等语句加上了时间限制 wait(timeout)。

TERMINATED,已退出的。
```
```
1、runnable：状态一般为RUNNABLE，表示线程具备所有运行条件，在运行队列中准备操作系统的调度，或者正在运行。
2、in Object.wait()：等待区等待，状态为WAITING或TIMED_WAITING。
3、waiting for monitor entry：进入区等待，状态为BLOCKED。
4、waiting on condition：等待去等待，被park。
5、sleeping：休眠的线程，调用了Thread.sleep()。
```

```
locked <地址> 目标：使用synchronized申请对象锁成功,监视器的拥有者。

waiting to lock <地址> 目标：使用synchronized申请对象锁未成功,在迚入区等待。

waiting on <地址> 目标：使用synchronized申请对象锁成功后,释放锁幵在等待区等待。

parking to wait for <地址> 目标
```
[](https://blog.csdn.net/lmb55/article/details/79349680)
[](https://www.javatang.com/archives/2017/10/25/36441958.html#waiting_on_condition)
[](https://blog.csdn.net/liwenxia626/article/details/80791704)

##linux线程状态 vs jvm线程状态
[](https://zhuanlan.zhihu.com/p/133275094)
linux线程状态
![](.z_thread清单列表_images/linux线程状态.png)
```
操作系统中的线程除去new和terminated状态，一个线程真实存在的状态，只有：

ready ：表示线程已经被创建，正在等待系统调度分配CPU使用权。
running：表示线程获得了CPU使用权，正在进行运算
waiting：表示线程等待（或者说挂起），让出CPU资源给其他线程使用

无论是Timed Waiting ，Waiting还是Blocked，对应的都是操作系统线程的waiting（等待）状态

```
jvm线程状态
![jvm线程状态](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/thread_status.jpg)
## 线程常见问题
###runnable状态有io
###死锁
###大量锁等待

##线程状态
[等待队列](https://blog.csdn.net/weixin_44537992/article/details/105990158)
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/thread_status.jpg)
##sleep && join && yield && interrupt
sleep
![sleep](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/thread_sleep.jpg)
join
![join](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/thread_join.jpg)
yield
![yield](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/thread_yield.jpg)

###sleep&&join&&yield&&park&&interrupt
本质是park线程
[hotspot源码](https://blog.csdn.net/qq_26222859/article/details/81112446)
[park本质](https://www.jb51.net/article/216231.htm)
[源码分析](https://juejin.cn/post/6844903971463626766#heading-1)
sleep、wait和park最终都是借助于pthread_cond_timedwait实现阻塞，其中wait比较特殊的是，需要结合ObjectMonitor使用

[hotspot Parker 和 ParkEvent源码分析](https://blog.csdn.net/qq_31865983/article/details/105184585)
Parker是Unsafe类的park和unpark方法的核心，ParkEvent是Thread的sleep/wait方法，synchronized关键字中让线程休眠的核心


[LockSupport park unpark源码](https://juejin.cn/post/6844903729380982797#heading-6)
###park和interrupt
![](.z_thread清单列表_images/park interrupt.png)
底层都使用parker.park,parker.unpark
[概念架构](https://blog.csdn.net/anlian523/article/details/106752414)
###sleep&&join&&wait
底层都是ParkEvent.park, ParkEvent.unpark


##reference回收线程
```
"Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fc65b029800 nid=0x5103 in Object.wait() [0x0000700006bb9000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x0000000795586bf8> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
	- locked <0x0000000795586bf8> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
```

```
"Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fc65b02a800 nid=0x4f03 in Object.wait() [0x0000700006cbc000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x0000000795588ed0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
	- locked <0x0000000795588ed0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
```

##线程主动停止
两阶段终止模式:其他线程发送信号，目标线程响应信号,(linux信号处理的模式)
![](.z_thread清单列表_images/thread_stop.png)
