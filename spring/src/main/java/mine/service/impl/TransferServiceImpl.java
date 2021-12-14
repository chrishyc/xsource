package mine.service.impl;

import mine.annotation.Autowired;
import mine.annotation.Service;
import mine.annotation.Transactional;
import mine.dao.AccountDao;
import mine.pojo.Account;
import mine.service.TransferService;

/**
 * @author 应癫
 */
@Service("transferServiceBean")
public class TransferServiceImpl implements TransferService {

    /**
     * 原始方案
     */
//    private AccountDao accountDao = new JdbcAccountDaoImpl();

    /**
     * 控制反转方案
     * @param accountDao
     */
//     private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    /**
     * 控制反转，且自动注入，
     */
    @Autowired
//    @Qualifier(value = "accountDao")
//    @Resource(type = JdbcAccountDaoImpl.class)
    private AccountDao accountDao;

    /**
     * 有set**方法，默认会自动注入
     */
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Transactional
    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        /*try{
            // 开启事务(关闭事务的自动提交)
            TransactionManager.getInstance().beginTransaction();*/

        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(to);
//            int c = 1/0;
        accountDao.updateAccountByCardNo(from);

        /*    // 提交事务

            TransactionManager.getInstance().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            TransactionManager.getInstance().rollback();

            // 抛出异常便于上层servlet捕获
            throw e;

        }*/
    }
}
