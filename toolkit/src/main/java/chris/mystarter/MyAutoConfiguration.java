package chris.mystarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chris
 */
@Configuration
@ConditionalOnClass
public class MyAutoConfiguration {
    @Bean
    public TestBean testBean() {
        return new TestBean();
    }
}
