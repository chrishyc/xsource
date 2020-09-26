package dubbo.impl;

import com.rpc.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

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
}
