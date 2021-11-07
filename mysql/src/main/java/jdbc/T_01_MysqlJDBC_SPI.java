package jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class T_01_MysqlJDBC_SPI {
    public static void main(String[] args) throws SQLException {
        
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?logger=com.mysql.cj.log.StandardLogger&profileSQL=true", "root", "");
        //5、获取执行sql语句的对象
        Statement stat = con.createStatement();
        for (int i = 0; i < 10000; i++) {
            //4、定义sql语句
            String sql = "insert IGNORE into  s2(key1,key2,key3,key_part1,key_part2,key_part3,common_field) " +
                    "values(substring(md5(rand()), 1, 10)," +
                    "rand()* 100000," +
                    "substring(md5(rand()), 1, 10)," +
                    "substring(md5(rand()), 1, 10)," +
                    "substring(md5(rand()), 1, 10)," +
                    "substring(md5(rand()), 1, 10)," +
                    "substring(md5(rand()), 1, 10))";
            
            //6、执行sql并接收返回结果
            int count = stat.executeUpdate(sql);
        }
        
        
        //8、释放资源
        stat.close();
        con.close();
    }
    
    public long countVowels(String word) {
        int[] dp = new int[word.length()];
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('u');
        set.add('o');
        long count = 0;
        for (int i = 0; i < word.length(); i++) {
            dp[i] = set.contains(word.charAt(i)) ? 1 : 0;
            count += dp[i];
        }
        
        for (int i = 2; i <= word.length(); i++) {
            for (int j = word.length() - i; j >= 0; j--) {
                dp[j + i - 1] = dp[j + i - 2] + (set.contains(word.charAt(j + i - 1)) ? 1 : 0);
                count += dp[j + i - 1];
            }
        }
        return count;
    }
    
}
