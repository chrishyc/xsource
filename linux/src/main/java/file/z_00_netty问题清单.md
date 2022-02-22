channel

##对象模型
![](/Users/chris/workspace/xsource/linux/src/main/java/file/images/netty_object_model.jpg)

##实现模型
ChannelHandler，ChannelInboundHandlerAdapter，ChannelOutboundHandlerAdapter
方法对应的原型?

ChannelHandlerContext的作用?ChannelHandlerContext有几个?
每当有 ChannelHandler 添加到 ChannelPipeline 中时，都会创建 ChannelHandler- Context。ChannelHandlerContext 的主要功能是管理它所关联的 ChannelHandler 和在 同一个 ChannelPipeline 中的其他 ChannelHandler 之间的交互。

ChannelHandlerContext 有很多的方法，其中一些方法也存在于 Channel 和 Channel- Pipeline 本身上，但是有一点重要的不同。如果调用 Channel 或者 ChannelPipeline 上的这 些方法，它们将沿着整个 ChannelPipeline 进行传播。而调用位于 ChannelHandlerContext 上的相同方法，则将从当前所关联的 ChannelHandler 开始，并且只会传播给位于该 ChannelPipeline 中的下一个能够处理该事件的 ChannelHandler。


要想调用从某个特定的 ChannelHandler 开始的处理过程，必须获取到在(Channel-
Pipeline)该 ChannelHandler 之前的 ChannelHandler 所关联的 ChannelHandler- Context。这个 ChannelHandlerContext 将调用和它所关联的 ChannelHandler 之后的 ChannelHandler。


因为一个 ChannelHandler 可以从属于多个 ChannelPipeline，所以它也可以绑定到多 个 ChannelHandlerContext 实例。对于这种用法指在多个 ChannelPipeline 中共享同一 个 ChannelHandler，对应的 ChannelHandler 必须要使用@Sharable 注解标注;否则， 试图将它添加到多个 ChannelPipeline 时将会触发异常。显而易见，为了安全地被用于多个 并发的 Channel(即连接)，这样的 ChannelHandler 必须是线程安全的。

总之，只应该在确定了你的 ChannelHandler 是线程安全的时才使用@Sharable 注解。

为何要共享同一个ChannelHandler 在多个ChannelPipeline中安装同一个ChannelHandler
的一个常见的原因是用于收集跨越多个 Channel 的统计信息。


ChannelPipeline入栈出站的方向

如同在图 6-5 中所能够看到的一样，代码清单 6-6 和代码清单 6-7 中的事件流是一样的。重要的 是要注意到，虽然被调用的 Channel 或 ChannelPipeline 上的 write()方法将一直传播事件通 过整个 ChannelPipeline，但是在 ChannelHandler 的级别上，事件从一个 ChannelHandler 到下一个 ChannelHandler 的移动是由 ChannelHandlerContext 上的调用完成的。


编码器和解码器是ChannelHandler的实现



EventLoop

socket关联select和socket绑定port过程


buffer池化概念,buffer与jdk8的翻转改善,直接存储，堆存储

线程池执行循环任务会阻塞吗?

future实现原理,netty异步原理,channel共享

连接对象:thread,select,channel,socket,pipeline,handler,future,bytebuf,listener


接收事件，注册，读取,c中select,java 8 select,netty select三者间的关系


netty源码回顾,share模式,childHandler##channelRead##msg总结,sync,ChannelPipeline
##netty io和应用层关联
###serverbootstrap 可以bind端口号多个？bind后在哪些线程?
2个bootstrap对应一个boss组,work组
###多个bootstrap使用一个boss,work组会有问题吗?
###handler和socket的关系?


##netty面试题
[](https://xiaozhuanlan.com/topic/4028536971)
[](https://blog.csdn.net/weixin_39864373/article/details/110886072)
##那些开源项目用到了Netty?
dubbo,RocketMQ,Spring Cloud Gateway
##Netty是什么
1.基于nio
2.支持各种协议
3.简化网络编程编解码,粘包拆包等编码

##为啥不直接用NIO呢?
基于reactor模式,是基于事件驱动的设计模式，拥有一个或多个并发输入源，有一个服务处理器和多个请求处理器，服务处理器会同步的将输入的请求事件以多路复用的方式分发给相应的请求处理器
数据粘包拆包
##为啥使用netty?

##netty做什么

##netty线程模型



##netty长连接

##Netty零拷贝

##直接内存和堆内存区别

直接内存的读写操作比普通Buffer快，但它的创建、销毁比普通Buffer慢（猜测原因是DirectBuffer需向OS申请内存涉及到用户态内核态切换，而后者则直接从堆内存划内存即可）。

因此直接内存使用于需要大内存空间且频繁访问的场合，不适用于频繁申请释放内存的场合。
[](https://www.zhihu.com/question/60892134)

##netty+tomcat有状态通信
#tcp粘包拆包
[](https://juejin.cn/post/6844904197712789518#heading-0)
##拓扑
MSS(Maximum Segment Size,TCP最大报文长度,1460B+40B首部)
拆包,数据太大
粘包,数据太小
##方案
```asp
传输的消息是定长的。只有长度都知道了，接收方就可以每次获取指定长度就完事了
在数据包的尾部加上回车符作为分割条件(结束条件)
将消息分为消息头和消息体。消息头中包含了消息的总长度。(一般来说消息头的第一个字段可以被设计表示为消息总长度)
更加复杂的应用层协议
```
