package dubbo.annotation;

import demo.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class ComsumerComponet {
    @DubboReference
    private HelloService helloService;
    public String  sayHello(String name){
        return  helloService.sayHi(name);
    }

}
