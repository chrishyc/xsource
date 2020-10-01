package springcloud.config.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手动刷新:curl -X POST 'http://localhost:8002/actuator/refresh'
 */
@SpringBootApplication
public class ConfigclientApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ConfigclientApplication.class, args);
    }
    
    @RefreshScope
    @RestController
    public class HelloController {
        @Value("${dev.hello}")
        private String hello;
        
        
        @RequestMapping("/hello")
        public String hello() {
            return hello;
        }
    }
}
