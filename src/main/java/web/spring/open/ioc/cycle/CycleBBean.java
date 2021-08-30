package web.spring.open.ioc.cycle;

import ioc.cycle.CycleABean;
import web.spring.mine.annotation.Autowired;
import web.spring.mine.annotation.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Component
public class CycleBBean implements InitializingBean, ApplicationContextAware {
    @Autowired
    private CycleABean cycleABean;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CycleBean afterPropertiesSet...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("CycleBean setApplicationContext...");
    }
}
