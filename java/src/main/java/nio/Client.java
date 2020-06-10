package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    public static void main(String[] args) throws IOException {
        new Client().start();
    }
    
    private void start() throws IOException {
        // 创建一个客户端SocketChannel对象
        SocketChannel channel = SocketChannel.open();
        // 设置客户端Channel为非阻塞模式
        channel.configureBlocking(false);
        
        // 创建一个供给客户端使用的Selector对象
        Selector selector = Selector.open();
        // 注册客户端Channel到Selector中，这里客户端Channel首先监听的是OP_CONNECT事件，
        // 因为其首先必须与服务器建立连接，然后才能发送和读取数据
        channel.register(selector, SelectionKey.OP_CONNECT);
        // 调用客户端Channel.connect()方法连接服务器，需要注意的是，该方法的调用必须放在
        // 上述Channel.register()方法之后，否则在注册之前客户端就已经注册完成，
        // 此时Selector就无法收到SelectionKey.OP_CONNECT事件了
        channel.connect(new InetSocketAddress("127.0.0.1", 7979));
        while (true) {
            // 监听客户端Channel的事件，这里会一直等待，直到有监听的事件到达。
            // 对于客户端，首先监听到的应该是SelectionKey.OP_CONNECT事件，
            // 然后在后续代码中才会将SelectionKey.OP_READ和WRITE事件注册
            // 到Selector中
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                
                // 监听到客户端Channel的SelectionKey.OP_CONNECT事件，并且处理该事件
                if (key.isConnectable()) {
                    connect(key, selector);
                }
                
                // 监听到客户端Channel的SelectionKey.OP_WRITE事件，并且处理该事件
                if (key.isWritable()) {
                    write(key, selector);
                }
                
                // 监听到客户端Channel的SelectionKey.OP_READ事件，并且处理该事件
                if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }
    
    private void connect(SelectionKey key, Selector selector) throws IOException {
        // 由于是客户端Channel，因而可以直接强转为SocketChannel对象
        SocketChannel channel = (SocketChannel) key.channel();
        channel.finishConnect();
        // 连接建立完成后就监听该Channel的WRITE事件，以供客户端写入数据发送到服务器
        channel.register(selector, SelectionKey.OP_WRITE);
    }
    
    private void write(SelectionKey key, Selector selector) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String message = "message from client";
        System.out.println("**********client: write message**********");
        System.out.println(message);
        // 客户端写入数据到服务器Channel中
        channel.write(ByteBuffer.wrap(message.getBytes()));
        // 数据写入完成后，客户端Channel监听OP_READ事件，以等待服务器发送数据过来
        channel.register(selector, SelectionKey.OP_READ);
    }
    
    private void read(SelectionKey key) throws IOException {
        System.out.println("**********client: read message**********");
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[1024]);
        // 接收到客户端Channel的SelectionKey.OP_READ事件，说明服务器发送数据过来了，
        // 此时可以从Channel中读取数据，并且进行相应的处理
        int len = channel.read(buffer);
        if (len == -1) {
            channel.close();
            return;
        }
        
        System.out.println(new String(buffer.array(), 0, len));
    }
}
