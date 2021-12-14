package open.ioc.processor.beanfactorypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class NBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    public NBeanDefinitionRegistryPostProcessor() {
        System.out.println("NBeanDefinitionRegistryPostProcessor constructor");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("NBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("NBeanDefinitionRegistryPostProcessor postProcessBeanFactory");
    }
}
