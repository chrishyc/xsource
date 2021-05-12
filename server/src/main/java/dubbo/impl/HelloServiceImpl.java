package dubbo.impl;

import com.rpc.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Random;

@DubboService
public class HelloServiceImpl implements HelloService {
    
    @Override
    public String sayHello(String name, int timeToWait) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello:" + name + RpcContext.getContext().getAttachment("clientIp");
    }
    
    @Override
    public String monitorMethodA() {
        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("enter monitorMethodA");
        return null;
    }
    
    @Override
    public String monitorMethodB() {
        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("enter monitorMethodB");
        return null;
    }
    
    @Override
    public String monitorMethodC() {
//        try {
//            Thread.sleep(new Random().nextInt(100));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("enter monitorMethodC");
//        return null;
        throw new RuntimeException("hello exception");
    }
}
