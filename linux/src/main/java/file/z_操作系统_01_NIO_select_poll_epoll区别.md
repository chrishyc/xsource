##临界知识
一次系统调用批量发送fd状态
类型拆分,只批量发送活跃的fd
##NIO accept的原理?
创建一个fd与四元组对应起来
##NIO和select的区别?
accept:进程空间保存fd集合,对每个fd进行系统调用
select:进程空间保存fd,所有fd作为参数进行一次系统调用
##select和poll,epoll的区别?

###select最大描述符问题
```
typedef struct fd_set {
        u_int fd_count;               /* how many are SET? */
        SOCKET  fd_array[FD_SETSIZE];   /* an array of SOCKETs */
} fd_set; 
```
```
#define FD_SET(fd, set) do { \
    u_int __i; \
    for (__i = 0; __i < ((fd_set FAR *)(set))->fd_count; __i++) { \
        if (((fd_set FAR *)(set))->fd_array[__i] == (fd)) { \
            break; \
        } \
    } \
    if (__i == ((fd_set FAR *)(set))->fd_count) { \
        if (((fd_set FAR *)(set))->fd_count < FD_SETSIZE) { \
            ((fd_set FAR *)(set))->fd_array[__i] = (fd); \
            ((fd_set FAR *)(set))->fd_count++; \
        } \
    } \
} while(0) 

```
[](https://www.cnblogs.com/hnrainll/archive/2011/12/13/2285973.html)
[](https://blog.csdn.net/dog250/article/details/105896693)


###poll
红黑树文件描述符
对象映射
epoll的局限

```
struct pollfd   //这个结构体一般会被定义为结构体数组
{
   int fd；        //要监听的文件描述符
   short events；  //期待fd发生的事件   ，其中 events把他设置成 POLLIN就够用了，表示检测该fd有没有可读数据
   short revents； //fd实际发生的事件
}；

int poll(struct pollfd *name,int num,int timeout);
```
一共有三个，分别是监听的结构体数组的数组名，实际监听着多少个结构体数组成员，阻塞时间控制参数。
[](https://blog.csdn.net/u014453898/article/details/53992003)

```
openat(AT_FDCWD, "/proc/net/if_inet6", O_RDONLY) = 17
socket(AF_INET, SOCK_STREAM, IPPROTO_IP) = 16
setsockopt(16, SOL_SOCKET, SO_REUSEADDR, [1], 4) = 0
fcntl(16, F_GETFL)                      = 0x2 (flags O_RDWR)
fcntl(16, F_SETFL, O_RDWR|O_NONBLOCK)   = 0
bind(16, {sa_family=AF_INET, sin_port=htons(9090), sin_addr=inet_addr("0.0.0.0")}, 16) = 0
//The backlog argument defines the maximum  length  to  which  the queue  of pending connections for sockfd may grow
listen(16, 50)
poll([{fd=17, events=POLLIN}, {fd=16, events=POLLIN}], 2, -1) = 1 ([{fd=16, revents=POLLIN}])
```
###epoll

//TODO

[](https://cloud.tencent.com/developer/article/1401558)
##阻塞&非阻塞,同步&异步

普通文件读写不会阻塞

##从网卡读数据过程涉及对象和过程?


##NIO模型演变
###accept阻塞模型
方案:accept创建fd关联四元组+读写在同一个线程,accept阻塞，read阻塞
优点:由于是同步执行，每个连接执行完成关闭后才执行其他连接,因此无需集合保存连接
缺点:accept可能阻塞线程，read可能阻塞线程，互相干扰
实现:SocketBIO
###accept非阻塞模型
方案:accept创建fd关联四元组+读写在同一个线程,accept非阻塞，read非阻塞，
优点:accept非阻塞,read非阻塞不会相互干扰
缺点:连接可能未执行完就往下执行，需要使用集合记录连接,每次循环都要将所有连接集合执行一遍，且每个连接都需要进行一次系统调用
    连接过多后，大部分时间都消耗在系统调用上面
实现:SocketNIO

###多路复用模型select
select和accept的区别：select的入参是读fd，写fd,然后内核对入参的fd进行状态判断，
accept用于生成连接对应的fd
方案:select监听指定fd集合,并返回fd集合，应用层自己根据返回的fd决定具体的连接/读/写过程
优点:相对accept无需每个连接进行系统调用,一个系统调用查找所有fd状态
缺点:指定的所有fd都去查找状态,如果连接非常多,查找状态也非常耗时
实现:SocketMultiplexingSingleThreadv1
###多路复用模型poll
select数据结构和数组长度由内核定义，有连接最大数限制
poll数据结构是动态数组，内核不定义数组长度
###多路复用模型epoll
对指定的fd集合使用事件，监听到来的事件并以红黑树集合缓存
每次只返回有事件到来的集合
###多路复用模型单线程
读写会阻塞select
###多路复用模型read,write多线程，select单个单线程
读写开新线程,不阻塞select,但由于多线程io状态难以维护，需要通过cancel来维护状态
且cancel涉及系统调用,会导致性能降低
SocketMultiplexingSingleThreadv2

为啥需要取消注册epoll cancel,fd红黑树，fd内核?
多线程导致异步执行io,io读还未结束，又进行select轮训，由于fd状态仍是可读，又会创建线程
因此需要cancel

###多路复用模型select多个多线程,select每个内部的read,write是单线程阻塞
每个cpu开一个select线程，每个select线程负责fd的连接/读/写,相当于select线程内是单线程阻塞执行
不用维护多线程io状态，无cancel系统调用
SocketMultiplexingThreads

io通过selector线性处理，io读到的数据可以异步处理
![](/Users/chris/workspace/xsource/linux/src/main/java/file/images/netty.jpeg)
###


###acceptable, readable有东西可读,writeable有空间可写
[](https://zhuanlan.zhihu.com/p/340304658)
###io同步异步，处理过程同步异步

###数据结构
tcp待完成队列
tcp已完成队列
发送队列，接受队列[](https://blog.csdn.net/weixin_39934085/article/details/111205630)
accept建立fd和四元组
epoll红黑树记录fd
###accept select
fd与四元组，是在accept创建的fd吗?
###tcp syn队列数据结构
首先我们需要明白一点，accept()是从accept队列里面取出客户端的syn请求，然后完成三次握手，
并且socket server对于每个客户端都创建一个新的socketfd，然后通过这socketfd来跟client数据传输。 



