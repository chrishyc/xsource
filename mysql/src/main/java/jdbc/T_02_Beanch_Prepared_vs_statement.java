package jdbc;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.PrintWriter;
import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 *
 # Run complete. Total time: 00:07:49
 
 Benchmark                                            (thread)  Mode  Cnt      Score      Error  Units
 T_02_Beanch_Prepared_vs_statement.prepare_statement        10  avgt    5  11563.664 ± 7736.131  ms/op
 T_02_Beanch_Prepared_vs_statement.statement                10  avgt    5   9667.935 ± 6354.478  ms/op
 */
@State(value = Scope.Benchmark)
public class T_02_Beanch_Prepared_vs_statement {
    
    @Param(value = {"10"})
    private int thread;
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(T_02_Beanch_Prepared_vs_statement.class.getSimpleName())
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
    public void statement() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "00000000");
        String id = "100";
        String sql = "update test set q4 = 33333  where id =" + id;
        Statement stat = con.createStatement();
        for (int i = 0; i < 10000; i++) {
            stat.executeUpdate(sql);
        }
        stat.close();
        con.close();
    }
    
    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void prepare_statement() throws SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useServerPrepStmts=true&cachePrepStmts=true", "root", "00000000");
        String sql = "update test set q4 = 33333  where id = ?";
        PreparedStatement stat = con.prepareStatement(sql);
        for (int i = 0; i < 10000; i++) {
            stat.setInt(1, 100);
            stat.executeUpdate();
        }
        stat.close();
        con.close();
    }
}
