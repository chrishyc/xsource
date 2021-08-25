package concurrent;

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

@State(value = Scope.Benchmark)
public class T07_L1_WriteCombining_L2 {

  @Param(value = {"10"})
  private int thread;

  private static final int ITERATIONS = 10000;
  private static final int ITEMS = 1 << 24;
  private static final int MASK = ITEMS - 1;

  private static final byte[] arrayA = new byte[ITEMS];
  private static final byte[] arrayB = new byte[ITEMS];
  private static final byte[] arrayC = new byte[ITEMS];
  private static final byte[] arrayD = new byte[ITEMS];
  private static final byte[] arrayE = new byte[ITEMS];
  private static final byte[] arrayF = new byte[ITEMS];

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(T07_L1_WriteCombining_L2.class.getSimpleName())
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
  public void runMoreThanWCBufferM() {
    runMoreThanWCBuffer();
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void runLessThanWCBufferM() {
    runLessThanWCBuffer();
  }

  public static long runMoreThanWCBuffer() {
    long start = System.nanoTime();
    int i = ITERATIONS;

    while (--i != 0) {
      int slot = i & MASK;
      byte b = (byte) i;
      arrayA[slot] = b;
      arrayB[slot] = b;
      arrayC[slot] = b;
      arrayD[slot] = b;
      arrayE[slot] = b;
      arrayF[slot] = b;
    }
    return System.nanoTime() - start;
  }

  public static long runLessThanWCBuffer() {
    long start = System.nanoTime();
    int i = ITERATIONS;
    while (--i != 0) {
      int slot = i & MASK;
      byte b = (byte) i;
      arrayA[slot] = b;
      arrayB[slot] = b;
      arrayC[slot] = b;
    }
    i = ITERATIONS;
    while (--i != 0) {
      int slot = i & MASK;
      byte b = (byte) i;
      arrayD[slot] = b;
      arrayE[slot] = b;
      arrayF[slot] = b;
    }
    return System.nanoTime() - start;
  }
}
