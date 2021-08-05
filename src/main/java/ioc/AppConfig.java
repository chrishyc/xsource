package ioc;

import demo.spring.utils.ConnectionUtils;
import demo.spring.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "ioc",
    "ioc.cycle",
    "ioc.eventListener",
    "ioc.processor.beanfactorypostprocessor",
    "ioc.processor.beanpostprocessor",
    "ioc.processor.importcandidate"})
public class AppConfig {

  @Value("${sun.io.unicode.encoding}")
  private String name;

  @Bean(name = "connectionUtils")
  public ConnectionUtils createConnectionUtils() {
    return new ConnectionUtils();
  }

  @Bean(name = "transactionManager")
  public TransactionManager createTransactionManager(ConnectionUtils connectionUtils) {
    return new TransactionManager(connectionUtils);
  }
}
