package spring.springboot.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "custom")
@PropertySource("classpath:custom.properties")
public class Custom {
    private String name;
    private String age;
    private String sex;
    private String hobby;
    @Bean
    public Dog dog(){
        return new Dog();
    }
}
