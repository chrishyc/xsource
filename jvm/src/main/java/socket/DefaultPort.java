package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;
public class DefaultPort {
    /**
     * {@link AbstractPlainSocketImpl#socketBind}生成localport
     * @param argv
     * @throws IOException
     */
    public static void main(String[] argv) throws IOException {
        ServerSocket serverSocket = new ServerSocket(0); //读取空闲的可用端口
        int port = serverSocket.getLocalPort();
        System.out.println("系统分配的端口号 port=" + port);
    }
}
