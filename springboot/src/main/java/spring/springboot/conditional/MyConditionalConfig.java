package spring.springboot.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import spring.springboot.SpringbootDemoApplication;

@Configuration
@ConditionalOnClass(value = {SpringbootDemoApplication.class})
public class MyConditionalConfig {
}
