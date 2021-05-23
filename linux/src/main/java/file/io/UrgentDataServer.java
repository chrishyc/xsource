package file.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * OOB,out of band
 * 带外数据（OOB）是指对紧急数据，中断或放弃排队中的数据流；接收方应立即处理紧急数据。完成后，TCP通知应用程序恢复流队列的正常处理。
 *
 * OOB并不影响网络，“紧急”仅影响远程端的处理带外数据（OOB）是指对紧急数据，中断或放弃排队中的数据流；接收方应立即处理紧急数据。完成后，TCP通知应用程序恢复流队列的正常处理。
 *
 * OOB并不影响网络，“紧急”仅影响远程端的处理
 *
 * https://zh.wikipedia.org/wiki/%E4%BC%A0%E8%BE%93%E6%8E%A7%E5%88%B6%E5%8D%8F%E8%AE%AE
 *
 * https://stackoverflow.com/questions/60632863/sending-tcp-urgend-data-through-the-socket-in-java
 */
public class UrgentDataServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("服务器已经启动，端口号：1234");
        while (true) {
            Socket socket = serverSocket.accept();
//            socket.setOOBInline(true);
            InputStream in = socket.getInputStream();
            InputStreamReader inReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(inReader);
            System.out.println(bReader.readLine());
            System.out.println(bReader.readLine());
            socket.close();
        }
    }
}
