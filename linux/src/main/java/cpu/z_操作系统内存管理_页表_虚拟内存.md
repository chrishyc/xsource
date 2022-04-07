#临界知识
而在使用虚拟内存的情况下，虚拟地址不是被直接送到内存总线上，而是被送到内存管理单元（Memory Management Unit，MMU），MMU把虚拟地址映射为物理内存地址
虚拟地址空间按照固定大小划分成称为页面（page）的若干单元。在物理内存中对应的单元称为页框（page frame）。页面和页框的大小通常是一样的，在本例中是4KB，现有的系统中常用的页大小一般从512字节到64KB。对应于64KB的虚拟地址空间和32KB的物理内存，我们得到16个虚拟页面和8个页框
##需求

1.支持多个进程同时运行
2.多个进程无法互相干扰
##方案
多进程->程序时间空间局部性原理->分页装入->分页机制
互相干扰->虚拟地址->虚拟内存->内存映射
##分页关键实现
分页初始化:如何知道程序有多少页,每页的数据结构,初始化缓存多少页?缓存在内核中?
载入分页:缺页时载入多少页?缺页中断执行过程?
页面替换(缓存满):->替换算法->LRU算法(leetcode 146)
##虚拟内存关键实现
虚拟地址空间大小:
虚拟地址空间的区间划分:
区间地址与实际地址映射关系:偏移量 + 段的基地址 = 线性地址 （虚拟空间）
地址映射实现:线性地址通过 OS + MMU（硬件 Memory Management Unit）

##cpu访问内存数据模型

cpu
MMU
Translation Lookaside Buffer(TLB)快表
L1
L2
L3
memory

虚拟地址

[](https://www.jianshu.com/p/b6356e0ec63c)
物理地址

##为什么使用虚拟地址?物理地址有什么问题?

##内存分段
![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/内存分段.jpg)
内存区间分为
代码段
数据段
bss段
rodata
stack
heap
每段以整体放入内存

段内部采用虚拟地址

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/内存分段寻址.jpg)
地址转换:由基地址寄存器,变址寄存器将虚拟地址转化为物理地址

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/内存分段管理.jpg)
内存管理:空闲内存链表维护

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/内存分段碎片.jpg)
缺点:段进行置换时,存在内部外部碎片,空闲区域整合影响性能

##交换技术


##内存分页
![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/内存分页.jpg)
采用虚拟内存技术
每个应用拥有自己的虚拟地址空间,0开始
并将虚拟内存划分为以页为单位管理,每个进程使用页表管理

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/页表项.jpg)
页表项

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/页表.jpg)
页表
![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/分级页表.jpg)
分级页表

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/缺页中断.jpg)
缺页中断

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/MMU.jpg)
MMU

![](/Users/chris/workspace/xsource/linux/src/main/java/cpu/images/缺页中断.jpg)
TLB


页面置换算法
LRU

##ZGC
算法叫做：Colored Pointer

GC信息记录在指针上，不是记录在头部， immediate memory use

42位指针 寻址空间4T JDK13 -> 16T 目前为止最大16T 2^44

##CPU如何区分一个立即数 和 一条指令
总线内部分为：数据总线 地址总线 控制总线

地址总线目前：48位

颜色指针本质上包含了地址映射的概念
