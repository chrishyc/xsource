package file.io.nio.rpc.refactor.rpcenv.serverenv;

import com.bjmashibing.system.lession.test.packmode.Myheader;
import com.bjmashibing.system.lession.test.proxymode.rpcprotocol.RpcContent;
import com.bjmashibing.system.lession.test.rpcenv.PackageMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 11:36
 */
class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    //provider:
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        PackageMsg pkg = (PackageMsg) msg;

        String IOReadThreadName = Thread.currentThread().getName();
        ctx.executor().parent().next().execute(                new Runnable() {
            @Override
            public void run() {
                String executeThreadName = Thread.currentThread().getName();
                String s = "io in :" + IOReadThreadName + " execute in :" + executeThreadName + " content.methodName :" + pkg.getContent().getMethodName() + " args :" + pkg.getContent().getArgs()[0];
                PackageMsg rpkg = PackageMsg.responsHeader(pkg.getMessageID(), new RpcContent(s));
                ctx.writeAndFlush(rpkg.getByteBuf());
            }
        });


    }

}
