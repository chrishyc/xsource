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


###epoll
//TODO

[](https://cloud.tencent.com/developer/article/1401558)
##阻塞&非阻塞,同步&异步

##从网卡读数据过程涉及对象和过程?
