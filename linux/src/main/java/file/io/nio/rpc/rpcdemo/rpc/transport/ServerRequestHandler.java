package file.io.nio.rpc.rpcdemo.rpc.transport;

import file.io.nio.rpc.rpcdemo.rpc.Dispatcher;
import file.io.nio.rpc.rpcdemo.rpc.protocol.MyContent;
import file.io.nio.rpc.rpcdemo.rpc.protocol.Myheader;
import file.io.nio.rpc.rpcdemo.util.Packmsg;
import file.io.nio.rpc.rpcdemo.util.SerDerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 */
public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    Dispatcher dis;

    public ServerRequestHandler(Dispatcher dis) {
        this.dis=dis;
    }

    //provider:
    //思考下解决方法？
    //
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Packmsg requestPkg = (Packmsg) msg;

        ctx.executor().execute(new Runnable() {
//        ctx.executor().parent().next().execute(new Runnable() {

            @Override
            public void run() {

                String serviceName = requestPkg.getContent().getName();
                String method = requestPkg.getContent().getMethodName();
                Object c = dis.get(serviceName);
                Class<?> clazz = c.getClass();
                Object res = null;
                try {


                    Method m = clazz.getMethod(method, requestPkg.getContent().getParameterTypes());
                    res = m.invoke(c, requestPkg.getContent().getArgs());


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


//                String execThreadName = Thread.currentThread().getName();
                MyContent content = new MyContent();
//                String s = "io thread: " + ioThreadName + " exec thread: " + execThreadName + " from args:" + requestPkg.content.getArgs()[0];
                content.setRes(res);
                byte[] contentByte = SerDerUtil.ser(content);

                Myheader resHeader = new Myheader();
                resHeader.setRequestID(requestPkg.getHeader().getRequestID());
                resHeader.setFlag(0x14141424);
                resHeader.setDataLen(contentByte.length);
                byte[] headerByte = SerDerUtil.ser(resHeader);
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);

                byteBuf.writeBytes(headerByte);
                byteBuf.writeBytes(contentByte);
                ctx.writeAndFlush(byteBuf);
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client close");
    }
}

