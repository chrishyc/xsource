package spring;

import org.junit.Test;
import spring.utils.DruidUtils;

public class Sample {
    @Test
    public void testMysql() {
        DruidUtils.getInstance();
    }

}
