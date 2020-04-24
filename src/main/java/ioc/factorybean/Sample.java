package ioc.factorybean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.support.*;

public class Sample {
    /**
     * factorybean获取实例的地方
     * {@link AbstractBeanFactory#getObjectForBeanInstance}
     */
    @Test
    public void test() {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object companyBean1 = beanFactory.getBean("companyBean");
        System.out.println(companyBean1);

        Object companyBean2 = beanFactory.getBean("&companyBean");
        System.out.println(companyBean2);
    }
}
