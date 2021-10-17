package jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlJDBC {
    public static void main(String[] args) throws SQLException {
        
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "00000000");
        
        //4、定义sql语句
        String sql = "update test set q4 = 13 where id = 2 ";
        
        //5、获取执行sql语句的对象
        Statement stat = con.createStatement();
        
        //6、执行sql并接收返回结果
        int count = stat.executeUpdate(sql);
        
        //7、处理结果
        System.out.println(count);
        
        //8、释放资源
        stat.close();
        con.close();
    }
    
}
