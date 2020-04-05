package ioc;

import demo.spring.utils.ConnectionUtils;
import demo.spring.utils.TransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "demo.spring")
public class AppConfig {
    @Bean(name = "connectionUtils")
    public ConnectionUtils createConnectionUtils(){
        return new ConnectionUtils();
    }

    @Bean(name = "transactionManager")
    public TransactionManager createTransactionManager(ConnectionUtils connectionUtils){
        return new TransactionManager(connectionUtils);
    }
}
