package ioc.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 1.解析得到beandefinition后，会先对继承了BeanFactoryPostProcessor接口的bean生成bean实例，
 * 进行set填充,，然后对此bean看ApplicationContextAware存在吗？setApplicationContext
 * 然后初始化afterPropertiesSet，最后执行postProcessBeanFactory
 *
 * 2.对于普通非BeanFactoryPostProcessor类，先执行ApplicationContextAware然后afterPropertiesSet
 *
 */
public class LifeBean implements InitializingBean, ApplicationContextAware, DisposableBean, BeanPostProcessor, BeanFactoryPostProcessor {

    public LifeBean(){
        System.out.println("LifeBean.LifeBean");
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("LifeBean.setApplicationContext");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LifeBean.afterPropertiesSet");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("LifeBean.postProcessBeanFactory");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("LifeBean.destroy");

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("LifeBean.postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("LifeBean.postProcessAfterInitialization");
        return bean;
    }
}
