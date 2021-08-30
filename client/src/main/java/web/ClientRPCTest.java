package web;

import java.net.InetSocketAddress;

public class ClientRPCTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //通过类反射机制类参数
        HelloService service = Client.getRemoteProxyObj(Class.forName("web.HelloService"), new InetSocketAddress("127.0.0.1", 9999));
        System.out.println((service.sayHi("zhangsan")));
    }
}
