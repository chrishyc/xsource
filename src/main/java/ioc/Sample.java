package ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Sample {
    @Test
    public void testClassPathXml() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestBean testBean = ac.getBean(TestBean.class);
        System.out.println(testBean);
    }

    @Test
    public void testFileSystemXml() {
        ApplicationContext ac = new FileSystemXmlApplicationContext("file:/Users/chris/xsource/src/main/resources/applicationContext.xml");
    }

    @Test
    public void testAnnotationConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testCycle(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("cycleBeans.xml");
//        TestBean testBean = ac.getBean(TestBean.class);
//        System.out.println(testBean);
    }
}
