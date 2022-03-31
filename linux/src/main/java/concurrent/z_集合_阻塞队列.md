#Arrayblock
缓存行
```asp
阻塞队列
ArrayBlockingQueue:一个由数组结构组成的有界阻塞队列，线程池，生产者消费者 
LinkedBlockingQueue:一个由链表结构组成的无界阻塞队列，线程池，生产者消费者 
PriorityBlockingQueue:一个支持优先级排序的无界阻塞队列，可以实现精确的定时任务 
DelayQueue:一个使用优先级队列实现的无界阻塞队列，可以实现精确的定时任务 
SynchronousQueue:一个不存储元素的阻塞队列，线程池 LinkedTransferQueue:一个由链表结构组成的无界阻塞队列 
LinkedBlockingDeque:一个由链表结构组成的双向无界阻塞队列，可以用在“工作窃取”模式 中
```
#concurrentLinkedQueue
阻塞队列可以通过加锁来实现，非阻塞队列可以通过 CAS 操作实现
使用链表作为其数据结构
[](https://cloud.tencent.com/developer/article/1803570)
```asp
ConcurrentLinkedQueue是一个基于链接节点的无界线程安全队列，它采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部，
当我们获取一个元素时，它会返回队列头部的元素。此实现使用了一个基于所述的高效非阻塞算法在简单、快速、实用的非阻塞，阻塞并发队列算法
```
#SynchronousQueue
SynchronousQueue没有容量，是无缓冲等待队列，是一个不存储元素的阻塞队列，会直接将任务交给消费者，必须等队列中的添加元素被消费后才能继续添加新的元素。
[](https://blog.csdn.net/qq_26881739/article/details/80983495)
[](https://zhuanlan.zhihu.com/p/29227508)
