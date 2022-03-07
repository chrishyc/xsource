#临界知识
锁状态
GC标记
![](.z_3_运行时_04_对象头markword_对象布局_images/aaf4eba5.png)
![](.z_3_运行时_04_对象头markword_对象布局_images/54028ab9.png)
#内存
#对象创建过程(new指令)
[z_Synchronized问题清单.md]
```asp
1.首先将去检查这个指令的参数是否能在常量池中定位到 一个类的符号引用
2.检查这个符号引用代表的类是否已被加载、解析和初始化过,执行相应的类加载过程

3.为新生对象分配内存,对象所需内存的大小在类加载完成后便可完全确定,为对象分配空间的任务实际上便等同于把一块确定 大小的内存块从Java堆中划分出来
4.当使用Serial、ParNew等带压缩 整理过程的收集器时，系统采用的分配算法是指针碰撞,当使用CM S这种基于清除(Sweep)算法的收集器时,空闲列表来分配内存
5.本地线程分配缓冲,old,eden

6.内存分配完成之后，虚拟机必须将分配到的内存空间(但不包括对象头)都初始化为零值
7.Java虚拟机还要对对象进行必要的设置,类的实例、如何才能找到 类的元数据信息、对象的哈希码、对象的GC分代年龄等,偏向锁等

8.执行<init>()方法,执行invokespecial指令
```
![](.z_3_运行时_04_对象头markword_对象布局_images/d9dc83df.png)
##栈上分配
![](.z_3_运行时_04_对象头markword_对象布局_images/d4c0e3f8.png)
[](https://segmentfault.com/a/1190000016960388)
##TLAB
![](.z_3_运行时_04_对象头markword_对象布局_images/ba6dcb3a.png)
[](https://www.cnblogs.com/wuqinglong/p/14583108.html#:~:text=%E6%B3%A8%E6%84%8F%EF%BC%9A%E8%BF%99%E9%87%8CTLAB%20%E7%9A%84%E7%BA%BF%E7%A8%8B,%E5%8C%BA%E5%9F%9F%E4%B8%AD%E5%88%86%E9%85%8D%E5%86%85%E5%AD%98%E8%80%8C%E5%B7%B2%E3%80%82)
[](https://blog.csdn.net/u011069294/article/details/107326055)
XX:TLABWasteTargetPercent
###TLAB和栈上分配区别?
这是多线程Allocator的一个优化，试想，多个线程如果同时操控一个堆，如果要在堆上分配对象，那么是不是要加锁（或者原子操作）保证分配的原子性？
每分配一个对象都来个原子操作，那还怎么玩？所以TLAB就是这样一种神奇的存在，每个线程单独持有一个Allocation Buffer，自己玩自己的，当自己的buffer不够的时候，再重新搞一块buffer过来自己用（这时候需要原子操作），通过减少大量的原子操作来提高Allocator的性能
###在TLAB中创建的对象，如何被其他线程共享？
被其他线程共享
[](https://www.cnblogs.com/dyg0826/p/11039964.html)
[](https://www.zhihu.com/question/56538259/answer/149400767)
##内存管理
指针碰撞
空闲链表
#对象分配过程
![](.z_5_对象布局_对象分配__images/969a4b8a.png)
[z_4_内存管理_00_内存对象分配策略_分配担保_大对象分配_空闲列表_TLAB.md]
#对象布局

##对象头(markword,哈希码,GC分代,偏向锁)
![](.z_3_运行时_04_对象头markword_images/80dfca10.png)
![](.z_3_对象布局_images/5e2a718a.png)
##内存指针压缩(compressed oops）
[](https://blog.wangqi.love/articles/Java/Java%E6%8C%87%E9%92%88%E5%8E%8B%E7%BC%A9.html)
[](https://cloud.tencent.com/developer/article/1863051)
![](.z_3_运行时_04_对象头markword_对象布局_images/f51641b4.png)
在64位JVM中，这个指针是64位的。当开启Compressed Class Pointers之后，这个指针是32位的，为了找到真正的64位地址，需要加上一个base值
![](.z_3_运行时_04_对象头markword_对象布局_images/e2ffa641.png)
UseCompressedOops
```asp
它的指针不再表示对象在内存中的精确位置，而是表示 偏移量 。这意味着 32 位的指针可以引用 40 亿个 对象 ， 而不是 40 亿个字节。最终， 
也就是说堆内存增长到 32 GB 的物理内存，也可以用 32 位的指针表示。

一旦你越过那个神奇的 ~32 GB 的边界，指针就会切回普通对象的指针。 每个对象的指针都变长了，就会使用更多的 CPU 内存带宽，也就是说你实际上失去了更多的内存。
事实上，当内存到达 40–50 GB 的时候，有效内存才相当于使用内存对象指针压缩技术时候的 32 GB 内存。

这段描述的意思就是说：即便你有足够的内存，也尽量不要 超过 32 GB。因为它浪费了内存，降低了 CPU 的性能，还要让 GC 应对大内存
```
![](.z_3_运行时_04_对象头markword_对象布局_images/4623b7fc.png)
###压缩原理
###压缩对象
##实例数据
实例数据部分是对象真正存储的有效信息，即我们在程序代码里面所定义的各种类型的字 段内容，无论是从父类继承下来的，还是在子类中定义的字段都必须记录起来
##对齐填充

#常见问题
![](.z_3_对象布局_images/c2929e6d.png)

[](https://mp.weixin.qq.com/s/kKyJgNzgub4EdG7tkblbvw)
[自己动手写java虚拟机第四章]
[](https://zhuanlan.zhihu.com/p/332248004)
