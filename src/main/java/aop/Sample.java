package aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
    @Test
    public void testClassPathXml() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Target target = ac.getBean(Target.class);
        target.transfer();
    }
}
