package jdbc;

import org.junit.Test;

import java.io.PrintWriter;
import java.sql.*;

public class T_02_jdbc_SQLInject_Prepare_client_server_cache {
    @Test
    public void inject() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?logger=com.mysql.cj.log.Slf4JLogger&profileSQL=true", "root", "00000000");
        
        // 注入
        String id = "100 or 1=1";
        String sql = "update test set q4 = 33333  where id =" + id;
        
        //注入
        String id1 = "'100'#'";
        String sql1 = "update test set q4 = 33333  where id =" + id1;
        
        //5、获取执行sql语句的对象
        Statement stat = con.createStatement();
        
        //6、执行sql并接收返回结果
        int count = stat.executeUpdate(sql);
        int count1 = stat.executeUpdate(sql1);
        
        //7、处理结果
        System.out.println(count);
        
        //8、释放资源
        stat.close();
        con.close();
    }
    
    /**
     * ClientPreparedStatement.isEscapeNeededForString
     *
     * @throws SQLException
     */
    @Test
    public void clientPreparedStatement() throws SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?logger=com.mysql.cj.log.Slf4JLogger&profileSQL=true", "root", "00000000");
        
        String id = "100'1";
        String sql = "update test set q4 = 444444  where id = ?";
        
        //5、获取执行sql语句的对象
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, id);
        //6、执行sql并接收返回结果
        int count = stat.executeUpdate();
        stat.close();
        
        String id1 = "100'#";
        String sql1 = "update test set q4 = 444444  where id = ?";
        stat = con.prepareStatement(sql1);
        stat.setString(1, id1);
        int count1 = stat.executeUpdate();
        //7、处理结果
        System.out.println(count);
        
        //8、释放资源
        stat.close();
        con.close();
    }
    
    /**
     * ConnectionImpl.prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
     * (服务端预编译&客户端预编译逻辑)
     * 开启useServerPrepStmts服务端预编译
     *
     * @throws SQLException
     */
    @Test
    public void serverPreparedStatement() throws SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?logger=com.mysql.cj.log.Slf4JLogger&profileSQL=true&useServerPrepStmts=true", "root", "00000000");
        
        // 注入
//        String id = "100 or 1=1";
        String sql = "update test set q4 = 444444  where id = ?";
        
        //5、获取执行sql语句的对象
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, 100);
        //6、执行sql并接收返回结果
        int count = stat.executeUpdate();
        stat.close();
        stat = con.prepareStatement(sql);
        stat.setInt(1, 102);
        int count1 = stat.executeUpdate();
        //7、处理结果
        System.out.println(count);
        
        //8、释放资源
        stat.close();
        con.close();
    }
    
    /**
     * 开启服务端预编译useServerPrepStmts
     * 开启服务端预编译缓存cachePrepStmts
     *
     * @throws SQLException
     */
    @Test
    public void serverCachePreparedStatement() throws SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?logger=com.mysql.cj.log.Slf4JLogger&profileSQL=true&useServerPrepStmts=true&cachePrepStmts=true", "root", "00000000");
        
        // 注入
        String sql = "update test set q4 = 444444  where id = ?";
        
        //5、获取执行sql语句的对象
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, 100);
        //6、执行sql并接收返回结果
        int count = stat.executeUpdate();
        stat.close();
        stat = con.prepareStatement(sql);
        stat.setInt(1, 102);
        int count1 = stat.executeUpdate();
        //7、处理结果
        System.out.println(count);
        
        //8、释放资源
        stat.close();
        con.close();
    }
    
}
