package file.io.nio.rpc.rpcdemo.service;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 21:16
 */
public class MyFly implements Fly {

    @Override
    public void xxoo(String msg) {
        System.out.println("server,get client arg:"+msg);
    }
}
