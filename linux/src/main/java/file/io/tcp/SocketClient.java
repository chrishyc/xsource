package file.io.tcp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @create: 2020-05-17 16:18
 */
public class SocketClient {

    public static void main(String[] args) {

        try {
            Socket client = new Socket();
            // 创建 socket 并连接服务器
            client.bind(new InetSocketAddress(3000));
            client.connect(new InetSocketAddress("localhost", 9090));

            client.setSendBufferSize(20);
            
            client.setTcpNoDelay(true);
            OutputStream out = client.getOutputStream();

            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while(true){
                String line = reader.readLine();
                if(line != null ){
                    byte[] bb = line.getBytes();
                    for (byte b : bb) {
                        out.write(b);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
