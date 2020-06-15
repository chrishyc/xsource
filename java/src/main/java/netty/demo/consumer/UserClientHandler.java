package netty.demo.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    
    private ChannelHandlerContext context;
    private String result;
    private RpcRequest rpcRequest;
    
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        context = ctx;
    }
    
    /**
     * 收到服务端数据，唤醒等待线程
     */
    
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) {
        result = msg.toString();
        notify();
    }
    
    /**
     * 写出数据，开始等待唤醒
     */
    
    @Override
    public synchronized Object call() throws InterruptedException {
        context.writeAndFlush(rpcRequest);
        wait();
        return result;
    }
    
    /*
     设置参数
     */
    
    void setPara(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }
    
    
}
