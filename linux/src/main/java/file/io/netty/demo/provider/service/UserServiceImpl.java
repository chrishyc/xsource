package file.io.netty.demo.provider.service;

import file.io.netty.UserService;
import file.io.netty.demo.consumer.JSONSerializer;
import file.io.netty.demo.consumer.RpcRequest;
import file.io.netty.demo.provider.RpcDecoder;
import file.io.netty.demo.provider.handler.UserServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class UserServiceImpl implements UserService {
    
    @Override
    public String sayHello(String word) {
        System.out.println("调用成功--参数 " + word);
        return "i like this song, " + word;
    }
    
    //hostName:ip地址  port:端口号
    public static void startServer(String hostName, int port) throws InterruptedException {
        
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new RpcDecoder(RpcRequest.class,new JSONSerializer()));
                        pipeline.addLast(new UserServerHandler());
                        
                    }
                });
        serverBootstrap.bind(hostName, port).sync();
        
        
    }
    
}
