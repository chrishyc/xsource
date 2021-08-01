参考文档:[计算机基础](http://mashibing.com/doc/)
##1.cpu怎么制作的?
硅,光刻机
##2.晶体管的工作原理?
p+n=二极管,n+p+n=三极管
##3.gpu工作过程?
内存将数据通过DMA传递给GPU
##4.汇编与机器码的关系?
助记符
##5.jvm机器码和本地机器码关系?
jvm机器码是jvm可理解的二进制文件
jvm将其转化为本地计算机机器码
##6.cpu可以和显示器,磁盘交互吗?
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/cpu架构图.jpg)
各种设备通过信号与cpu交互
##7.cpu的组成部分有哪些?
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/cpu内部图.jpg)
1.指令位置:pc,Program Counter 程序计数器 （记录当前指令地址）,指令不包括数据，指令存储数据
2.数据:寄存器,暂时存储CPU计算需要用到的数据
3.数据计算:alu,Arithmetic & Logic Unit 运算单元
4.数据读写:cu,Control Unit 控制单元
5.寻址:mmu,Memory Management Unit 内存管理单元
6.缓存.
##9.线程切换在cpu中的过程?
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/多核cpu.jpg)
需要保存当前线程的寄存器数据,pc数据并记录在缓存中
并将另一个线程的寄存器数据,pc数据读入寄存器和pc中
##10.cpu缓存机制，设置缓存为啥能优化时间?
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/存储缓存级别.jpg)
1.程序局部性原理
局部时间、空间原理
2.数据都是按块读取，程序从磁盘读取一个字节,实际是cpu发送读指令给DMA，DMA执行指令，磁盘通过主存总线直接发给内存(磁盘读取的数据不经过cpu，直接给内存,所以磁盘也有控制单元?).
磁盘每次读取一块4k到内存作为一页,然后从页中读取一个字节.

### cpu时钟发生器

###为什么是三级缓存?
实验测试得到的数据,时间和空间的平衡
1.CPU核心的运行频率远高于内存的运行频率，存储器成为瓶颈
2.使用运行频率和cpu相近的缓存，但是要考虑成本和缓存搜索时间，搜索时间不能超过上百个核心时钟周期
缓存不能做的太大,在此限制下要提高搜索速度
3.时间局部性，上次访问这次访问可能性很大，空间局部性，访问这个周边的也很有可能访问，所以采用分级
[参考大话计算机]


###缓存行cache line
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/缓存行.jpg)
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/缓存地址.jpg)
内存最小读写单位:1字节，8位
内存与缓存协作的统一最小单位cache line(块):32字节?8位的整数倍
内存地址=块地址+块内地址
缓存地址=块号+块内地址
###缓存数据如何定位到?
缓存中记录了cache line的块号，用块号和内存块号做对比
一个cache被分为S个组，每个组有E个cacheline，而一个cacheline中，有B个存储单元

###缓存行大小如何决定?
intel 64B

###命中率计算


##11.多核cpu下三级缓存机制的问题？

cache line伪共享问题,两个cpu从l3缓存操作同一份数据的两个不同变量
两个变量需要保证数据一致性
cache line 64B,64字节
###cache line参数
块号+块内地址+状态(2位)

内存1024*1024*1024B,cacheline 64B
则块号=1024*1024*16
块内地址=64/8=8

###cache line格式

tag=块号+块内地址+状态(dirty,invalid)

###缓存与cpu数据交换如何实现
是什么总线?

##cacheline与主存映射策略
组关联
###cacheline替换策略
LRU
FIFO

###缓存一致性协议MESI(缓存锁)
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/缓存一致性协议.jpg)

使用状态号MESI
[](https://www.cnblogs.com/z00377750/p/9180644.html)
![](https://images2018.cnblogs.com/blog/1014100/201806/1014100-20180613224959895-380715655.gif)
###多于一个cache line的数据共享问题
总线锁

###缓存行对齐编程




###volatile和cpu缓存的关系?
[volatitle与lock信号](https://blog.csdn.net/qq_26222859/article/details/52235930)  
当一个CPU修改缓存中的字节时，服务器中其他CPU会被通知，它们的缓存将视为无效。于是，在上面的情况下，核3发现自己的缓存中数据已无效，核0将立即把自己的数据写回主存，然后核3重新读取该数据
```
0x0000000002931351
        : lock add dword ptr [rsp],0h ;*putstatic instance
       

        ; - org.xrq.test.design.singleton.LazySingleton::getInstance
        @13 
        (line 
        14
        )
```

在lock前缀指令执行期间已经在处理器内部的缓存中被锁定（即包含该内存区域的缓存行当前处于独占或以修改状态），
并且该内存区域被完全包含在单个缓存行（cache line）中，那么处理器将直接执行该指令。
由于在指令执行期间该缓存行会一直被锁定，其它处理器无法读/写该指令要访问的内存区域，
因此能保证指令执行的原子性。这个操作过程叫做缓存锁定（cache locking），
缓存锁定将大大降低lock前缀指令的执行开销，但是当多处理器之间的竞争程度很高或者指令访问的内存地址未对齐时，
仍然会锁住总线
[volatiel与一致性协议关系](https://blog.csdn.net/zsxcomputer/article/details/113249953)
[一致性协议详解](https://wudaijun.com/2019/04/cpu-cache-and-memory-model/#valine-comments)

既然有了MESI协议，为什么还要volatile？这是因为MESI本身存在一些性能问题
（例如修改一个存在于其他CPU缓存的数据时，需要等待其他CPU的invalidate ack），
因此额外引入了store buffer和invalidate queue等存储结构来优化性能。
当数据仅存在于L1/L2 cache中时，MESI足可以保证volatile的内存语义，
但如果数据存在于store buffer或者invalidate queue中时，
仅靠MESI无法保证数据的一致性，这时需要引入内存屏障


主存

L1,L2,L3缓存控制器

cache line2个状态位

消息通信机制

内存屏障

主存总线

缓存总线,既然CPU有缓存一致性协议（MESI），为什么JMM还需要volatile关键字
##storebuff
[](https://www.zhihu.com/question/296949412)
[指令屏障](https://blog.csdn.net/qq_18433441/article/details/108585843)
[内存屏障](https://blog.csdn.net/wll1228/article/details/107775976)
[查看 lock指令](https://www.cnblogs.com/ITPower/p/13584321.html)
## invalid queue

##直写 vs 回写

## 原子操作
[原子操作](https://www.cnblogs.com/egmkang/p/14080645.html)
[环形总线](https://www.zhihu.com/question/346366744)
[原子性](https://ifeve.com/atomic-operation/)
[cmpxchg](https://www.zhihu.com/search?type=content&q=CMPXCHG)
[原子操作](https://www.bilibili.com/video/BV1Sp4y1h7bu?from=search&seid=1126417998132914696)
###cache line性能测试
![](/Users/chris/workspace/xsource/linux/src/main/resources/images/缓存padding.jpg)

##cpu一致性模型
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/cacheline.jpg)
[](https://www.zhihu.com/question/65372648)
CAS的特性使得他称为实现任何高层“锁”的必要的构建。
几乎所有的“锁”，如Mutex，ReentrantLock等都得用CAS让线程先原子性的抢到一个东西
（比如一个队列的头部），然后才能维护其他锁相关的数据。并且很有意思的是，
如果一个竞争算法只用到了CAS，却没有让线程“等待”，就会被称为“无锁算法”。
