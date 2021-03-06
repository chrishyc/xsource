package ioc.cycle;

import demo.spring.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//@Data
//@Lazy
@Component
public class TestBean implements InitializingBean, ApplicationContextAware {
    @Autowired
    private CycleBean cycleBean;

    public void setCycleBean(CycleBean cycleBean) {
        this.cycleBean = cycleBean;
    }

    public TestBean() {
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
