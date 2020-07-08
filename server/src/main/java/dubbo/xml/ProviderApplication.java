package dubbo.xml;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.xml.*;
import org.w3c.dom.Element;
import org.apache.dubbo.config.spring.schema.*;
import org.apache.dubbo.config.spring.context.*;
import org.apache.dubbo.config.bootstrap.*;

/**
 * 1.spring支持自定义xml处理器{@link BeanDefinitionParserDelegate#parseCustomElement(Element)},
 * 会解析每个jar中/META-INF/spring.handlers目录中mapping关系
 * http\://dubbo.apache.org/schema/dubbo=org.apache.dubbo.config.spring.schema.DubboNamespaceHandler
 * 2.自定义解析器会注册监听器{@link DubboNamespaceHandler#registerApplicationListeners(BeanDefinitionRegistry)}
 * 3.{@link DubboBootstrapApplicationListener}监听器中启动{@link DubboBootstrap}
 */
public class ProviderApplication {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
        applicationContext.start();
        System.in.read();
    }
}
