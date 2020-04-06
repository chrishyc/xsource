package ioc;

public class CycleBean {
    private TestBean testBean;

    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }
}
