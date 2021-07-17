package chris.mystarter;

import com.rpc.api.service.TargetA;
import com.rpc.api.service.TargetB;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConditionalClass {
    @Bean
    @ConditionalOnClass(TargetA.class)
    TargetA getBeanA() {
        return new TargetA();
    }
    @Bean
    @ConditionalOnMissingClass("TargetA")
    TargetB getBeanB() {
        return new TargetB();
    }
}
