package file.io.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ReuseClient {
    private final Socket mSocket;
    
    public ReuseClient(String host, int port) throws IOException {
        mSocket = new Socket();
        // 创建 socket 并连接服务器
        mSocket.setReuseAddress(true);
        mSocket.bind(new InetSocketAddress(3000));
        
        mSocket.connect(new InetSocketAddress(host, port));
    }
    
    public void run() throws IOException {
        // 和服务端进行通信
        Thread readerThread = new Thread(this::readResponse);
        readerThread.start();
        
        OutputStream out = mSocket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = System.in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }
    
    private void readResponse() {
        try {
            InputStream in = mSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0) {
                System.out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] argv) {
        try {
            // 由于服务端运行在同一主机，这里我们使用 localhost
            ReuseClient client = new ReuseClient("localhost", 9878);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
