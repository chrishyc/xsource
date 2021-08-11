========================集合&&copyonwrite&&阻塞队列&&hashmap&&ConcurrentHashMap============================
##cow
![](.集合问题清单_images/copy-on-write.png)
###方案
丢失一定的实时性,保证读线程最终一致性

###为什么需要复制数组呢？ 
如果将 array 数组设定为 volitile 的， 对 volatile 变量写 happens-before 读，读线程不是能够感知到 volatile 变量的变化
原因是，这里 volatile 的修饰的仅仅只是数组引用，数组中的元素的修改是不能保证可见性的。因此 COW 采用的是新旧两个数据容器，
通过第 5 行代码将数组引用指向新的数组。

##blocking queue
###ArrayBlockingQueue
![](.z_集合问题清单_images/ArrayBlockingQueue.png)
###LinkedBlockingQueue
![](.z_集合问题清单_images/LinkedBlockingQueue.png)
###linux如何实现阻塞指定时间?
###PriorityQueque

##ConcurrentHashMap
![](.z_集合__copyonwrite__阻塞队列__hashmap__ConcurrentHashMap_images/loadfactor.png)
###hash运算优化
[参考T26_Map_HashMap.java]  

[hash运算优化](https://juejin.cn/post/6844903583255642120#heading-5)  

优化过程:[%运算]->[性能差,使用二进制运算]->[二进制运算只考虑低位,冲突大]->[优化增加扰动,高位也考虑进来,]
###1.8扩容
![](.z_集合__copyonwrite__阻塞队列__hashmap__ConcurrentHashMap_images/多线程步长处理.png)
