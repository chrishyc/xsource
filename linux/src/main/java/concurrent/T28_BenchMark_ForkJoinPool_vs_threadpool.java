/**
 * 认识Executor
 */
package concurrent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(value = Scope.Benchmark)
public class T28_BenchMark_ForkJoinPool_vs_threadpool {

  @Param(value = {"10"})
  private int thread;
  int length = 100_000_000;
  long[] arr = new long[length];

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(T28_BenchMark_ForkJoinPool_vs_threadpool.class.getSimpleName())
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

  @Setup
  public void init() {
    // 构造数据
    for (int i = 0; i < length; i++) {
      arr[i] = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
    }
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void singleThread() {
    singleThreadSum(arr);
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void threadPool() throws ExecutionException, InterruptedException {
    multiThreadSum(arr);
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void forkJoinPool() throws ExecutionException, InterruptedException {
    forkJoinSum(arr);
  }

  private static void singleThreadSum(long[] arr) {
    long sum = 0;
    for (int i = 0; i < arr.length; i++) {
      sum += (arr[i] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3);
    }
  }

  private static void multiThreadSum(long[] arr) throws ExecutionException, InterruptedException {
    int count = 8;
    ExecutorService threadPool = Executors.newFixedThreadPool(count);
    List<Future<Long>> list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      int num = i;
      // 分段提交任务
      Future<Long> future = threadPool.submit(() -> {
        long sum = 0;
        for (int j = arr.length / count * num; j < (arr.length / count * (num + 1)); j++) {
          try {
            // 模拟耗时
            sum += (arr[j] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        return sum;
      });
      list.add(future);
    }

    // 每个段结果相加
    long sum = 0;
    for (Future<Long> future : list) {
      sum += future.get();
    }
  }

  private static void forkJoinSum(long[] arr) throws ExecutionException, InterruptedException {
    ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    // 提交任务
    ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(new SumTask(arr, 0, arr.length));
    // 获取结果
    Long sum = forkJoinTask.get();
  }

  private static class SumTask extends RecursiveTask<Long> {
    private long[] arr;
    private int from;
    private int to;

    public SumTask(long[] arr, int from, int to) {
      this.arr = arr;
      this.from = from;
      this.to = to;
    }

    @Override
    protected Long compute() {
      // 小于1000的时候直接相加，可灵活调整
      if (to - from <= 1000) {
        long sum = 0;
        for (int i = from; i < to; i++) {
          // 模拟耗时
          sum += (arr[i] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3);
        }
        return sum;
      }

      // 分成两段任务，本文由公从号“彤哥读源码”原创
      int middle = (from + to) / 2;
      SumTask left = new SumTask(arr, from, middle);
      SumTask right = new SumTask(arr, middle, to);

      // 提交左边的任务
      left.fork();
      // 右边的任务直接利用当前线程计算，节约开销
      Long rightResult = right.compute();
      // 等待左边计算完毕
      Long leftResult = left.join();
      // 返回结果
      return leftResult + rightResult;
    }
  }
}

