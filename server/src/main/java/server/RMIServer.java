package server;


import stub.HelloServiceImpl;
import stub.IHelloService;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteCall;

import sun.rmi.registry.*;
import sun.rmi.server.*;
import sun.rmi.transport.tcp.*;
/**
 * 调试rpc:
 * 1.查看状态机watch -n 0.5 -d 'netstat -ant | grep -E "9898|63039|63121"'
 * 2.wireShake查看端口号tcp.port == 9898||tcp.port==63039||tcp.port==63121
 * 3.debug
 *
 * 1.客户端_stub,{@link UnicastRef#invoke(Remote, Method, Object[], long)}
 * 2.服务端_skeleton,{@link UnicastServerRef#dispatch(Remote, RemoteCall)}
 * 3.注册中心:
 * 客户端:RegistryImpl_Stub(send,port:63215)
 * 服务端:RegistryImpl_Skel(accept,port:9898),{@link RegistryImpl_Skel#dispatch(Remote, RemoteCall, int, long)}
 * 原服务:RegistryImpl
 *
 * 底层socket
 * {@link TCPEndpoint}
 *
 * {@link TCPChannel#newConnection()}
 * {@link TCPConnection#getInputStream()}
 * {@link TCPConnection#getOutputStream()}
 *
 * {@link TCPTransport#listen()}
 *
 */
public class RMIServer {
    
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        //1.创建一个远程对象,同时也会创建stub对象，以及skeleton对象
        IHelloService helloService = new HelloServiceImpl();
        
        //2.启动注册服务:创建了远程对象注册表Registry的实例，并指定端口为8888
        LocateRegistry.createRegistry(9898);
        
        //3.真正注册：绑定的URL的标准格式：rmi://host:port/name rmi可以省略
        Naming.bind("//127.0.0.1:9898/zm", helloService);
        
        
    }
    
    
}
