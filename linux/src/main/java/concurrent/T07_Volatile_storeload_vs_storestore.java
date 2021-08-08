package concurrent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * storeload vs storestore: https://tech.meituan.com/2014/09/23/java-memory-reordering.html
 *
 *
 * Benchmark                                                         (thread)  Mode  Cnt   Score   Error  Units
 * T07_Volatile_storeload_vs_storestore.putOrderedObject_storestore        10  avgt    5  12.703 ± 1.374  ms/op
 * T07_Volatile_storeload_vs_storestore.volatile_storeload                 10  avgt    5  20.997 ± 3.537  ms/op
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(20)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class T07_Volatile_storeload_vs_storestore {

  private static T07_unsafe_putOrderedObject_memory_barrier_StoreStore storeStore = new T07_unsafe_putOrderedObject_memory_barrier_StoreStore();
  private static T07_Volatile_memory_barrier_StoreLoad storeLoad = new T07_Volatile_memory_barrier_StoreLoad();
  @Param(value = {"10"})
  private int thread;

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(T07_Volatile_storeload_vs_storestore.class.getSimpleName())
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
  public void putOrderedObject_storestore() {
    for (int i = 0; i < 100000; i++) {
      storeStore.create();
      storeStore.get().getStatus();
    }
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void volatile_storeload() {
    for (int i = 0; i < 100000; i++) {
      storeLoad.create();
      storeLoad.get().getStatus();
    }
  }
}
