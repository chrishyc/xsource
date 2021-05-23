package file.io.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @create: 2020-05-17 05:34
 * BIO  多线程的方式
 *
 * 查看tcp连接状态
 * lsof -i -n -P | grep 9090
 *
 * jps -l
 *
 * 查看tcp文件fd
 * lsof -p 5288
 *
 * pstree查看进程树
 */
public class SocketIOPropertites {


    //server socket listen property:
    private static final int RECEIVE_BUFFER = 10;
    private static final int SO_TIMEOUT = 0;
    private static final boolean REUSE_ADDR = false;
    private static final int BACK_LOG = 2;
    //client socket listen property on server endpoint:
    private static final boolean CLI_KEEPALIVE = false;
    private static final boolean CLI_OOB = false;
    private static final int CLI_REC_BUF = 20;
    private static final boolean CLI_REUSE_ADDR = false;
    private static final int CLI_SEND_BUF = 20;
    private static final boolean CLI_LINGER = true;
    private static final int CLI_LINGER_N = 0;
    private static final int CLI_TIMEOUT = 0;
    /**
     * Disable Nagle's algorithm for this connection
     * unix网络编程7.9
     */
    private static final boolean CLI_NO_DELAY = false;
/*

    StandardSocketOptions.TCP_NODELAY
    StandardSocketOptions.SO_KEEPALIVE
    StandardSocketOptions.SO_LINGER
    StandardSocketOptions.SO_RCVBUF
    StandardSocketOptions.SO_SNDBUF
    StandardSocketOptions.SO_REUSEADDR

 */
    
    
    /**
     * 参考UNIX网络编程
     * @param args
     */
    public static void main(String[] args) {

        ServerSocket server = null;
        try {
            server = new ServerSocket();
            /**
             * 对应listen(int sockfd,int backlog)中第二个参数
             *
             * https://www.cnblogs.com/Orgliny/p/5780796.html
             */
            server.bind(new InetSocketAddress(9090), BACK_LOG);
            /**
             *对应setsockopt(int sockfs,int level,int optname,const void *optvalue,socklen_t optlen)
             * 第七章
             */
            server.setReceiveBufferSize(RECEIVE_BUFFER);
            /**
             * TIME_WAIT时也可以重用端口号，不会导致对端一直重发FIN?
             */
            server.setReuseAddress(REUSE_ADDR);
            /**
             * 超时逻辑分析:https://cloud.tencent.com/developer/article/1574588
             */
            server.setSoTimeout(SO_TIMEOUT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server up use 9090!");
        try {
            while (true) {
    
                /**
                 * 内核创建了socker连接，只是创建了四元组(ip1,port1,ip2,port2),但还未绑定到服务器进程
                 *
                 *  lsof -p 5765
                 * java    5765 chris  130u  IPv6 0x3db2bb7ec1726071      0t0                 TCP *:websm (LISTEN)
                 */
                 System.in.read();  //分水岭：
    
                /**
                 * linux内核accept源码,创建socket fd绑定到进程描述符,但是并没有创建新的端口号
                 * accept绑定四元组(ip1,port1,ip2,port2)到 fd,形成socket fd，程序从socket fd中
                 * 读取数据，socket fd从四元组(ip1,port1,ip2,port2)中读取数据
                 *
                 * https://cloud.tencent.com/developer/article/1441582
                 *
                 * 此时losf -p server_pid只能看到listen的socketfd,没有来自客户端的socketfd连接
                 *
                 *
                 *
                 */
                Socket client = server.accept();  //阻塞的，没有 -1  一直卡着不动  accept(4,
                /**
                 * lsof -p 5765
                 * java    5765 chris  130u  IPv6 0x3db2bb7ec1726071      0t0                 TCP *:websm (LISTEN)
                 * java    5765 chris  132u  IPv6 0x3db2bb7ebd85dd51      0t0                 TCP localhost:websm->localhost:49572 (CLOSE_WAIT)
                 * java    5765 chris  133u  IPv6 0x3db2bb7ebd85ea11      0t0                 TCP localhost:websm->localhost:49606 (ESTABLISHED)
                 */
                System.out.println("client port: " + client.getPort());

                client.setKeepAlive(CLI_KEEPALIVE);
                client.setOOBInline(CLI_OOB);
                client.setReceiveBufferSize(CLI_REC_BUF);
                client.setReuseAddress(CLI_REUSE_ADDR);
                client.setSendBufferSize(CLI_SEND_BUF);
                client.setSoLinger(CLI_LINGER, CLI_LINGER_N);
                client.setSoTimeout(CLI_TIMEOUT);
                client.setTcpNoDelay(CLI_NO_DELAY);

                //client.read   //阻塞   没有  -1 0
                new Thread(
                        () -> {
                            try {
                                InputStream in = client.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                char[] data = new char[1024];
                                while (true) {

                                    int num = reader.read(data);

                                    if (num > 0) {
                                        System.out.println("client read some data is :" + num + " val :" + new String(data, 0, num));
                                    } else if (num == 0) {
                                        System.out.println("client readed nothing!");
                                        continue;
                                    } else {
                                        System.out.println("client readed -1...");
                                        System.in.read();
                                        client.close();
                                        break;
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                ).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
