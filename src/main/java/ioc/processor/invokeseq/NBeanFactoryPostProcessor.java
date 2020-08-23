package ioc.processor.invokeseq;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class NBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public NBeanFactoryPostProcessor() {
        System.out.println("NBeanFactoryPostProcessor constructor");
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("NBeanFactoryPostProcessor postProcessBeanFactory");
    }
}
