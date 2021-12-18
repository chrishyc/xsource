package open.ioc.cycle;

import mine.annotation.Autowired;
import mine.annotation.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Component
public class T_01_CycleB implements InitializingBean, ApplicationContextAware {
    @Autowired
    private T_01_CycleA cycleABean;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CycleBean afterPropertiesSet...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("CycleBean setApplicationContext...");
    }
}
