package spring.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * @author chris
 * springboot以module形式run无反应,发现原因:
 * 1.不是以maven项目运行,add as maven
 * 2.运行起来报错 Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean
 * 因为springboot2.0+需要tomcat8.5+版本
 * <p>
 * 1.{@link SpringBootConfiguration}={@link Configuration}
 * <p>
 * 2.{@link ComponentScan}
 * {@link ConfigurationClassParser#doProcessConfigurationClass)}
 * <p>
 * 3.{@link EnableAutoConfiguration}
 * <p>
 * 3a
 * {@link AutoConfigurationPackage}
 * {@link AutoConfigurationPackages.Registrar#registerBeanDefinitions}
 * 注册一个BeanDefinition来存储 base package 信息
 * <p>
 * <p>
 * 3b
 * {@link AutoConfigurationImportSelector}
 * {@link AutoConfigurationMetadataLoader#loadMetadata(ClassLoader, String)}
 * 加载META-INF/spring-autoconfigure-metadata.properties中变量
 * <p>
 * {@link SpringFactoriesLoader#loadSpringFactories},SPI机制
 * 加载所有META-INF/spring.factories
 * <p>
 * 4.run方法开始运行bean工厂{@link AbstractApplicationContext#refresh()}
 */
@MapperScan(basePackages = {"spring.springboot.mapper"})
@SpringBootApplication
public class SpringbootDemoApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
    
}
