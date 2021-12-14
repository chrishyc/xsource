package open.ioc.life;

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

/**
 * 1.解析得到beandefinition后，会先对继承了BeanFactoryPostProcessor接口的bean生成bean实例，
 * 进行set填充,，然后对此bean看ApplicationContextAware存在吗？setApplicationContext
 * 然后初始化afterPropertiesSet，最后执行postProcessBeanFactory
 *
 * 2.对于普通非BeanFactoryPostProcessor类，先执行ApplicationContextAware然后afterPropertiesSet
 *
 */
public class LifeBean implements InitializingBean, ApplicationContextAware, DisposableBean, BeanPostProcessor,
        BeanFactoryPostProcessor , InstantiationAwareBeanPostProcessor {

    public LifeBean(){
        System.out.println("LifeBean.LifeBean");
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("LifeBean.ApplicationContextAware.setApplicationContext");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LifeBean.InitializingBean.afterPropertiesSet");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("LifeBean.BeanFactoryPostProcessor.postProcessBeanFactory");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("LifeBean.DisposableBean.destroy");

    }

    /**
     * 实例化
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("LifeBean.InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        return null;
    }


    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("LifeBean.InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
        return true;
    }


    /**
     * 初始化
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("LifeBean.BeanPostProcessor.postProcessBeforeInitialization");
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("LifeBean.BeanPostProcessor.postProcessAfterInitialization");
        return bean;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        System.out.println("LifeBean.InstantiationAwareBeanPostProcessor.postProcessProperties");
        return null;
    }
}
