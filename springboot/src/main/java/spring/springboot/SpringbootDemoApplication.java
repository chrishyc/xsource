package spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

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
 * {@link PropertiesPropertySourceLoader#loadProperties}加载配置文件
 * <p>
 * 4.run方法开始运行bean工厂{@link AbstractApplicationContext#refresh()}
 * <p>
 * 5.{@link SpringApplication}构造时,会从classpath目录所有jar中加载spring.factories,
 * 从中获取key={@link org.springframework.context.ApplicationContextInitializer}
 * key={@link org.springframework.context.ApplicationListener}
 * key={@link org.springframework.boot.SpringApplicationRunListener}
 * key={@link org.springframework.boot.SpringBootExceptionReporter}
 * <p>
 * 6.key={@link org.springframework.boot.autoconfigure.EnableAutoConfiguration}
 * 任何需要实现自动配置的类,key都为此值
 * <p>
 * key={@link org.springframework.boot.autoconfigure.AutoConfigurationImportFilter}
 * ```
 * org.springframework.boot.autoconfigure.condition.OnBeanCondition,\
 * org.springframework.boot.autoconfigure.condition.OnClassCondition,\
 * org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition
 * ```
 * 自动配置过滤逻辑{@link AutoConfigurationImportSelector#filter}
 * {@link OnClassCondition#getOutcomes}
 * <p>
 * META-INF/spring-autoconfigure-metadata.properties配置classpath中所有META-INF/spring.factories中申明的所有***AutoConfiguaration以及
 * 他们对应的OnClassCondition，通过此处的OnClassCondition来决定这些***AutoConfiguaration是否被加载，最后把能加载的类
 * 存入{@link AutoConfigurationImportSelector.AutoConfigurationEntry}中
 */
//@MapperScan(basePackages = {"spring.springboot.mapper"})
@SpringBootApplication
@ComponentScan(basePackages = "spring.springboot.conditional")
//@EnableCaching
//@EnableRedisHttpSession
public class SpringbootDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
}
