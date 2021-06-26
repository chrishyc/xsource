package file.io.nio.rpc.refactor.rpcenv;

import com.bjmashibing.system.lession.test.packmode.Myheader;
import com.bjmashibing.system.lession.test.proxymode.rpcprotocol.RpcContent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author: 马士兵教育
 * @create: 2020-07-19 02:36
 */
public class DecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        while (buf.readableBytes() >= 119) {
            byte[] bytes = new byte[119];
            buf.getBytes(buf.readerIndex(), bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            Myheader header = (Myheader) oin.readObject();
            if ((buf.readableBytes() - 119) >= header.getDataLen()) {
                buf.readBytes(119);
                byte[] data = new byte[header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                if(header.getFlag()==0x14141414){
                    RpcContent content = (RpcContent) doin.readObject();
                    out.add(new PackageMsg(header.getRequestID(),header,content));
                }else{
                    RpcContent content = (RpcContent) doin.readObject();

                    out.add(new PackageMsg(header.getRequestID(),header,content));
                }

            } else {
                break;
            }
        }
    }
}
