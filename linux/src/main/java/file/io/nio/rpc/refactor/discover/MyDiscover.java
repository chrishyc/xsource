package file.io.nio.rpc.refactor.discover;


import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * @author: 马士兵教育
 * @create: 2020-07-19 00:14
 */
public class MyDiscover {

    static HashMap<Class, InetSocketAddress> rpcMap = new HashMap<>();

    public static void register(Class interfacInfo,InetSocketAddress address){
        rpcMap.put(interfacInfo,address);
    }

    public static InetSocketAddress discover(Class interfaceInfo){
        return rpcMap.get(interfaceInfo);
    }






}
