package demo.spring;

import org.junit.Test;
import demo.spring.dao.impl.JdbcAccountDaoImpl;
import demo.spring.factory.BeanFactory;
import demo.spring.utils.ConnectionUtils;
import demo.spring.utils.DruidUtils;

public class Sample {
    @Test
    public void testMysql() {
        DruidUtils.getInstance();
    }

    @Test
    public void testSql() throws Exception {
        JdbcAccountDaoImpl accountDao = new JdbcAccountDaoImpl();
        accountDao.setConnectionUtils(new ConnectionUtils());
        System.out.println(accountDao.queryAccountByCardNo("6029621011001"));
    }

    @Test
    public void testBeanFactory() {
        BeanFactory.getBean("accountDao");
    }

}
