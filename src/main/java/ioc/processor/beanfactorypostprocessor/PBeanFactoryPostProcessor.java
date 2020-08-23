package ioc.processor.beanfactorypostprocessor;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;

@Component
public class PBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {
    public PBeanFactoryPostProcessor() {
        System.out.println("PBeanFactoryPostProcessor constructor");
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("PBeanFactoryPostProcessor postProcessBeanFactory");
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
