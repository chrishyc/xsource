package netty.demo.provider.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.consumer.RpcRequest;
import netty.demo.provider.service.UserServiceImpl;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class UserServerHandler extends ChannelInboundHandlerAdapter {
    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        // 判断是否符合约定，符合则调用本地方法，返回数据
        // msg:  UserService#sayHello#are you ok?
        if (msg instanceof RpcRequest) {
            RpcRequest rpcRequest = (RpcRequest) msg;
            String className = rpcRequest.getClassName();
            Assert.notNull(className, "not null");
            Class clazz = UserServiceImpl.class;
            Assert.notNull(clazz);
            //TODO 需要获取对应的实现类
            Object o = clazz.newInstance();
            Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            Object ret = method.invoke(o, rpcRequest.getParameters());
            ctx.writeAndFlush(ret);
        }
        
        
    }
}
