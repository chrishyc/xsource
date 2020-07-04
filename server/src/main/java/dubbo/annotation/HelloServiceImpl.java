package dubbo.annotation;

import demo.HelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl   implements HelloService {
    @Override
    public String sayHi(String name) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello:"+name;
    }
}
