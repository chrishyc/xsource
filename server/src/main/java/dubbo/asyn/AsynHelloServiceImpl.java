package dubbo.asyn;

import web.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class AsynHelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "asyn result:" + name;
    }
}
