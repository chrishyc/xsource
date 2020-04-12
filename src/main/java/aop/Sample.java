package aop;

import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
    @Test
    public void testXmlAop() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
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
     *
     * {@link org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader#parseBeanDefinitions}
     * 解析节点Element时，会根据节点的namespace来判断节点类型，是否是自定义类型。
     *
     * 如果是自定义类型，然后调用{@link org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver#resolve}注册自定义
     * NamespaceHandler.
     *
     * 对于有<aop:config/>标签的xml配置,是自定义类型namespace节点
     * 会注册{@link org.springframework.aop.config.AopNamespaceHandler},而此类会创建
     *
     *
     * {@link org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator}并放入
     * beanDefinitionMap中，之后解析会生成实例放入singletonObjects中
     */
    @Test
    public void testInternalAutoProxyCreator(){

    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/chris/xsource/src/main/java/aop/");
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
        Target target = ac.getBean(Target.class);
        target.transfer();
    }
}
