package open.ioc.cycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class T_Prototype implements InitializingBean, ApplicationContextAware {
    
    public T_Prototype() {
        System.out.println("TestBean construct finish");
    }
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestBean afterPropertiesSet...");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("TestBean setApplicationContext...");
    }
}
