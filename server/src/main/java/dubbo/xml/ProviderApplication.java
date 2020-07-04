package dubbo.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderApplication {
    public static void main(String[] args) throws  Exception{
        ClassPathXmlApplicationContext   applicationContext  = new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
        applicationContext.start();
        System.in.read();
    }
}
