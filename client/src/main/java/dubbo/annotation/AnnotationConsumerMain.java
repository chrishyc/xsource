package dubbo.annotation;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnotationConsumerMain {
    public static void main(String[] args) throws Exception {
        System.out.println("-------------");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        // 获取消费者组件
        ComsumerComponet service = context.getBean(ComsumerComponet.class);
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (true) {
            executorService.execute(() -> {
//                service.monitorMethodA();
//                service.monitorMethodB();
                try {
                    service.monitorMethodC();
                } catch (Exception e) {
                    System.out.println("catch you again");
                }
            });
        }
    }
    
    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan(basePackages = "dubbo.annotation")
    @EnableDubbo(scanBasePackages = "dubbo.annotation")
    static class ConsumerConfiguration {
    
    }
}
