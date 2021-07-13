package concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * http://deepoove.com/jmh-visual-chart/
 */
@State(value = Scope.Benchmark)
public class T14_BenchMarkTest4LongAdder {
    private static final LongAdder LONG_ADDER_VALUE = new LongAdder();
    private static final AtomicLong ATOMIC_LONG_VALUE = new AtomicLong(0);
    
    @Param(value = {"10"})
    private int thread;
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(T14_BenchMarkTest4LongAdder.class.getSimpleName())
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
    public void longAddrIncrementTest() {
        for (int i = 0; i < 100000; i++) {
            LONG_ADDER_VALUE.increment();
        }
    }
    
    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void atomicLongIncrementTest() {
        for (int i = 0; i < 100000; i++) {
            ATOMIC_LONG_VALUE.incrementAndGet();
        }
    }
}
