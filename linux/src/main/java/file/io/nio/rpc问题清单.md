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
