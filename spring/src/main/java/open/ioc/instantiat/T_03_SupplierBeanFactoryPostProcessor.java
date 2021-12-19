package open.ioc.instantiat;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class T_03_SupplierBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("T_03_Supplier");
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinition;
        genericBeanDefinition.setInstanceSupplier(T_03_Supplier::createB);
    }
}
