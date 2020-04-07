package demo.spring;

import demo.spring.dao.impl.JdbcAccountDaoImpl;
import demo.spring.factory.AnnotationBeanFactory;
import demo.spring.factory.BeanFactory;
import demo.spring.utils.ConnectionUtils;
import demo.spring.utils.DruidUtils;
import org.junit.Test;

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

    @Test
    public void testResource() {
        AnnotationBeanFactory factory = new AnnotationBeanFactory();
        System.out.println(factory);
    }

}
