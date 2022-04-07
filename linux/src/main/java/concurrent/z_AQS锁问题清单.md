##对象模型
state，阻塞队列
cas+volatite+park
##CountDownLatch
##读写锁
![](images/rwreentrantlock_读写锁过程.jpg)
##面试题
[](https://javaguide.cn/java/concurrent/reentrantlock.html#_2-3-5-%E5%B0%8F%E7%BB%93)
###处于排队等候机制中的线程，什么时候可以有机会获取锁呢？
release,cas
###如果处于排队等候机制中的线程一直无法获取锁，需要一直等待么?
interrupt
###Lock 函数通过 Acquire 方法进行加锁，但是具体是如何加锁的呢？
