package dubbo.xml;


import demo.HelloService;

import java.util.concurrent.TimeUnit;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "XML:" + name;
    }
}
