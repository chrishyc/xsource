package ioc.processor.beanfactorypostprocessor;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

@Component
public class OBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    public OBeanFactoryPostProcessor() {
        System.out.println("OBeanFactoryPostProcessor constructor");
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("OBeanFactoryPostProcessor postProcessBeanFactory");
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
