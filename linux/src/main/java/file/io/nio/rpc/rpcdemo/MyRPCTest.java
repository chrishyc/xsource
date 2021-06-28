package file.io.nio.rpc.rpcdemo;

import file.io.nio.rpc.rpcdemo.proxy.MyProxy;
import file.io.nio.rpc.rpcdemo.rpc.Dispatcher;
import file.io.nio.rpc.rpcdemo.rpc.protocol.MyContent;
import file.io.nio.rpc.rpcdemo.rpc.transport.MyHttpRpcHandler;
import file.io.nio.rpc.rpcdemo.service.*;
import file.io.nio.rpc.rpcdemo.util.SerDerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 马士兵教育
 * @create: 2020-07-12 20:08
 * <p>
 * 12号的课开始手写RPC ，把前边的IO的课程都看看
 * http://mashibing.com/vip.html#%E5%91%A8%E8%80%81%E5%B8%88%E5%86%85%E5%AD%98%E4%B8%8Eio%E7%A3%81%E7%9B%98io%E7%BD%91%E7%BB%9Cio
 */

/*
    1，先假设一个需求，写一个RPC
    2，来回通信，连接数量，拆包？
    3，动态代理呀，序列化，协议封装
    4，连接池
    5，就像调用本地方法一样去调用远程的方法，面向java中就是所谓的 面向interface开发
 */


/**
 * 上节课，基本写了一个能发送
 * 小问题，当并发通过一个连接发送后，服务端解析bytebuf 转 对象的过程出错
 */
public class MyRPCTest {

    //多多包涵，如果一会翻车，请不要打脸。。。。。


    @Test
    public void startServer() {

        MyCar car = new MyCar();
        MyFly fly = new MyFly();

        Dispatcher dis = Dispatcher.getDis();

        dis.register(Car.class.getName(), car);
        dis.register(Fly.class.getName(), fly);

        NioEventLoopGroup boss = new NioEventLoopGroup(20);
        NioEventLoopGroup worker = boss;

        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept cliet port: " + ch.remoteAddress().getPort());
                        ChannelPipeline p = ch.pipeline();

//                        //1，自定义的rpc
//                        p.addLast(new ServerDecode());
//                        p.addLast(new ServerRequestHandler(dis));
                        //在自己定义协议的时候你关注过哪些问题：粘包拆包的问题，header+body

                        //2，小火车，传输协议用的就是http了  <- 你可以自己学，字节节码byte[]
                        //其实netty提供了一套编解码
                        p.addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(1024*512))
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        //http 协议 ,  这个msg是一个啥：完整的http-request
                                        FullHttpRequest request = (FullHttpRequest) msg;
                                        System.out.println(request.toString());  //因为现在sonsumer使用的是一个现成的URL


                                        //这个就是consumer 序列化的MyContent
                                        ByteBuf content = request.content();
                                        byte[]  data = new byte[content.readableBytes()];
                                        content.readBytes(data);
                                        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
                                        MyContent myContent = (MyContent)oin.readObject();

                                        String serviceName = myContent.getName();
                                        String method = myContent.getMethodName();
                                        Object c = dis.get(serviceName);
                                        Class<?> clazz = c.getClass();
                                        Object res = null;
                                        try {


                                            Method m = clazz.getMethod(method, myContent.getParameterTypes());
                                            res = m.invoke(c, myContent.getArgs());


                                        } catch (NoSuchMethodException e) {
                                            e.printStackTrace();
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }


                                        MyContent resContent = new MyContent();
                                        resContent.setRes(res);
                                        byte[] contentByte = SerDerUtil.ser(resContent);

                                        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                                                HttpResponseStatus.OK,
                                                Unpooled.copiedBuffer(contentByte));

                                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,contentByte.length);

                                        //http协议，header+body
                                        ctx.writeAndFlush(response);


                                    }
                                });


                    }
                }).bind(new InetSocketAddress("localhost", 9090));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void startHttpServer(){
        MyCar car = new MyCar();
        MyFly fly = new MyFly();

        Dispatcher dis = Dispatcher.getDis();

        dis.register(Car.class.getName(), car);
        dis.register(Fly.class.getName(), fly);


        //tomcat jetty  【servlet】
        Server server = new Server(new InetSocketAddress("localhost", 9090));
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        server.setHandler(handler);
        handler.addServlet(MyHttpRpcHandler.class,"/*");  //web.xml

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //模拟comsumer端 && provider
    @Test
    public void get() {
//        new Thread(()->{
//            startServer();
//        }).start();
//
//        System.out.println("server started......");

        AtomicInteger num = new AtomicInteger(0);
        int size = 50;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                Car car = MyProxy.proxyGet(Car.class);//动态代理实现   //是真的要去触发 RPC调用吗？
                String arg = "hello" + num.incrementAndGet();
                String res = car.ooxx(arg);
                System.out.println("client over msg: " + res + " src arg: " + arg);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testRPC() {

        Car car = MyProxy.proxyGet(Car.class);
        Persion zhangsan = car.oxox("zhangsan", 16);
        System.out.println(zhangsan);
    }








    @Test
    public void testRpcLocal() {
        new Thread(() -> {
            startServer();
        }).start();

        System.out.println("server started......");

        Car car = MyProxy.proxyGet(Car.class);
        Persion zhangsan = car.oxox("zhangsan", 16);
        System.out.println(zhangsan);
    }



}
