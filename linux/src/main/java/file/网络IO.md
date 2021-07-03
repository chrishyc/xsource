
[问题列表](https://segmentfault.com/a/1190000020610336)
```
请画出三次握手和四次挥手的示意图
为什么连接的时候是三次握手？
什么是半连接队列？
ISN(Initial Sequence Number)是固定的吗？
三次握手过程中可以携带数据吗？
如果第三次握手丢失了，客户端服务端会如何处理？
SYN攻击是什么？
挥手为什么需要四次？
四次挥手释放连接时，等待2MSL的意义?
```
##1.TCP可靠性,无差错如何保证?
##2.为啥要三次握手?
互相通知序列号
![](https://i.loli.net/2019/06/08/5cfb776a34e0a13949.png)

##3.序列号可以默认为1吗?可以不交换序列号吗?

### ISN（初始序列号）机制
连接断开重用，四元组复用，老连接的序列号可能和新连接序列号重复，如何避免此问题?
这个生成器会用一个32位长的时钟，差不多4µs 增长一次，因此 ISN 会在大约 4.55 小时循环一次
而一个段在网络中并不会比最大分段寿命（Maximum Segment Lifetime (MSL) ，默认使用2分钟）长，
MSL 比4.55小时要短，所以我们可以认为 ISN 会是唯一的。
[ISN机制](https://www.zhihu.com/question/24853633/answer/573627478)

###旧链路包问题

##状态变化

syn_send,syn_rev,esbelsi,esbell
connect

bind
listen
accept
##数据丢失,超时重传

##tcp等待队列，完成队列,最大队列数

###socket等待队列，完成队列压测
ubuntu docker,
压测工具:wrk
socket队列查看工具:ss [ss](https://commandnotfound.cn/linux/1/232/ss-%E5%91%BD%E4%BB%A4)

wrk -t12 -c400 -d30s http://127.0.0.1:8080/index.html

查看tcp队列：ss -lnt

查看tcp队列：watch -n 0.1 'netstat -antp| grep 9090'

查看队列溢出:netstat -s | egrep "listen|LISTEN"
查看等待队列:netstat -natp | grep SYN_RECV | wc -l

/proc/sys/net/core/somaxconn
/proc/sys/net/ipv4/tcp_max_syn_backlog

backlog是listen端口的全队列大小

root用户 优先级高，队列数不受限制


tomcat ServerProperties.acceptcount

[参考](https://juejin.cn/post/6847902222425161735)
[](https://blog.csdn.net/weixin_39634438/article/details/112670713)

##数据报文分片

##tcp窗口大小

##响应报文类型
##RST的应用
[](https://www.zhihu.com/question/24853633/answer/573627478)

##PUSH
S=SYN 发起连接标志
P=PUSH 传送数据标志
F=FIN 关闭连接标志
R=RESET 异常关闭连接，链接重置
##连接keepalive

##长链接
[长链接](https://segmentfault.com/a/1190000021696056)

##安全问题
[SYN攻击](https://rgb-24bit.github.io/blog/2019/tcp-connect-manage.html)
tcp等待队列有限
##三次握手过程中可以携带数据吗？
第一次握手不可以放数据，其中一个简单的原因就是会让服务器更加容易受到攻击了
[](https://segmentfault.com/a/1190000020610336)



##tcp全双工
客户端收发

服务端收发
##四次挥手做了什么?
![](https://i.loli.net/2019/06/08/5cfb6c1233d4487468.png)

##为啥要四次挥手?
[](https://www.jianshu.com/p/3fac2f449789)
TCP协议是一种面向连接的、可靠的、基于字节流的运输层通信协议。TCP是全双工模式，
这就意味着，当主机1发出FIN报文段时，只是表示主机1已经没有数据要发送了，主机1告诉主机2，
它的数据已经全部发送完毕了；但是，这个时候主机1还是可以接受来自主机2的数据；
当主机2返回ACK报文段时，表示它已经知道主机1没有数据发送了，
但是主机2还是可以发送数据到主机1的；当主机2也发送了FIN报文段时，
这个时候就表示主机2也没有数据要发送了，就会告诉主机1，我也没有数据要发送了，之后彼此就会愉快的中断这次TCP连接。
[四次挥手](https://rgb-24bit.github.io/blog/2019/tcp-connect-manage.html)
```
四次挥手的过程其实就是关闭连接的过程
关闭连接的过程中，主动关闭者和被动关闭者需要停止各自的 发送 和 接收 操作
任何一端只能主动关闭自身的 发送 操作
任何一端只能在确定对方已经停止 发送 操作以后才能停止相应的 接收 操作
```

##四次挥手的状态
CLOSE,FIN_WAIT1
CLOSE_WAIT,
FIN_WAIT2
LAST_ACK
TIME_WAIT
CLOSED
##TIME_WAIT

```
TIME_WAIT 这个状态也是比较常见的一个问题了，第四次挥手后进行第四次挥手的一方会进入 TIME_WAIT 状态，要至少等待 2MSL 才关闭连接。

这是为了避免另一端没有收到自己的 ACK 又进行了 FIN 的重发，如果自己直接就把连接关了，那么就收不到这个 FIN 数据报了。这样一来，另一端就会长时间处在 LAST_ACK 的状态。

虽然 TIME_WAIT 这个状态是出于好意，但有些时候还是为造成一些问题，特别是在 Web 服务器这种需要主动关闭连接的服务端。

2MSL 的时间长度默认情况下并不短，通常情况下可能有 30~300 秒，这意味着在这个时间段类相应的 端口 资源是一直被占据的，这对相当依赖有限的端口资源的服务器来说是难以接受的。

因此，可以考虑通过将 2MSL 调低来解决这样问题。
```

###Address already in use问题
[](https://www.jianshu.com/p/711be2f1ec6a)
使用SO_REUSEADDR，可以在time_wait期间复用

###2MSL消耗的资源
消耗本机四元组
##三次握手四次挥手实践
[](https://www.jianshu.com/p/a4beee06220c)

##tcp参数实战
[](https://www.cnblogs.com/embedded-linux/p/9534205.html)
##client超时重试
[](https://cloud.tencent.com/developer/article/1574588)
```
Java_java_net_PlainSocketImpl_socketConnect(...){

    if (timeout <= 0) {
    	 ......
        connect_rv = NET_Connect(fd, (struct sockaddr *)&him, len);
    	 .....
    }else{
    	 // 如果timeout > 0 ，则设置为nonblock模式
        SET_NONBLOCKING(fd);
        /* no need to use NET_Connect as non-blocking */
        connect_rv = connect(fd, (struct sockaddr *)&him, len);
        /*
         * 这边用系统调用select来模拟阻塞调用超时
         */
        while (1) {
            ......
            struct timeval t;
            t.tv_sec = timeout / 1000;
            t.tv_usec = (timeout % 1000) * 1000;
            connect_rv = NET_Select(fd+1, 0, &wr, &ex, &t);
            ......
        }
        ......
        // 重新设置为阻塞模式
        SET_BLOCKING(fd);
        ......
    }
}
```

## socket通信
![](/Users/chris/workspace/xsource/linux/src/main/java/file/images/socket通信模型.jpg)
![](https://img-blog.csdnimg.cn/img_convert/1079c3922c9b2d1031514aee55b91b39.png)


##查看系统调用
strace -ff -o out cmd

##为啥线程accept不与read在同一个线程,而要创造子线程?
accept创造socket fd,read读 socket fd
在同一个线程处理,可能导致socket连接处理来不及,内核缓存溢出

##阻塞&同步
阻塞,non-blocking:
[](https://www.remlab.net/op/nonblock.shtml)


同步:
read write

##tcp keepalive

####为什么应用层需要heart beat/
[](https://blog.csdn.net/lanyang123456/article/details/90578453)

##tcp包大小
tcp窗口大小，序列号,tcp包大小mtu 1500，mss
缓存阻塞，窗口数据丢弃
##
nodelay
##
oobinline


##查看进程资源限制
ulimit -a

cat /proc/sys/fs/file-max
##安全问题ddos攻击
1.攻击方式
tcp syn阶段
带宽
web请求
2.解决方案
防火墙
nginx
增加带宽
[](https://www.bilibili.com/video/BV14W411d7Cp?from=search&seid=483521343909660865)

##网卡读写流程
![对象模型](https://blog.csdn.net/zhanglh046/article/details/115474548)
![实现模型](https://www.cnblogs.com/gogly/archive/2012/06/10/2541573.html)
###写
![](https://img-blog.csdnimg.cn/20210407000225556.png)
第一：用户程序调用套接字API，比如write或者send系统调用
第二：CPU进入内核态，此时会将数据拷贝到套接字缓冲区
第三：协议栈处理套接字缓冲区的数据，但并不是立即发送
不立即发送的原因就是，每次写的数据大小是由应用程序决定，如果每次要发送的数据太少，就会发送大量的小数据包，不立即发送的原因就是，每次写的数据大小是由应用程序决定，如果每次要发送的数据太少，就会发送大量的小数据包，
导致网路效率下降，所以需要积累到一定数量再发送出去。一般来说是根据MTU来决定，以太网中MTU一般是1500字节，如果(数据包大小+40(数据帧的首部长度)) > MTU,则会分片，然后满了再发送出去；到那时如果没有满，难道一直等到数据包满了才可以发送吗，协议栈内部有一个计时器，到期了也会将数据发送出去
第四：需要发送数据包的时候，协议栈会产生一个软中断，触发CPU中断处理程序，告诉网卡驱动程序有新的网络包需要发送
第五：网卡驱动程序会从套接字缓冲区读取要发送的数据包，通过DMA拷贝到网络接口控制器(网卡)的数据缓冲区
第六：等待网卡的数据缓冲区写满了之后，就会把数据发送出去

###读
![](https://img-blog.csdnimg.cn/20210407000238532.png)
第一: 网卡收到网络数据包，放入数据缓冲区
第二: 数据缓冲区满了，则通知DMA引擎
第三: DMA引擎向CPU申请总线和主存的使用权，将缓冲区数据拷贝到主存的套接字缓冲区
第四: 当拷贝完成后，操作系统怎么知道有数据包到来？所以需要一种机制通知操作系统。最简单方式就是触发中断，但是如果数据量很大，可能就会频繁触发中断，导致CPU没有时间执行其他的程序，从而影响系统性能。Linux 2.6引入了New API机制，综合中断和轮询的方式来接收网络数据包：第一次通过中断触发中断服务处理程序，然后唤醒软中断采用轮询方式来轮询数据，直到没有数据时才恢复中断，这样一次中断可以处理多个数据包，降低网卡中断带来的性能消耗。

第五：网络控制器(网卡)的中断机构向CPU发出中断请求，CPU会执行内核中网卡驱动程序的处理逻辑：
1 禁用网卡中断：避免后续数据处理频繁的向CPU发起中断
2 打开软中断：有可能后续的中断服务处理程序执行时间长，会影响CPU执行其他的进程或者中断请求，所以使用软中断，主要通过轮询的方式获取数据并处理数据
第六：软中断交给协议栈进行处理，协议栈对数据包进行拆包，并且将等待队列中的处于等待的线程加入到就绪队列，等待被CPU再次调度
1 链路层识别上册协议（IPV4 or IPV6）,去掉帧头和帧尾，然后交给网络层
2 网络层取出IP头，判断网络包目标主机，如果不是当前主机，则转发；如果是去掉IP头，然后交给传输层
3 取出TCP或者UDP头后，根据<源IP、源端口、目标IP、目标端口>四元组作为标识，找到对应的socket

###socket缓冲区
![](https://imgconvert.csdnimg.cn/aHR0cDovL2MuYmlhbmNoZW5nLm5ldC9jcHAvdXBsb2Fkcy9hbGxpbWcvMTUxMDE4LzEtMTUxMDFRNjBHVjI3LmpwZw?x-oss-process=image/format,png)
[](https://blog.csdn.net/daaikuaichuan/article/details/83061726)


###缓冲区实现


##缓冲vs队列vs缓存
缓冲:字符设备的读写,网卡进行socket通信,避免多次进行系统调用，缓存到一定程度然后进行发送出去，mysql 日志读写，mq日志读写
缓存:块设备的读写，如磁盘读写，内存、缓存读写，提高访问速度，操作系统使用页缓存管理磁盘的数据,常见的文件读写都使用了页缓存
cpu缓存,L1/L2/L3缓存,访问提速


