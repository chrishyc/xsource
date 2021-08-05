package ioc.processor.beanpostprocessor;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

@Component
public class OBeanPostProcessor implements BeanPostProcessor, Ordered {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("OBeanPostProcessor,postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("OBeanPostProcessor,postProcessAfterInitialization");
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
