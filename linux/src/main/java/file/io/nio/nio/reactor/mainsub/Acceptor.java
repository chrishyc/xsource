/**
 * sharemer.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package file.io.nio.nio.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author sunqinwen
 * @version \: Acceptor.java,v 0.1 2019-03-28 13:52
 */
public class Acceptor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;

    private final int coreNum = Runtime.getRuntime().availableProcessors(); // 获取CPU核心数

    private final Selector[] selectors = new Selector[coreNum]; // 创建selector给SubReactor使用，个数为CPU核心数

    private int next = 0; // 轮询使用subReactor的下标索引

    private SubReactor[] reactors = new SubReactor[coreNum]; // subReactor

    private Thread[] threads = new Thread[coreNum]; // subReactor的处理线程

    Acceptor(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;
        // 初始化
        for (int i = 0; i < coreNum; i++) {
            selectors[i] = Selector.open();
            reactors[i] = new SubReactor(selectors[i], i); //初始化sub reactor
            threads[i] = new Thread(reactors[i]); //初始化运行sub reactor的线程
            threads[i].start(); //启动（启动后的执行参考SubReactor里的run方法）
        }
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        try {
            socketChannel = serverSocketChannel.accept(); // 连接
            if (socketChannel != null) {
                System.out.println(String.format("收到来自 %s 的连接",
                        socketChannel.getRemoteAddress()));
                socketChannel.configureBlocking(false); //
                reactors[next].registering(true); // 注意一个selector在select时是无法注册新事件的，因此这里要先暂停下select方法触发的程序段，下面的weakup和这里的setRestart都是做这个事情的，具体参考SubReactor里的run方法
                selectors[next].wakeup(); // 使一個阻塞住的selector操作立即返回
                SelectionKey selectionKey = socketChannel.register(selectors[next],
                        SelectionKey.OP_READ); // 当前客户端通道SocketChannel向selector[next]注册一个读事件，返回key
                selectors[next].wakeup(); // 使一個阻塞住的selector操作立即返回
                reactors[next].registering(false); // 本次事件注册完成后，需要再次触发select的执行，因此这里Restart要在设置回false（具体参考SubReactor里的run方法）
                selectionKey.attach(new AsyncHandler(socketChannel, selectors[next], next)); // 绑定Handler
                if (++next == selectors.length) {
                    next = 0; //越界后重新分配
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
