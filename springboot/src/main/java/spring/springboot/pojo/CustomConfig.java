package spring.springboot.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chris
 */
@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "custom")
@PropertySource("classpath:custom.properties")
public class CustomConfig {
    private String name;
    private String age;
    private String sex;
    private String hobby;
    @Value("${custom.description}")
    private String test;
}
