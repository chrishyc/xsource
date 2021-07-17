/**
 * sharemer.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package file.io.nio.nio.reactor.simple;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author sunqinwen
 * @version \: Acceptor.java,v 0.1 2019-03-28 13:52
 */
public class Acceptor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        try {
            socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println(String.format("收到来自 %s 的连接",
                        socketChannel.getRemoteAddress()));
                new AsyncHandler(socketChannel, selector); //这里把客户端通道传给Handler，Handler负责接下来的事件处理（除了连接事件以外的事件均可）
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
