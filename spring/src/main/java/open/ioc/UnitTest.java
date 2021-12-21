package open.ioc;


import open.aop.pojo.AnnotationAdvice;
import open.aop.topology.T_01_BeforeAdvice;
import open.aop.topology.T_01_Target;
import open.ioc.cycle.T_Prototype;
import open.ioc.life.LifeBean;
import open.ioc.processor.importcandidate.MyComponent;
import open.ioc.processor.importcandidate.MyImportSelector;
import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class UnitTest {
    /**
     * -Dspring.profiles.active=CHRIS
     */
    @Test
    public void testClassPathXml() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("s_01_prototype.xml");
        T_Prototype t1 = ac.getBean(T_Prototype.class);
        System.out.println(t1);
        T_Prototype t2 = ac.getBean(T_Prototype.class);
        System.out.println(t2);
    }
    
    @Test
    public void test_ioc_01_Instantial_01_InstantiationAwareBeanPostProcessor() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("s_02_instantiat_InstantiationAwareBeanPostProcessor.xml");
        System.out.println(ac.getBean("a"));
    }
    
    @Test
    public void test_ioc_01_Instantial_02_FactoryBean() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("s_02_instantiat_FactoryBean.xml");
        System.out.println(ac.getBean("T_02_FactoryBean"));
    }
    
    @Test
    public void test_ioc_01_Instantial_03_Supplier() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("s_02_instantiat_Supplier.xml");
        System.out.println(ac.getBean("T_03_Supplier"));
    }
    
    @Test
    public void test_aop_01_ProxyFactory() {
        // 创建ProxyFactory
        T_01_Target target = new T_01_Target();
        ProxyFactory pf = new ProxyFactory(target);
        
        Advice beforeAdvice = new T_01_BeforeAdvice();
        // Advisor包含Pointcut和Advice，叫通知器，这里就设置了切点pointcut
        RegexpMethodPointcutAdvisor regexpAdvisor = new RegexpMethodPointcutAdvisor();
        regexpAdvisor.setPattern("open.aop.topology.T_01_Target.say()");
        regexpAdvisor.setAdvice(beforeAdvice);
        // 将通知器Advisor注册到ProxyFactory
        pf.addAdvisor(regexpAdvisor);
        
        
        // 生成代理，执行方法
        T_01_Target proxy = (T_01_Target) pf.getProxy();
        proxy.say();
    }
    
    @Test
    public void test_aop_01_AspectJProxyFactory() {
        // 创建ProxyFactory
        T_01_Target target = new T_01_Target();
        AspectJProxyFactory pf = new AspectJProxyFactory(target);
        
        pf.addAspect(AnnotationAdvice.class);
        
        // 生成代理，执行方法
        T_01_Target proxy = pf.getProxy();
        proxy.say();
    }
    
    @Test
    public void testFileSystemXml() {
        ApplicationContext ac = new FileSystemXmlApplicationContext("file:/Users/chris/xsource/src/main/resources/applicationContext.xml");
    }
    
    /**
     * 1.ApplicationListener生成逻辑{@link AbstractApplicationContext#registerListeners()}
     * 2.ApplicationListener调用逻辑{@link AbstractApplicationContext#publishEvent(Object)}
     */
    @Test
    public void testAnnotationConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        T_Prototype myComponent = ac.getBean(T_Prototype.class);
        System.out.println(myComponent);
//        MyImportSelector myImportSelector = ac.getBean(MyImportSelector.class);
//        System.out.println(myImportSelector);
    }
    
    @Test
    public void testAnnotationConfigScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext("ioc");
        MyComponent myComponent = ac.getBean(MyComponent.class);
        System.out.println(myComponent);
        MyImportSelector myImportSelector = ac.getBean(MyImportSelector.class);
        System.out.println(myImportSelector);
    }
    
    @Test
    public void testCycle() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("cycleBeans.xml");
//        TestBean testBean = ac.getBean(TestBean.class);
//        System.out.println(testBean);
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
        T_Prototype testBean = ac.getBean(T_Prototype.class);
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
    public void testXmlComponentScan() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    /**
     * {@link org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#registerDefaultFilters()}
     * 注册@Component.class
     * <p>
     * {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan}
     */
    @Test
    public void testAnnotationComponentScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }
    
    /**
     * {@link org.springframework.beans.factory.support.BeanDefinitionReaderUtils}
     * <p>
     * if (isInnerBean) {
     * // Inner bean: generate identity hashcode suffix.
     * id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + ObjectUtils.getIdentityHexString(definition);
     * }
     * <p>
     * spring内部bean都会带#
     * 例如DefaultBeanFactoryPointcutAdvisor#0
     */
    @Test
    public void testInnerBean() {
    
    }
    
    /**
     * debug bean实例关键点
     * postProcessBeforeInitialization，
     * postProcessAfterInitialization，
     * postProcessBeforeInstantiation，
     * postProcessAfterInstantiation，
     * addSingletonFactory，
     * addSingleton
     * <p>
     * AbstractApplicationContext.refresh
     */
    @Test
    public void testDebugPoint() {
    
    }
    
    @Test
    public void testBeanWrapper() {
        Car car = new Car();
        BeanWrapper beanWrapperOfCar = PropertyAccessorFactory.forBeanPropertyAccess(car);
        
        PropertyValue brandValue = new PropertyValue("brand", 124);
        
        PropertyValue maxSpeedValue = new PropertyValue("maxSpeed", "333");
        PropertyValue priceValue = new PropertyValue("price", "324.0");
        
        beanWrapperOfCar.setPropertyValue(brandValue);
        beanWrapperOfCar.setPropertyValue(maxSpeedValue);
        beanWrapperOfCar.setPropertyValue(priceValue);
    }
}
