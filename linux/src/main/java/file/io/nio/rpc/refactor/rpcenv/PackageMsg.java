package file.io.nio.rpc.refactor.rpcenv;

import file.io.nio.rpc.refactor.packmode.Myheader;
import file.io.nio.rpc.refactor.proxymode.rpcprotocol.RpcContent;
import file.io.nio.rpc.refactor.util.SerDerTool;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.util.UUID;

/**
 */
public class PackageMsg {

    long messageID;
    byte[] headerByte;
    byte[] contentByte;

    Myheader header;
    RpcContent content;



    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public byte[] getHeaderByte() {
        return headerByte;
    }

    public void setHeaderByte(byte[] headerByte) {
        this.headerByte = headerByte;
    }

    public byte[] getContentByte() {
        return contentByte;
    }

    public void setContentByte(byte[] contentByte) {
        this.contentByte = contentByte;
    }

    public Myheader getHeader() {
        return header;
    }

    public void setHeader(Myheader header) {
        this.header = header;
    }

    public RpcContent getContent() {
        return content;
    }

    public void setContent(RpcContent content) {
        this.content = content;
    }

    PackageMsg(long requesID,Myheader header,RpcContent content){
        this.messageID = requesID;
        this.header = header;
        this.content = content;
    }
    public void doByte(){
        this.contentByte = SerDerTool.objToByte(this.content);
        header.setDataLen(contentByte.length);
        this.headerByte = SerDerTool.objToByte(this.header);
    }
    public static PackageMsg requestHeader(RpcContent content){
        int f = 0x14141414;
        long messageID =  Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return createHeader(f,messageID,content);
    }
    public static PackageMsg responsHeader(long messageID,RpcContent content){
        int f = 0x14141424;
        return createHeader(f,messageID,content);
    }
    private static PackageMsg createHeader( int f,long messageID ,RpcContent content){
        Myheader header = new Myheader();
        header.setFlag(f);
        header.setRequestID(messageID);
        PackageMsg pack = new PackageMsg(messageID,header,content);
        pack.doByte();
        return pack;
    }
    public ByteBuf getByteBuf() {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(this.headerByte.length+this.contentByte.length);
        byteBuf.writeBytes(headerByte);
        byteBuf.writeBytes(contentByte);
        return byteBuf;
    }
}
