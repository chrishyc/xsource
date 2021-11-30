package web.spring.open.ioc.processor.beanpostprocessor;

import web.spring.mine.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;

@Component
public class PBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {
    @Autowired
    private NBeanPostProcessor n;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("PBeanPostProcessor,postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("PBeanPostProcessor,postProcessAfterInitialization" + n);
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
