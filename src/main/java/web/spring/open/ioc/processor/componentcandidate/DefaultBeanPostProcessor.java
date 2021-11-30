package web.spring.open.ioc.processor.componentcandidate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import web.spring.open.ioc.AppConfig;

@Configuration
@Component
@ComponentScan(basePackages = "ioc.eventListener")
@Import(value = AppConfig.class)
@ImportResource
public class DefaultBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("dataSource".equals(beanName)) {
        }
        System.out.println("BeanPostProcessor 实现类 postProcessBeforeInitialization 方法被调用中......");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("accountDao".equals(beanName)) {
        }
        System.out.println("BeanPostProcessor 实现类 postProcessAfterInitialization 方法被调用中......");
        return bean;
    }
}
