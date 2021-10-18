package jdbc;

import jdbc.pool.mine.ConnectionPool;
import jdbc.pool.mine.ConnectionPoolUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * # Run complete. Total time: 00:01:55
 * <p>
 * Benchmark                   (thread)  Mode  Cnt     Score      Error  Units
 * ConnectionPoolTest.notPool        10  avgt    5  4196.965 ± 6874.238  ms/op
 * ConnectionPoolTest.pool           10  avgt    5    ≈ 10⁻³             ms/op
 * <p>
 * Benchmark result is saved to result.json
 */
@State(value = Scope.Benchmark)
public class T_03_Beanch_ConnectionPoolTest {
    
    @Param(value = {"10"})
    private int thread;
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(T_03_Beanch_ConnectionPoolTest.class.getSimpleName())
                .warmupIterations(3)// 预热3轮
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(5)// 度量5轮，总共测试5轮来度量性能
                .forks(1)
                .threads(10)
                .result("result.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
    
    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void pool() {
        try {
            ConnectionPool connPool = ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
            // SQL测试语句
            String sql = "Select * from test";
            // 循环测试100次数据库连接
            for (int i = 0; i < 100; i++) {
                Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
                connPool.returnConnection(conn);// 连接使用完后释放连接到连接池
            }
            connPool.closeConnectionPool();// 关闭数据库连接池。注意：这个耗时比较大。
        } catch (Exception e) {
        
        }
    }
    
    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void notPool() {
        /*不使用连接池创建100个连接的时间*/
        
        try {
            String sql = "Select * from test";
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < 100; i++) {
                // 创建连接
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mysql", "root", "00000000");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
                conn.close();// 关闭连接
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
