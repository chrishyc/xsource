package aop;

import aop.pojo.Target;
import aop.pojo.Target2;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
    /**
     * 1.xsource中pom改成jar
     */
    @Test
    public void testXmlAop() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
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
    
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/chris/xsource/src/main/java/aop/");
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
        Target2 target = ac.getBean(Target2.class);
        target.transfer();
    }
}
