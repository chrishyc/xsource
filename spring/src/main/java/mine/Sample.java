package mine;

import mine.dao.impl.JdbcAccountDaoImpl;
import mine.factory.AnnotationBeanFactory;
import mine.factory.BeanFactory;
import mine.utils.ConnectionUtils;
import mine.utils.DruidUtils;
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
