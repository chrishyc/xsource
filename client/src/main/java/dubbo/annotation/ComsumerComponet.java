package dubbo.annotation;

import com.rpc.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class ComsumerComponet {
    @DubboReference
    private HelloService helloService;
    
    public String sayHello(String name) {
        return helloService.sayHello(name, 1);
    }
    
    public String monitorMethodA() {
        return helloService.monitorMethodA();
    }
    
    public String monitorMethodB() {
        return helloService.monitorMethodB();
    }
    
    public String monitorMethodC() {
        return helloService.monitorMethodC();
    }
    
}
