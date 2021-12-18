package open.ioc.cycle;

import mine.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class T_02_CycleA implements InitializingBean, ApplicationContextAware {
    @Autowired
    private T_01_CycleB testBean;

    public void T_02_CycleA(int name) {
    
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CycleBean afterPropertiesSet...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("CycleBean setApplicationContext...");
    }
}
