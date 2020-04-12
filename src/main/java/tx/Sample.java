package tx;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tx.annotation.AnnotationService;
import tx.annotation.Config;
import tx.xml.XmlService;

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

    @Test
    public void testAnnotation() {
        ApplicationContext beanFactory = new AnnotationConfigApplicationContext(Config.class);
        Object annotationService = beanFactory.getBean(AnnotationService.class);
        System.out.println(annotationService);
    }
}
