package open.tx;

import com.alibaba.druid.pool.DruidDataSource;
import open.tx.propagation.T_01_propagationB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Specify whether {@code @Bean} methods should get proxied in order to enforce
 * * bean lifecycle behavior
 * <p>
 * {@link org.springframework.context.annotation.ConfigurationClassUtils#checkConfigurationClassCandidate}中会解析
 * Configuration是否有proxyBeanMethods属性，有则在
 * {@link org.springframework.context.annotation.ConfigurationClassPostProcessor#enhanceConfigurationClasses}中生成代理类
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "open.tx")
@EnableTransactionManagement
public class TXConfig {

    @Bean
    public DataSource dataSource() {
        // configure and return the necessary JDBC DataSource
        DruidDataSource source = new DruidDataSource();
        source.setUrl("jdbc:mysql://127.0.0.1:3306/mysql");
        source.setUsername("root");
        source.setPassword("");
        return source;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
