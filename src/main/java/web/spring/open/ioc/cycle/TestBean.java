package web.spring.open.ioc.cycle;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Data
@Lazy
@Service
public class TestBean implements InitializingBean, ApplicationContextAware {
    @Autowired
    private CycleBean cycleBean;
    
    @Value("${spring.profiles.active}")
    private String name;
    
    private String mifi_namespace = "mifi:";
    
    public void setCycleBean(CycleBean cycleBean) {
        this.cycleBean = cycleBean;
    }
    
    public TestBean() {
        System.out.println("TestBean construct finish");
    }
    
    /**
     * 之前PostConstruct未生效,PBeanPostProcessor.postProcessBeforeInitialization返回Null
     * 导致InitDestroyAnnotationBeanPostProcessor未处理
     */
    @PostConstruct
    public void init() {
        if (name.equalsIgnoreCase("CHRIS")) {
            mifi_namespace = "chris:";
        }
        System.out.println("mifi_namespace:" + mifi_namespace);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (name.contains("CHRIS")) System.out.println("mifi_namespace:" + mifi_namespace);
        System.out.println("TestBean afterPropertiesSet...");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("TestBean setApplicationContext...");
    }
}
