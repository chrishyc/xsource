package tx;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tx.spring.AnnotationService;
import tx.xml.XmlService;

public class Sample {
    /**
     * {@link org.springframework.transaction.config.TxNamespaceHandler}
     */
    @Test
    public void testXml() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("tx.xml");
        XmlService xmlService = beanFactory.getBean(XmlService.class);
        xmlService.query();
    }

    @Test
    public void testAnnotation() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object annotationService = beanFactory.getBean(AnnotationService.class);
        System.out.println(annotationService);
    }
}
