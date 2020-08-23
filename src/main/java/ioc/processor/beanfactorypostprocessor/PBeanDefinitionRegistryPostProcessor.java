package ioc.processor.beanfactorypostprocessor;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

@Component
public class PBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
    public PBeanDefinitionRegistryPostProcessor() {
        System.out.println("PBeanDefinitionRegistryPostProcessor constructor");
    }
    
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("PBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry");
        
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("PBeanDefinitionRegistryPostProcessor postProcessBeanFactory");
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
