##强引用
==null才会被gc回收

场景:普通对象
##软引用
内存不足才会回收,适合缓存

场景:map缓存
##弱引用
每次gc都回收,避免内存泄露，

场景:Threadlocal弱引用key,但Threadlocal v是强引用容易内存泄露,使用remove移除
leak
![](images/threadlocal_leak_memory.jpg)
remove not leak
![](images/threadlocal_remove.jpg)

[](https://zhuanlan.zhihu.com/p/91579723)
![](https://pic3.zhimg.com/80/v2-d95296da07a435875213f6727c270bca_1440w.jpg)
##虚引用
![](images/虚引用过程.jpg)
虚引用获取不到对象,不影响对象回收,对象回收后会插入到虚引用队列中，并唤醒reference线程执行队列中虚引用元素

每个虚引用元素如果有自己的队列，则将虚引用元素加入到自己的队列


场景:直接内存回收DirectByteBuffer，cleaner虚引用指向DirectByteBuffer，DirectByteBuffer回收后,jvm会唤醒
reference线程执行，reference线程判断是cleaner对象会执行cleaner.run进行直接内存回收

![](https://img-blog.csdnimg.cn/20200527120155177.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hvaG9vMTk5MA==,size_16,color_FFFFFF,t_70#pic_center)
[](https://blog.csdn.net/hohoo1990/article/details/106356145)
