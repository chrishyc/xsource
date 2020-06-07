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
import sun.rmi.transport.*;
import java.rmi.server.*;
/**
 * 调试rpc:
 * 1.查看状态机watch -n 0.5 -d 'netstat -ant | grep -E "9898|63039|63121"'
 * 2.wireShake查看端口号tcp.port == 9898||tcp.port==63039||tcp.port==63121
 * 3.debug
 *
 * 流程:
 * 服务端开启监听中心,并绑定端口号9898
 * 客户端请求端口号9898获取目标远程对象代理类(有远程对象的port和方法索引表).第一个socket连接监听中心
 * 客户端开始调用方法，此时创建新的socket用于连接远程对象的监听端口号port，和远程对象进行通信.第二个socket连接
 *
 *
 * 相关概念：
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
 * {@link StreamRemoteCall#executeCall()}
 *
 * {@link RemoteObjectInvocationHandler.methodToHash_Maps}记录远程方法对应的索引表
 *
 * {@link StreamRemoteCall#StreamRemoteCall(Connection, ObjID, int, long)}远程调用协议格式
 *
 * {@link UnicastRef#invoke(Remote, Method, Object[], long)} 客户端远程调用主逻辑
 *
 *
 *
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
