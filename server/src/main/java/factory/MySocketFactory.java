package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.net.SocketOptions;

public class MySocketFactory implements SocketImplFactory {
    @Override
    public SocketImpl createSocketImpl() {
        Object o = null;
        try {
            Class socket = Class.forName("java.net.SocksSocketImpl");
            Constructor constructor = socket.getDeclaredConstructor();
            constructor.setAccessible(true);
            o = constructor.newInstance();
            Method method = socket.getMethod("setOption", int.class, Object.class);
            method.setAccessible(true);
            method.invoke(o, SocketOptions.SO_TIMEOUT, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (SocketImpl) o;
    }
}
