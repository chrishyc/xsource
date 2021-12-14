package web.spring.open.ioc.cycle;

import org.springframework.stereotype.Component;
import web.spring.mine.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Component
public class CycleBean implements InitializingBean, ApplicationContextAware {
    @Autowired
    private TestBean testBean;

    public void setTestBean(TestBean testBean) {
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
