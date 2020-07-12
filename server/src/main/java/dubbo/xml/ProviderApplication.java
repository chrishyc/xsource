package dubbo.xml;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.xml.*;
import org.w3c.dom.Element;
import org.apache.dubbo.config.spring.schema.*;
import org.apache.dubbo.config.spring.context.*;
import org.apache.dubbo.config.bootstrap.*;
import org.apache.dubbo.config.spring.*;

/**
 * 1.spring支持自定义xml处理器{@link BeanDefinitionParserDelegate#parseCustomElement(Element)},
 * 会解析每个jar中/META-INF/spring.handlers目录中mapping关系
 * http\://dubbo.apache.org/schema/dubbo=org.apache.dubbo.config.spring.schema.DubboNamespaceHandler
 * 2.自定义解析器会注册监听器{@link DubboNamespaceHandler#registerApplicationListeners(BeanDefinitionRegistry)}
 * 3.{@link DubboBootstrapApplicationListener}监听器中启动{@link DubboBootstrap}
 *
 *
 * 1.{@link DubboNamespaceHandler}自定义handler订阅自定义标签{@link DubboNamespaceHandler#init()}
 * 2.解析自定义标签时，根据namespaceURL定位到对应handler,然后从{@link DubboNamespaceHandler}中找到自定义标签对应的标签处理器进行处理
 * {@link ServiceBean}
 * 3.{@link DubboBeanDefinitionParser#parse(Element, ParserContext, Class, boolean)}解析RootBeanDefinition时,会将实例class
 * 设置为{@link ServiceBean},这样实例化标签<dubbo:service interface="demo.HelloService" ref="helloService" filter="timeFilter"/>
 * 即实例化{@link ServiceBean}
 */
public class ProviderApplication {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
        applicationContext.start();
        System.in.read();
    }
}
