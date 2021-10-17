package jdbc.pool.dbcp;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DBCPPool {
    public static void main(String[] args) throws SQLException {
        //1，得到dataSource对象，
        DataSource dataSource = DBCPUtils.getDataSource();
        //2，得到QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        //3，执行查询作sql
        queryRunner.update("update test set q4 = ? where id = ?", 111111, 2);
    }
    
}
