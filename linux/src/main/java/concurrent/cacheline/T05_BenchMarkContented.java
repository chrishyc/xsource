package concurrent.cacheline;

import sun.misc.Contended;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
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
 * Benchmark                          (thread)  Mode  Cnt  Score   Error  Units
 * T05_BenchMarkContented.Falseshare        10  avgt    5  7.710 ± 7.388  ms/op
 * T05_BenchMarkContented.contended         10  avgt    5  3.579 ± 1.053  ms/op
 */
@State(value = Scope.Benchmark)
public class T05_BenchMarkContented {


  @Contended
  volatile long a;

  @Contended
  volatile long b;

  volatile long c;

  volatile long d;

  @Param(value = {"10"})
  private int thread;

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(T05_BenchMarkContented.class.getSimpleName())
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
  public void contended() {
    for (int i = 0; i < 10_0000; i++) {
      a = i;
      b = i + 1;
    }
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void Falseshare() {
    for (int i = 0; i < 10_0000; i++) {
      c = i;
      d = i + 1;
    }
  }
}
