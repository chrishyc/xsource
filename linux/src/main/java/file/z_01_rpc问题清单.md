rpc实现模型
远程通信:通信协议,编码/解码方式,注册中心,客户端服务端,客户端代理类，对象很大时非完整写实现
负载均衡:连接池概念,整体学习
保持连接:心跳包

代理生成原理,bytebubby,asm,

##对象持久化原理objectoutputstream,io流体系和设计模式
序列化对象过程,rpc对象序列化


协议:header,msgbody

请求，返回的处理过程?

rpc过程中的线程关系?rpc阻塞实现CompletableFuture

服务端线程池优化

channel readbyte,setbyte区别

粘包拆包完整性不能保证


##dubbo使用的业务处理线程是io线程吗?
CompletableFuture
FutureTask
##rpc调用实现
![](/Users/chris/workspace/xsource/linux/src/main/java/file/images/rpc_invoke_process.png)
##service mesh(服务网)
[](https://www.cnblogs.com/tianyamoon/p/10106587.html)
![](https://img2018.cnblogs.com/blog/21899/201812/21899-20181212092501971-882456460.png)
##rpc请求接收模型
请求:异步,串行
异步发起请求,串行传输数据

request,response：异步请求，返回时不一定时按顺序返回，需要状态位标记mapping
此方式是有状态通信
![](/Users/chris/workspace/xsource/linux/src/main/java/file/images/netty_rpc_modle.jpg)
##rpc模型
netty
redis
tomcat

###netty模型涉及的对象
每个对象可用的技术类型，最终选择，why?

客户端:
代理(javaassist,cgl)
协议(rpc,http,协议头部，协议体，序列化)
请求响应有状态链路(异步,串行,连接池mapping requestId)

注册中心：

服务端:

io模型:
eventloop组，boss,work,
select,channel,channelpipline
###netty io 层和netty业务层的关系

#RPC框架
RMI
Dubbo
gRPC
Thrift
#序列化协议
