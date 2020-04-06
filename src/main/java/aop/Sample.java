package aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
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
}
