package netty.demo.provider;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import netty.demo.consumer.RpcRequest;
import netty.demo.consumer.Serializer;

import java.nio.charset.Charset;
import java.util.List;


public class RpcDecoder extends MessageToMessageDecoder<ByteBuf> {
    private Class<?> clazz;
    
    private Serializer serializer;
    
    
    public RpcDecoder(Class<?> clazz, Serializer serializer) {
        
        this.clazz = clazz;
        
        this.serializer = serializer;
        
    }
    
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int len = msg.readInt();
        RpcRequest rpcRequest = serializer.deserialize(RpcRequest.class, msg.toString(Charset.defaultCharset()).getBytes());
        out.add(rpcRequest);
    }
}
