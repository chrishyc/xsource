package demo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

public class Client {
    public static <T> T getRemoteProxyObj(Class service, InetSocketAddress inetSocketAddress) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket();
                ObjectOutputStream outputStream = null;
                ObjectInputStream inputStream = null;
                try {
                    //与端口建立连接
                    socket.connect(inetSocketAddress);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    
                    //按顺序将参数传给server端
                    outputStream.writeUTF(service.getName());
                    outputStream.writeUTF(method.getName());
                    outputStream.writeObject(method.getParameterTypes());
                    outputStream.writeObject(args);
                    
                    //获取返回的结果
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    return inputStream.readObject();
                } finally {
                    Objects.requireNonNull(inputStream).close();
                    Objects.requireNonNull(outputStream).close();
                    socket.close();
                }
                
            }
        });
    }
}
