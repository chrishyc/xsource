package file.io.nio.rpc.refactor.rpcenv.connectpool;

import file.io.nio.rpc.refactor.discover.MyDiscover;
import file.io.nio.rpc.refactor.packmode.MsgCallBackMapping;
import file.io.nio.rpc.refactor.rpcenv.DecodeHandler;
import file.io.nio.rpc.refactor.rpcenv.PackageMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 */ //源于 spark 源码
public class ClientFactory{

    int poolSize = 100;
    NioEventLoopGroup clientWorker;
    Random rand = new Random();
    private ClientFactory(){}
    private static final ClientFactory factory;
    static {
        factory = new ClientFactory();
    }
    public static ClientFactory getFactory(){
        return factory;
    }


    //一个consumer 可以连接很多的provider，每一个provider都有自己的pool  K,V

    ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();

    public static CompletableFuture sendMsg(PackageMsg packageMsg , Class interfaceInfo) {
        ClientFactory factory =  ClientFactory.getFactory();
        NioSocketChannel clientChannel = factory.getClient(MyDiscover.discover(interfaceInfo));
        CompletableFuture cf = new CompletableFuture();
        MsgCallBackMapping.addCallBack(packageMsg.getMessageID(), cf);
        clientChannel.writeAndFlush(packageMsg.getByteBuf());
        return cf;
    }

    public synchronized NioSocketChannel getClient(InetSocketAddress address){

         ClientPool clientPool = outboxs.get(address);
        if(clientPool ==  null){
            outboxs.putIfAbsent(address,new ClientPool(poolSize));
            clientPool =  outboxs.get(address);
        }

        int i = rand.nextInt(poolSize);

       if( clientPool.clients[i] != null && clientPool.clients[i].isActive()){
           return clientPool.clients[i];
       }

       synchronized (clientPool.lock[i]){
           return clientPool.clients[i] = create(address);
       }

    }

    private NioSocketChannel create(InetSocketAddress address){

        //基于 netty 的客户端创建方式
        clientWorker = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new DecodeHandler());
                        p.addLast(new ClientResponsesHandler());  //解决给谁的？？  requestID..
                    }
                }).connect(address);
        try {
            NioSocketChannel client = (NioSocketChannel)connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;


    }


}
