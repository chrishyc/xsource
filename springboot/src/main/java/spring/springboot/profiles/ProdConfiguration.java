package spring.springboot.profiles;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import spring.springboot.pojo.Cat;

@Configuration
@Profile("prod&dev")
@PropertySource("classpath:application-prod.properties")
@ConfigurationProperties(prefix = "chris")
public class ProdConfiguration {
    private String name;
    
    @Profile("prod")
    @Bean
    public Cat catProvider() {
        return new Cat();
    }
}
