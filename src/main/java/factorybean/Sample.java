package factorybean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
    @Test
    public void test() {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object companyBean1 = beanFactory.getBean("companyBean");
        System.out.println(companyBean1);

        Object companyBean2 = beanFactory.getBean("&companyBean");
        System.out.println(companyBean2);
    }
}
