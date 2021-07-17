package file.io.netty.demo.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {
    
    //创建线程池对象
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    private static UserClientHandler userClientHandler;
    
    //1.创建一个代理对象 providerName：UserService#sayHello are you ok?
    public Object createProxy(final Class<?> serviceClass, final String providerName) {
        //借助JDK动态代理生成代理对象
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //（1）调用初始化netty客户端的方法
                
                if (userClientHandler == null) {
                    initClient();
                }
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setRequestId("1");
                rpcRequest.setClassName(method.getDeclaringClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                // 设置参数
                userClientHandler.setPara(rpcRequest);
                
                // 去服务端请求数据
                
                return executor.submit(userClientHandler).get();
            }
        });
        
        
    }
    
    /**
     * {@link ChannelPipeline}介绍channelHandler执行顺序
     *
     *  I/O Request
     *  *                                            via {@link Channel} or
     *  *                                        {@link ChannelHandlerContext}
     *  *                                                      |
     *  *  +---------------------------------------------------+---------------+
     *  *  |                           ChannelPipeline         |               |
     *  *  |                                                  \|/              |
     *  *  |    +---------------------+            +-----------+----------+    |
     *  *  |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |              /|\                                  |               |
     *  *  |               |                                  \|/              |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |              /|\                                  .               |
     *  *  |               .                                   .               |
     *  *  | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
     *  *  |        [ method call]                       [method call]         |
     *  *  |               .                                   .               |
     *  *  |               .                                  \|/              |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |              /|\                                  |               |
     *  *  |               |                                  \|/              |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
     *  *  |    +----------+----------+            +-----------+----------+    |
     *  *  |              /|\                                  |               |
     *  *  +---------------+-----------------------------------+---------------+
     *  *                  |                                  \|/
     *  *  +---------------+-----------------------------------+---------------+
     *  *  |               |                                   |               |
     *  *  |       [ Socket.read() ]                    [ Socket.write() ]     |
     *  *  |                                                                   |
     *  *  |  Netty Internal I/O Threads (Transport Implementation)            |
     *  *  +-------------------------------------------------------------------+
     *
     * For example, let us assume that we created the following pipeline:
     *  * <pre>
     *  * {@link ChannelPipeline} p = ...;
     *  * p.addLast("1", new InboundHandlerA());
     *  * p.addLast("2", new InboundHandlerB());
     *  * p.addLast("3", new OutboundHandlerA());
     *  * p.addLast("4", new OutboundHandlerB());
     *  * p.addLast("5", new InboundOutboundHandlerX());
     *  * </pre>
     *  * In the example above, the class whose name starts with {@code Inbound} means it is an inbound handler.
     *  * The class whose name starts with {@code Outbound} means it is a outbound handler.
     *  * <p>
     *  * In the given example configuration, the handler evaluation order is 1, 2, 3, 4, 5 when an event goes inbound.
     *  * When an event goes outbound, the order is 5, 4, 3, 2, 1.  On top of this principle, {@link ChannelPipeline} skips
     *  * the evaluation of certain handlers to shorten the stack depth:
     *  * <ul>
     *  * <li>3 and 4 don't implement {@link ChannelInboundHandler}, and therefore the actual evaluation order of an inbound
     *  *     event will be: 1, 2, and 5.</li>
     *  * <li>1 and 2 don't implement {@link ChannelOutboundHandler}, and therefore the actual evaluation order of a
     *  *     outbound event will be: 5, 4, and 3.</li>
     *  * <li>If 5 implements both {@link ChannelInboundHandler} and {@link ChannelOutboundHandler}, the evaluation order of
     *  *     an inbound and a outbound event could be 125 and 543 respectively.</li>
     *
     *  1.怎么区分Inbound,Outbound?
     *  {@link AbstractChannelHandlerContext#findContextInbound()}
     *  {@link AbstractChannelHandlerContext#findContextOutbound()}
     *
     *  2.channelHandler执行顺序?
     *  读:inbound1,inbound2,...
     *  写:outbound n,outbound n-1,...
     *
     *  3.首位处理策略?
     *  {@link DefaultChannelPipeline.HeadContext}
     *  {@link DefaultChannelPipeline.TailContext}
     * @throws InterruptedException
     */
    //2.初始化netty客户端
    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();
        
        EventLoopGroup group = new NioEventLoopGroup();
        
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(userClientHandler);
                    }
                });
        
        bootstrap.connect("127.0.0.1", 8990).sync();
        
    }
    
    
}
