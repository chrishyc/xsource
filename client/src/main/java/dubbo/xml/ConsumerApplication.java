package dubbo.xml;

import web.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerApplication {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        String result = helloService.sayHi("world");
        System.out.println("result=" + result);
        System.in.read();
    }
}
