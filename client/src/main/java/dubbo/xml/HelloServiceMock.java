package dubbo.xml;

import web.HelloService;

public class HelloServiceMock implements HelloService {
    @Override
    public String sayHi(String name) {
        return "hello mock";
    }
}
