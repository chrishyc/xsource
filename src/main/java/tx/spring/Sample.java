package tx.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
    @Test
    public void testXml() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object xmlService = beanFactory.getBean(XmlService.class);
        System.out.println(xmlService);
    }

    @Test
    public void testAnnotation() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object annotationService = beanFactory.getBean(AnnotationService.class);
        System.out.println(annotationService);
    }
}
