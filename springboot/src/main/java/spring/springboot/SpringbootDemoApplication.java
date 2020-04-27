package spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author chris
 * springboot以module形式run无反应,发现原因:
 * 1.不是以maven项目运行,add as maven
 * 2.运行起来报错 Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean
 * 因为springboot2.0+需要tomcat8.5+版本
 *
 * 1.{@link SpringBootConfiguration}={@link Configuration}
 *
 * 2.{@link ComponentScan}
 *
 * 3.{@link EnableAutoConfiguration}
 *
 * 3a
 * {@link AutoConfigurationPackage}
 * {@link AutoConfigurationPackages.Registrar#registerBeanDefinitions}
 * 注册一个BeanDefinition来存储 base package 信息
 *
 *
 * 3b
 * {@link AutoConfigurationImportSelector}
 * {@link AutoConfigurationMetadataLoader#loadMetadata(ClassLoader, String)}
 * 加载META-INF/spring-autoconfigure-metadata.properties中变量
 */
@SpringBootApplication
public class SpringbootDemoApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
    
}
