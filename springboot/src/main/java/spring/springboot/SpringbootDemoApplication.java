package spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chris
 * springboot以module形式run无反应,发现原因:
 * 1.不是以maven项目运行,add as maven
 * 2.运行起来报错 Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean
 * 因为springboot2.0+需要tomcat8.5+版本
 */
@SpringBootApplication
public class SpringbootDemoApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
    
}
