package mine.dao.impl;


import mine.annotation.Autowired;
import mine.annotation.Component;
import mine.annotation.Service;
import mine.dao.AccountDao;
import mine.pojo.Account;
import mine.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author 应癫
 */
@Component("accountDao")
@Service("JdbcAccountDaoBean")
public class JdbcAccountDaoImpl implements AccountDao {

    /**
     * 原始方案
     */
//    private ConnectionUtils connectionUtils = new ConnectionUtils();
    @Autowired
    private ConnectionUtils connectionUtils;

    private List<Integer> list;

    public JdbcAccountDaoImpl() {

    }

    public JdbcAccountDaoImpl(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public JdbcAccountDaoImpl(List<Integer> list) {
        this.list = list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    /**
     * 控制反转，自动注入方法
     *
     * @param connectionUtils
     */

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }


    public void init() {
        System.out.println("初始化方法.....");
    }

    public void destory() {
        System.out.println("销毁方法......");
    }

    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        //从连接池获取连接
        // Connection con = DruidUtils.getInstance().getConnection();
        Connection con = connectionUtils.getCurrentThreadConn();
        String sql = "select * from account_test where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();
        while (resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }

        resultSet.close();
        preparedStatement.close();
        //con.close();

        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {

        // 从连接池获取连接
        // 改造为：从当前线程当中获取绑定的connection连接
        //Connection con = DruidUtils.getInstance().getConnection();
        Connection con = connectionUtils.getCurrentThreadConn();
        String sql = "update account_test set money=? where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, account.getMoney());
        preparedStatement.setString(2, account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        //con.close();
        return i;
    }
}
