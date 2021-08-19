package com.xsource.network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chris
 * <p>
 * lsof -i tcp:9877
 * COMMAND   PID  USER   FD   TYPE             DEVICE SIZE/OFF NODE NAME
 * java    11663 chris  137u  IPv6 0x2056b827c566e7df      0t0  TCP *:9877 (LISTEN)
 */
public class EchoServer {
    private final ServerSocket mServerSocket;

    public EchoServer(int port) throws IOException {
        // 1. 创建一个 ServerSocket 并监听端口 port
        mServerSocket = new ServerSocket();
        mServerSocket.setReuseAddress(true);
        mServerSocket.bind(new InetSocketAddress(9877));
    }

    public void run() throws IOException {
        // 2. 开始接受客户连接
        Socket client = mServerSocket.accept();
        new Thread(() -> {
            try {
                handleClient(client);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void handleClient(Socket socket) throws IOException, InterruptedException {
//        Thread.sleep(100000);
        // 3. 使用 socket 进行通信 ...
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }


    public static void main(String[] argv) {
        try {
            EchoServer server = new EchoServer(9877);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
