package open;


import net.sf.cglib.core.DebuggingClassWriter;
import open.aop.Config;
import open.aop.pojo.AnnotationAdvice;
import open.aop.pojo.Target;
import open.aop.pojo.Target2;
import open.aop.topology.T_01_BeforeAdvice;
import open.aop.topology.T_01_Target;
import open.ioc.AppConfig;
import open.ioc.Car;
import open.ioc.cycle.T_Prototype;
import open.ioc.life.LifeBean;
import open.ioc.processor.importcandidate.MyComponent;
import open.ioc.processor.importcandidate.MyImportSelector;
import open.tx.TXConfig;
import open.tx.propagation.T_01_propagation;
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
    
    /**
     * 1.xsource中pom改成jar
     */
    @Test
    public void test_01_namespace() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("s_aop_01_namespace.xml");
        Target target = ac.getBean(Target.class);
        target.transfer();
    }
    
    @Test
    public void testAnnotationAop() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        Target2 target = ac.getBean(Target2.class);
        target.transfer();
    }
    
    /**
     * aop代理生成器internalAutoProxyCreator什么时候生成?
     * <p>
     * {@link org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader#parseBeanDefinitions}
     * 解析节点Element时，会根据节点的namespace来判断节点类型，是否是自定义类型。
     * <p>
     * 如果是自定义类型，然后调用{@link org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver#resolve}注册自定义
     * NamespaceHandler.
     * <p>
     * 对于有<aop:config/>标签的xml配置,是自定义类型namespace节点
     * 会注册{@link org.springframework.aop.config.AopNamespaceHandler},而此类会创建
     * <p>
     * <p>
     * {@link org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator}并放入
     * beanDefinitionMap中，之后解析会生成实例放入singletonObjects中
     */
    @Test
    public void testInternalAutoProxyCreator() {
    
    }
    
    /**
     * AOP有几个核心概念类和接口:
     * 1.{@link org.aopalliance.intercept.MethodInterceptor}代理类的拦截器逻辑，例如
     * {@link org.springframework.transaction.interceptor.TransactionInterceptor}实现对tx事务管理的代理增强
     * spring代理拦截器为{@link org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor}
     * 而TransactionInterceptor等拦截器会以chain链式串起来在DynamicAdvisedInterceptor内部执行
     * <p>
     * 2.{@link org.aopalliance.intercept.MethodInvocation}方法执行器，代表实现类为
     * {@link org.springframework.aop.framework.CglibAopProxy.CglibMethodInvocation}
     * DynamicAdvisedInterceptor.intercept拦截每个方法时，会生成一个CglibMethodInvocation方法执行器，
     * 该方法执行器会注入上面的链式拦截器进行执行。拦截器执行到最后时，会执行原始方法回调
     */
    @Test
    public void testAopInvocation() {
    
    }
    
    @Test
    public void test_tx_01_propagation() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TXConfig.class);
        T_01_propagation target = ac.getBean(T_01_propagation.class);
        target.methodB();
    }
    
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/chris/xsource/src/main/java/aop/");
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
        Target2 target = ac.getBean(Target2.class);
        target.transfer();
    }
}
