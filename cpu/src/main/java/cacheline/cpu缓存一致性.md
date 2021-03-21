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
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/cpu架构图.jpg)
各种设备通过信号与cpu交互
##7.cpu的组成部分有哪些?
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/cpu内部图.jpg)
1.指令位置:pc,Program Counter 程序计数器 （记录当前指令地址）,指令不包括数据，指令存储数据
2.数据:寄存器,暂时存储CPU计算需要用到的数据
3.数据计算:alu,Arithmetic & Logic Unit 运算单元
4.数据读写:cu,Control Unit 控制单元
5.寻址:mmu,Memory Management Unit 内存管理单元
6.缓存.
##8.jvm的组成部分?
todo:
##9.线程切换在cpu中的过程?
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/多核cpu.jpg)
需要保存当前线程的寄存器数据,pc数据并记录在缓存中
并将另一个线程的寄存器数据,pc数据读入寄存器和pc中
##10.cpu缓存机制，设置缓存为啥能优化时间?
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/存储缓存级别.jpg)
1.程序局部性原理
局部时间、空间原理
2.数据都是按块读取，程序从磁盘读取一个字节,实际是cpu发送读指令给DMA，DMA执行指令，磁盘通过主存总线直接发给内存(磁盘读取的数据不经过cpu，直接给内存,所以磁盘也有控制单元?).
磁盘每次读取一块4k到内存作为一页,然后从页中读取一个字节.

### cpu时钟发生器

###为什么是三级缓存?
实验测试得到的数据,时间和空间的平衡

###缓存行cache line
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/缓存行.jpg)
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/缓存地址.jpg)
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
###缓存一致性协议MESI(缓存锁)
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/缓存一致性协议.jpg)

使用状态号MESI
https://www.cnblogs.com/z00377750/p/9180644.html
###多于一个cache line的数据共享问题
总线锁

###缓存行对齐编程

###volatile和cpu缓存的关系?
[volatitle与lock信号](https://blog.csdn.net/qq_26222859/article/details/52235930)  
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

缓存总线

###cache line性能测试
![](/Users/chris/workspace/xsource/cpu/src/main/resources/images/缓存padding.jpg)
