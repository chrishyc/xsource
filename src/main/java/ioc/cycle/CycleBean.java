package ioc.cycle;

import demo.spring.annotation.Autowired;
import demo.spring.annotation.Component;

@Component
public class CycleBean {
    @Autowired
    private TestBean testBean;

    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }
}
