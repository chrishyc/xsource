package ioc.cycle;

import demo.spring.annotation.Autowired;
import demo.spring.annotation.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Component
public class CycleABean implements InitializingBean, ApplicationContextAware {
    @Autowired
    private CycleBBean cycleBBean;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CycleBean afterPropertiesSet...");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("CycleBean setApplicationContext...");
    }
}
