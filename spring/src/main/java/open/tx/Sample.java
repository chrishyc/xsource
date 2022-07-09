package open.tx;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import open.tx.annotation.AnnotationService;
import open.tx.xml.XmlService;

public class Sample {
    /**
     * {@link org.springframework.transaction.config.TxNamespaceHandler}
     * 1.主逻辑在{@link org.springframework.transaction.interceptor.TransactionInterceptor#invoke}
     *
     * 2.TransactionInterceptor创建时机：在解析的标签为advice时，会使用TxAdviceBeanDefinitionParser解析器，
     *   此解析器生成的BeanDefinition包含了TransactionInterceptor.class
     *
     * 3.
     */
    @Test
    public void testXml() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("tx.xml");
        XmlService xmlService = beanFactory.getBean(XmlService.class);
        xmlService.query();
    }

    /**
     * {@link org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass}
     * 会处理@ComponentScans，@Import,@Component等注解
     *
     * 其中@EnableTransactionManagement，@Enable**注解的解析是递归查看注解内部是否有@Import注解，
     * 有@Import则进行解析{@link org.springframework.context.annotation.ConfigurationClassParser#collectImports}
     *
     * {@link org.springframework.transaction.annotation.EnableTransactionManagement}
     * 中使用{@link org.springframework.context.annotation.Import},@Import 支持
     * importing {@code @Configuration} classes, {@link ImportSelector} and
     *  * {@link ImportBeanDefinitionRegistrar} implementations
     *
     * {@link org.springframework.context.annotation.ConfigurationClassParser#processImports}
     *
     * if (candidate.isAssignable(ImportSelector.class)) {
     * }
     * else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
     * }
     * else {
     * }
     */
    @Test
    public void testEnableTransactionManagement() throws InterruptedException {
        ApplicationContext beanFactory = new AnnotationConfigApplicationContext(TXConfig.class);
        AnnotationService annotationService = beanFactory.getBean(AnnotationService.class);
        annotationService.transfer();
//        annotationService.query();
        System.out.println("testEnableTransactionManagement:" + annotationService.getClass());
        Thread.sleep(1000000);
    }

    /**
     * {@link org.springframework.transaction.annotation.TransactionManagementConfigurationSelector}
     * tx注解形式的总配置类,以ImportSelector形式导入
     *
     * {@link org.springframework.context.annotation.AutoProxyRegistrar}
     * tx注解形式的代理工厂 配置类，以ImportBeanDefinitionRegistrar形式导入
     *
     * {@link org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration}
     * tx注解形式的事务管理 配置类，以@Configuration形式导入
     *
     * 事务管理 需要spring统一的PlatformTransactionManager
     * 而PlatformTransactionManager需要datasource注入，因此还需要注入这两个bean
     *
     * 在{@link org.springframework.transaction.interceptor.TransactionInterceptor#invokeWithinTransaction}中，
     * 会通过determineTransactionManager查找PlatformTransactionManager的实现类
     */
    @Test
    public void testAnnotationAdvisor(){
        ApplicationContext beanFactory = new AnnotationConfigApplicationContext(TXConfig.class);
        AnnotationService annotationService = beanFactory.getBean(AnnotationService.class);
        annotationService.query();
    }
}
