package concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * http://deepoove.com/jmh-visual-chart/
 * <p>
 * https://dafengge0913.github.io/jmh/
 *
 * 2/8:
 * 1.测试结果查看
 *   Mode: thrpt代表吞吐量，avgt代表平均运行时间
 *   Cnt：iteration组数
 *   Score：对应Units的值
 *   Units：统计的单位
 *   Error：误差
 * 2.主要概念
 *   Iteration:最小单位，包含一组invocations
 *   Invocation：一次benchmark方法调用
 *   Operation：如果一次benchmark方法调用包含循环,Operation为循环中的一次操作
 *   Warmup:进行预热,使得多次执行后代码已转化为本地代码,避免解释执行
 *
 * <p>
 * Benchmark                         (thread)  Mode  Cnt  Score   Error  Units
 * T26_BenchMarkTest_hash.hashMap          10  avgt   10  0.009 ± 0.003  ms/op
 * T26_BenchMarkTest_hash.hashTable        10  avgt   10  0.019 ± 0.039  ms/op
 */
@State(value = Scope.Benchmark)
public class T26_BenchMarkTest_hash {
    
    @Param(value = {"10"})
    private int thread;
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(T26_BenchMarkTest_hash.class.getSimpleName())
                .warmupIterations(5)// 预热5轮
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(10)// 度量5轮，总共测试5轮来度量性能
                .forks(1)
                .threads(10)
                .result("result.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
    
    /**
     * Result "concurrent.T26_BenchMarkTest_hash.hashMap":
     * 0.009 ±(99.9%) 0.003 ms/op [Average]
     * (min, avg, max) = (0.007, 0.009, 0.014), stdev = 0.002
     * CI (99.9%): [0.006, 0.013] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void hashMap() {
        for (int i = 0; i < 150; i++) {
            String k = "chris" + i;
            int h = 0;
            h ^= k.hashCode();
            h ^= (h >>> 20) ^ (h >>> 12);
            h ^= h ^ (h >>> 7) ^ (h >>> 4);
            int ret = h & (16 - 1);
        }
    }
    
    /**
     * Result "concurrent.T26_BenchMarkTest_hash.hashTable":
     * 0.019 ±(99.9%) 0.039 ms/op [Average]
     * (min, avg, max) = (0.008, 0.019, 0.092), stdev = 0.026
     * CI (99.9%): [≈ 0, 0.059] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void hashTable() {
        for (int i = 0; i < 150; i++) {
            String k = "chris" + i;
            int hash = k.hashCode();
            int tableLen = 11;
            int h = (hash & 0x7FFFFFFF) % tableLen;
        }
    }
}
