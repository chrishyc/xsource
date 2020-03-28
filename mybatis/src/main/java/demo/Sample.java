package demo;

import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.Properties;

public class Sample {
    public static void main(String[] args) throws SQLException {
//        SQLDao proxy = (SQLDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//                new Class[]{SQLDao.class}, new MyInvokeHandler());
//        System.out.println(proxy.getCount());
        testStatement();
//        queryProperty();
    }

    public static void queryProperty() {
        Properties properties = System.getProperties();
        String jdbc = properties.getProperty("jdbc.properties");
        System.out.println(jdbc);
    }

    /**
     * 通过查看jdbc.properties自动加载驱动
     */
    public static void testStatement() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?characterEncoding=utf-8", "root", "00000000");
            String sql = "select * from chris";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("age");
                String username = resultSet.getString("name");
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                System.out.println(user);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {

                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
