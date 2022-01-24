#管程

#happens-before
1.锁规则
monitor enter发生在monitor exit之前,临界区的代码对其他线程可见
银行家算法
```asp
先行发生原则(Happens-Before)是判断数据是否存在竞争、线程是否安全的主要依据。 先行发生是Java内存，模型中定义的两项操作之间的偏序关系，如果操作A先行发生于操作B，那么操作 A产生的影响能够被操作B观察到。 口诀:如果两个操作之间具有happen-before关系，那么前一个操作的结果就会对后面的一个操作可 见。是Java内存模型中定义的两个操作之间的偏序关系。
常见的happen-before规则:
1.程序顺序规则: 一个线程中的每个操作，happen-before在该线程中的任意后续操作。(注解:如果只有一个线程的操 作，那么前一个操作的结果肯定会对后续的操作可见。) 程序顺序规则中所说的每个操作happen-before于该线程中的任意后续操作并不是说前一个操作必须要 在后一个操作之前执行，而是指前一个操作的执行结果必须对后一个操作可见，如果不满足这个要求那
就不允许这两个操作进行重排序
2.锁规则: 对一个锁的解锁，happen-before在随后对这个锁加锁。(注解:这个最常见的就是synchronized方法和 syncronized块)
3.volatile变量规则: 对一个volatile域的写，happen-before在任意后续对这个volatile域的读。该规则在CurrentHashMap 的读操作中不需要加锁有很好的体现。
4.传递性:
如果A happen-before B，且B happen-before C，那么A happen - before C.
5.线程启动规则:
Thread对象的start()方法happen-before此线程的每一个动作。
6.线程终止规则: 线程的所有操作都happen-before对此线程的终止检测，可以通过Thread.join()方法结束， Thread.isAlive()的返回值等手段检测到线程已经终止执行。
7.线程中断规则: 对线程interrupt()方法的调用happen-before发生于被中断线程的代码检测到中断时事件的发生
```
