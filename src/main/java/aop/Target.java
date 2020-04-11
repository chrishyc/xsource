package aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Target implements InitializingBean, ApplicationContextAware, DisposableBean, BeanPostProcessor,
        BeanFactoryPostProcessor, InstantiationAwareBeanPostProcessor {
    public void transfer() {
//        throw new RuntimeException("no error");
    }

    public Target() {
        System.out.println("Target.Target");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Target.ApplicationContextAware.setApplicationContext");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Target.InitializingBean.afterPropertiesSet");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("Target.BeanFactoryPostProcessor.postProcessBeanFactory");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Target.DisposableBean.destroy");

    }

    /**
     * 初始化
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Target.BeanPostProcessor.postProcessBeforeInitialization:" + beanName);
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Target.BeanPostProcessor.postProcessAfterInitialization:" + beanName);
        return bean;
    }

    /**
     * 实例化
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("Target.InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation:" + beanName);
        return null;
    }


    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("Target.InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation:" + beanName);
        return true;
    }


    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        System.out.println("Target.InstantiationAwareBeanPostProcessor.postProcessProperties:" + beanName);
        return null;
    }
}
