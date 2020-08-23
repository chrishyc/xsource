package ioc.processor.invokeseq;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;

@Component
public class OBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, Ordered {
    public OBeanDefinitionRegistryPostProcessor() {
        System.out.println("OBeanDefinitionRegistryPostProcessor constructor");
    }
    
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("OBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry");
        
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("OBeanDefinitionRegistryPostProcessor postProcessBeanFactory");
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
