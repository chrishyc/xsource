package mine.utils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 应癫
 */
public class DruidUtils {

    private DruidUtils(){
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();


    static {
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql:///mysql");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("00000000");
        druidDataSource.setMaxWait(5000);

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
