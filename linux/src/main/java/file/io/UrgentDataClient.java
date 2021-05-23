package file.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
 *
 */
public class UrgentDataClient {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1234);
//        socket.setOOBInline(true);
        OutputStream out = socket.getOutputStream();
        OutputStreamWriter outWriter = new OutputStreamWriter(out);
        outWriter.write(67); // 向服务器发送字符"C"
        outWriter.write("hello world\r\n");
        socket.sendUrgentData(65); // 向服务器发送字符"A"
        socket.sendUrgentData(322); // 向服务器发送字符"B"
//        outWriter.flush();
        socket.sendUrgentData(214); // 向服务器发送汉字”中”
        socket.sendUrgentData(208);
        socket.sendUrgentData(185); // 向服务器发送汉字”国”
        socket.sendUrgentData(250);
        socket.close();
    }
}
