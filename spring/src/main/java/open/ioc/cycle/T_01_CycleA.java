package open.ioc.cycle;

import org.springframework.stereotype.Component;
import mine.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Component
public class T_01_CycleA implements InitializingBean, ApplicationContextAware {
    @Autowired
    private T_01_CycleB testBean;

    public void setTestBean(T_01_CycleB testBean) {
        this.testBean = testBean;
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
