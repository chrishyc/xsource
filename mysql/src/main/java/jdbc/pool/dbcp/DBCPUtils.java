package jdbc.pool.dbcp;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class DBCPUtils {
    private static BasicDataSource dataSource;
    
    /**
     * java    31599 chris   67u  IPv6 0xb6d5e2c597087dd      0t0  TCP localhost:50032->localhost:mysql (ESTABLISHED)
     * java    31599 chris   78u  IPv6 0xb6d5e2c59707b1d      0t0  TCP localhost:50033->localhost:mysql (ESTABLISHED)
     * java    31599 chris   79u  IPv6 0xb6d5e2c5970817d      0t0  TCP localhost:50034->localhost:mysql (ESTABLISHED)
     * java    31599 chris   80u  IPv6 0xb6d5e2c5df9849d      0t0  TCP localhost:50035->localhost:mysql (ESTABLISHED)
     * java    31599 chris   81u  IPv6 0xb6d5e2c5df9717d      0t0  TCP localhost:50037->localhost:mysql (ESTABLISHED)
     * java    31599 chris   82u  IPv6 0xb6d5e2c5df977dd      0t0  TCP localhost:50038->localhost:mysql (ESTABLISHED)
     * java    31599 chris   83u  IPv6 0xb6d5e2c5d8bd7dd      0t0  TCP localhost:50039->localhost:mysql (ESTABLISHED)
     * java    31599 chris   84u  IPv6 0xb6d5e2c5d8bd17d      0t0  TCP localhost:50040->localhost:mysql (ESTABLISHED)
     * java    31599 chris   85u  IPv6 0xb6d5e2c5d8bcb1d      0t0  TCP localhost:50041->localhost:mysql (ESTABLISHED)
     * java    31599 chris   86u  IPv6 0xb6d5e2c5d8bde3d      0t0  TCP localhost:50042->localhost:mysql (ESTABLISHED)
     */
    static {
        dataSource = new BasicDataSource();
        //基本设置
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql");
        dataSource.setUsername("root");
        dataSource.setPassword("00000000");
        //高级设置
        dataSource.setInitialSize(10);//初始化连接
        dataSource.setMinIdle(5);//最小空闲连接
        dataSource.setMaxIdle(20);//最大空闲连接
        dataSource.setMaxActive(50);//最大连接数量
        dataSource.setMaxWait(1000);//超时等待时间以毫秒为单位
    }
    
    /**
     * 获取DataSource对象
     *
     * @return
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}
