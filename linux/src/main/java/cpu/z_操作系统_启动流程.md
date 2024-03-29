##1.什么是操作系统?
1.硬件:cpu/内存/设备/设备中断
2.软件:进程调度/文件管理
##什么是BIOS,CMOS,RTC是什么?
[BIOS和CMOS关系](https://jingyan.baidu.com/article/a378c960ff01edb32928304c.html)


###BIOS是什么?
ROM芯片不可修改，内存频率等参数存在CMOS里

其实，它是一组固化到计算机内主板上一个ROM芯片上的程序，它保存着计算机最重要的基本输入输出的程序、
开机后自检程序和系统自启动程序，它可从CMOS中读写系统设置的具体信息
[BIOS介绍](https://baike.baidu.com/item/bios#2)
###BIOS做什么
上电自检
内存频率
设备驱动
引导程序
##BIOS自检原理
BIOS
设备总线的地址信息
电平信号 
地址总线
芯片引脚
[BIOS](https://blog.51cto.com/dog250/1271095?source=dra)

###BIOS怎么做?
通过BIOS设置程序对CMOS参数进行设
###知名厂商
AMI BIOS

###为啥需要BIOS，而不是直接启动操作系统


###什么是CMOS(complementary metal oxide semiconductor)
互补金属氧化物半导体，保存时间信息,可读写的RAM芯片,它存储了计算机系统的时钟信息和硬件配置信息等。

###RTC
实时时钟

###RTC电池
CMOS电池，断电维持CMOS时间更新,维持CMOS RAM信息

CPU芯片

##2.什么是内核?
##3.什么是宏内核?
pc/手机端，内核控制cpu/内存/存储访问
##4.什么是微内核?
[什么是微内核?](https://zhuanlan.zhihu.com/p/77428675)
iot,内核不控制内存/设备,应用程序调度，弹性部署
[微内核minix docker](https://hub.docker.com/r/madworx/minix)
![微内核进程](/Users/chris/workspace/xsource/linux/src/main/java/os/images/微内核进程.jpg)
![微内核read过程](/Users/chris/workspace/xsource/linux/src/main/java/os/images/微内核read过程.jpg)
![微内核read过程](/Users/chris/workspace/xsource/linux/src/main/java/os/images/微内核read请求纵向分析.jpg)
[微内核原理](https://mp.weixin.qq.com/s?__biz=MzAwMDUwNDgxOA==&mid=2652666131&idx=1&sn=7cb56350104265c289809fe3b9a03256&chksm=810f3f8eb678b698433c62d3118ad1e5a3c49e3dd6fd6c019b28966ee69629ad63f9aaedf27b&scene=21#wechat_redirect)
[微内核知乎](https://www.zhihu.com/question/339638625)
##vmm
##用户态/内核态?
对硬件资源进行控制
内核态能执行r0级别指令，读写网卡/磁盘/显卡，200多个系统调用 sendfile read write pthread fork 
用户态只能执行r3级别指令
[计算机怎么知道用户态和内核态](https://www.zhihu.com/question/26188312)

##什么是内核态用户态?
[大话计算机]
内核地址
用户空间地址

基地址寄存器
长度地址寄存器
CPU特权指令,R0
###为什么操作系统可以执行特权指令?用户态不可以执行?

###内核切换用户态?

###用户态切换内核态?
int80软中断，查询中断向量表，提升权限级别

##用户态/内核态切换干了什么?

##保护模式vs实模式
[大话计算机]
保护模式:用户空间
实模式:内核空间(DOS操作系统)


GDT全局描述符表

GDT权限检查

CS段寄存器:选择子(GDT表项地址)+CPL(current privilege level,2位)

RPL(request privilege level)  DRL(data privilege level)  CRL(control privilege level)

LDT(local descriptor table)

LDTR(local descriptor table descriptor)


##操作系统如何避免应用程序更改特权级别

###分区操作系统
进程的内存地址分配、增长问题
对应的地址寄存器增长问题

###分页操作系统

###单核vs多核操作系统

##进程调度原理
单核CPU进程调度
多核CPU进程调度

##进程内部结构

程序文件分段:数据段,代码段
时间空间局部性原理

分区执行

段基地址寄存器：CS,DS,SS,ES

程序可以基于段为力度放在内存不同区域
![段基寄存器](/Users/chris/workspace/xsource/linux/src/main/java/os/images/段基寄存器.jpg)

##进程和线程的区别?
进程是OS分配资源的基本单位，线程是执行调度的基本单位

进程分配的资源包括:独立的内存地址,每个大概4G(包括虚拟存储),文件描述符,进程表PCB

线程:调度执行（线程共享进程的内存空间，没有自己独立的内存空间）,实现上看就是指令栈的进出和切换

##内核态线程与用户态线程
TSS
##内核线程实现与用户线程实现


##线程实现方案
linux：线程是普通进程，多个进程共享一个内存空间，达到线程的概念

[c语言用户态线程实现](https://github.com/ivanallen/MyThread)


##内核线程模型
hotspot线程,重量级线程:   用户态线程:内核态线程=1:1,内核态线程就是一个linux普通进程,和其他进程共享内存空间
涉及内核态和用户态的切换,切换时间消耗大

fiber，协程,轻量级协程,用户态切换，不涉及内核态切换: 用户态协程:用户态线程:内核态线程=n:1:1,用户态多个协程对应jvm一个用户态线程,一个用户态线程分片执行多个协程.

###用户态线程由谁创建?
jvm

##什么是协程?fiber，如何实现?
协程是用户态的线程，不涉及在内核态创建进程.
每个协程都对应一个指令栈,协程切换对应指令栈的切换,协程执行对应指令栈的进出
优势：1：占有资源很少 OS : 线程,1M Fiber：4K 2：切换比较简单 3：启动很多个10W+
用户态切换资源时间消耗少,反而比申请多个内核态进程执行时间短

目前2020 3 22支持内置纤程的语言：Kotlin Scala Go Python(lib)... Java? （open jdk : loom）

##线程和协程选择?
纤程 vs 线程池：很短的计算任务，不需要和内核打交道，并发量高！

##孤儿进程、僵尸进程
父进程持有所有fork出的子进程的pcb
僵尸进程:子进程死了,父进程还持有子进程的pcb
孤儿进程:父进程死了，子进程没有父亲，会被挂在某个特定进程上

##进程调度
单核cpu，一个时刻只有一个进程在执行，通过时钟中断切换进程，当切换到进程调度程序时，由他执行调度操作
多核中，并没有单独使用一个内核给进程调度程序独占，还是通过时钟中断切换进程

进程调度策略
进程优先级级别
##中断
硬件中断，软中断(重点)
给操作系统内核信号

硬件中断的过程:

线程中断:线程设置标志位

###中断涉及对象
硬件(键盘)，中断电信号,键盘控制器,中断控制器，
cpu,中断向量表，操作系统,中断处理程序
中断处理程序是设备内核驱动程序的一部分，是普通的c程序

中断处理程序和特定中断关联，如设备产生不同中断，则该设备可以对应多个中断处理程序
网络设备的中断处理程序将硬件网络数据包拷贝到内存

中断号，中断处理逻辑

###中断信号为啥要发给中断控制器?可以直接发给cpu吗?

###cpu怎么知道中断信号要给谁处理?

###中断处理器做了什么事情?
###优先级高的中断信号会中断低级的吗?
###中断重入?

http://www.elecfans.com/emb/20190402899322.html

http://www.wowotech.net/irq_subsystem/interrupt_subsystem_architecture.html/comment-page-2

https://zhuanlan.zhihu.com/p/55561945

[中断原理](https://zhuanlan.zhihu.com/p/80903637)


###中断上半部分，下半部分

##系统调用
When a process is created and its virtual memory is divided into user-space and a kernel-space , where user space region contains data, code, stack, heap of the process & kernel-space contains things such as the page table for the process, kernel data structures and kernel code etc. To run kernel space code, control must shift to kernel mode(using 0x80 software interrupt for system calls) & kernel stack is basically shared among all processes currently executing in kernel space
http://blog.chinaunix.net/uid-10386087-id-2958669.html
[系统调用表](http://asm.sourceforge.net/syscall.html#2)

c代码
int ![0x80](/Users/chris/workspace/xsource/linux/src/main/java/os/images/软中断0x80.jpg),软件中断，系统调用编号通过寄存器eax传递， 而参数通过寄存器ebx、ecx、edx、esi和edi传递
c代码
int 0x80,软件中断

sysenter和sysexit

系统调用号,[系统调用静态表](/Library/Developer/CommandLineTools/SDKs/MacOSX11.1.sdk/usr/include/sys/syscall.h )

在应用程序借助于标准库切换到核心态后，内核面临的任务是查找与该系统调用匹配的处理程序 函数，并向该处理函数提供传递的参数。sys_call_table表中保存了一组指向处理程序例程的函数 13 指针，可用于查找处理程序(在所有平台上)。因为该表是用汇编语言指令在内核的数据段中产生的， 其内容因平台而不同。但原理总是同样的:内核根据系统调用编号找到表中适当的位置，由此获得指 向目标处理程序函数的指针。

因为核心态和用户态使用两个不同的栈，如第3章所述，系统调用参数不能像通常那 样在栈上传递。在两个栈之间的切换，或者由进入核心态时调用的体系结构相关的汇编语 言代码进行，或者在特权级别从用户态切换到核心态时由处理器自动进行。
##信号

![](.操作系统系统调用&中断_images/信号执行流程.png)

###系统调用vs中断vs信号
![系统调用](/Users/chris/workspace/xsource/linux/src/main/java/os/images/系统调用.jpg)
https://zhuanlan.zhihu.com/p/38138831
![软中断与系统调用](/Users/chris/workspace/xsource/linux/src/main/java/os/images/系统调用\&软中断.jpg)
![系统调用过程](/Users/chris/workspace/xsource/linux/src/main/java/os/images/系统调用过程.jpg)
![系统调用过程](/Users/chris/workspace/xsource/linux/src/main/java/os/images/系统调用过程_2.jpg)
[linux系统调用视频](https://www.bilibili.com/video/BV15E411G7iZ?from=search&seid=10887133014228049877)  
[硬中断和软中断区别](https://zhuanlan.zhihu.com/p/289410487)
[系统调用](https://zhuanlan.zhihu.com/p/126229110)
[栈段寄存器](https://blog.csdn.net/farmwang/article/details/52318185)
![系统调用参数检查](/Users/chris/workspace/xsource/linux/src/main/java/os/images/系统调用参数检查.jpg)
![系统调用上下文](/Users/chris/workspace/xsource/linux/src/main/java/os/images/系统调用上下文.jpg)
![系统调用案例](https://bilibili.com/video/BV1Ft411t7Bt?from=search&seid=8724465101070945811)
```
硬中断的处理流程如下：

1、外设 将中断请求发送给中断控制器；
2、中断控制器 根据中断优先级，有序地将中断传递给 CPU；
3、CPU 终止执行当前程序流，将 CPU 所有寄存器的数值保存到栈中；
4、CPU 根据中断向量，从中断向量表中查找中断处理程序的入口地址，执行中断处理程序；
5、CPU 恢复寄存器中的数值，返回原程序流停止位置继续执行。
```

```
软中断模拟了硬中断的处理过程：

1、无
2、无
3、CPU 终止执行当前程序流，将 CPU 所有寄存器的数值保存到栈中；
4、CPU 根据中断向量，从中断向量表中查找中断处理程序的入口地址，执行中断处理程序；
5、CPU 恢复寄存器中的数值，返回原程序流停止位置继续执行

```

### [内核态和用户态的区别](https://zhuanlan.zhihu.com/p/77234351)
```
其实进程在内核态和用户态各有一个堆栈。运行在用户空间时进程使用的是用户空间中的堆栈，
而运行在内核空间时，进程使用的是内核空间中的堆栈。所以说，Linux 中每个进程有两个栈，
分别用于用户态和内核态


```

##异常

##信号量和锁的区别

##linux操作系统知识图谱
[linux操作系统知识图谱](https://developer.aliyun.com/article/724622)

##内核上下文，中断上下文，进程上下文
