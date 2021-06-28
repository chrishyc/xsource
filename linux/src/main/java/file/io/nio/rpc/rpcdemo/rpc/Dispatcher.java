package file.io.nio.rpc.rpcdemo.rpc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 20:20
 */
public class Dispatcher {

    private static Dispatcher dis = null;
    static {
        dis = new Dispatcher();
    }
    public static Dispatcher getDis(){
        return dis;
    }
    private Dispatcher(){

    }

    public  static ConcurrentHashMap<String,Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String k,Object obj){
        invokeMap.put(k,obj);
    }
    public Object get(String k){
        return invokeMap.get(k);
    }

}
