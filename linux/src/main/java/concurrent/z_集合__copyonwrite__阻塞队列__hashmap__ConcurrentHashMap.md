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
![](.z_集合__copyonwrite__阻塞队列__hashmap__ConcurrentHashMap_images/concurrenthashmap1.8.png)
##hashmap
[](https://github.com/Snailclimb/JavaGuide/blob/main/docs/java/collection/hashmap-source-code.md)
1.HashMap 可以存储 null 的 key 和 value，但 null 作为键只能有一个，null 作为值可以有多个
  
2.当链表长度大于阈值（默认为 8）（将链表转换成红黑树前会判断，如果当前数组的长度小于 64，那么会选择先进行数组扩容，而不是转换为红黑树）时，将链表转化为红黑树，以减少搜索时间

3. 默认的初始化大小为 16。之后每次扩充，容量变为原来的 2 倍

![](.z_集合__copyonwrite__阻塞队列__hashmap__ConcurrentHashMap_images/01fdebe6.png)
![](.z_集合__copyonwrite__阻塞队列__hashmap__ConcurrentHashMap_images/e336eca8.png)
###key为空时hashcode=0
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
###会扩容两次吗?
不会,当链表长度大于阈值（默认为 8）第一次扩容后,第2次判断时就不用再扩容
##容器
list,set,queue,map
list:arraylist,linkedlist,copyonwritearraylist,ConcurrentLinkedQueue
set:hashset,treeset,linkedhashset
queue:优先队列,双端队列,BlockingQueue
map:hashmap,treemap,linkedhashmap,concurrenthashmap,ConcurrentSkipListMap
[](https://javaguide.cn/java/concurrent/java-concurrent-collections.html#concurrentskiplistmap)
