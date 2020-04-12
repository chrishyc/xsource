package ioc;

import ioc.cycle.TestBean;
import ioc.lazyinit.LazyInitBean;
import ioc.life.LifeBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Sample {
    @Test
    public void testClassPathXml() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestBean testBean = ac.getBean(TestBean.class);
        System.out.println(testBean);
    }

    @Test
    public void testFileSystemXml() {
        ApplicationContext ac = new FileSystemXmlApplicationContext("file:/Users/chris/xsource/src/main/resources/applicationContext.xml");
    }

    @Test
    public void testAnnotationConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testCycle() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("cycleBeans.xml");
//        TestBean testBean = ac.getBean(TestBean.class);
//        System.out.println(testBean);
    }

    @Test
    public void testLazyInit() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("lazyInit.xml");
        LazyInitBean lazyInitBean = ac.getBean(LazyInitBean.class);
        System.out.println();
    }

    /**
     * 1.生成invokeBeanFactoryPostProcessors
     * 2.生成registerBeanPostProcessors
     * 3.生成普通bean,finishBeanFactoryInitialization
     * <p>
     * 生成BeanFactoryPostProcessors时会调用已有BeanPostProcessors
     * 生成BeanPostProcessors时会调用已有BeanPostProcessors
     * 生成普通bean时会调用已有BeanPostProcessors
     * <p>
     * 普通bean:
     * 构造函数初始化
     * set注入
     * setApplicationContext
     * <p>
     * <p>
     * postProcessBeforeInitialization
     * afterPropertiesSet
     * postProcessAfterInitialization
     */
    @Test
    public void testProcessor() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("processor.xml");
        TestBean testBean = ac.getBean(TestBean.class);
        System.out.println(testBean);
    }

    @Test
    public void testLife() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("life.xml");
        LifeBean lifeBean = ac.getBean(LifeBean.class);
        System.out.println(lifeBean);
    }

    /**
     * {@link org.springframework.context.config.ContextNamespaceHandler}
     */
    @Test
    public void testComponentScan() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    /**
     * {@link org.springframework.beans.factory.support.BeanDefinitionReaderUtils}
     *
     * if (isInnerBean) {
     * // Inner bean: generate identity hashcode suffix.
     * id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + ObjectUtils.getIdentityHexString(definition);
     * }
     *
     * spring内部bean都会带#
     * 例如DefaultBeanFactoryPointcutAdvisor#0
     */
    @Test
    public void testInnerBean() {

    }
}
