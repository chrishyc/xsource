package concurrent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.CommandLineOptionException;
import org.openjdk.jmh.runner.options.CommandLineOptions;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(20)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class T07_VolatileBarrierBench {

  volatile int sink;
  Integer src;

  private int c;

  @Param("0")
  private int backoff;

     /*
      The benchmark below is rather fragile:
        - we pass the object to non-inlinable method, and at least on Linux x86_64
          calling convention the argument gets to %(esp+0), which is important for this test;
        - looping() is non-inlinable to prevent loop unrolling effects;
        - consumeCPU allows for tunable backoffs;
        - unboxing value before entering the consumeCPU allows to split the *load* from %(esp),
          and the subsequent barrier;

      Use with great care, and mostly for producing profiled assemblies.
     */

  @Setup
  public void setup() throws NoSuchFieldException {
    src = 42;
  }

  @Benchmark
  public void testVolatile() {
    testWith_Volatile(src);
  }

  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void testWith_Volatile(Integer v1) {
    c = 0;
    do {
      int v = v1;
      Blackhole.consumeCPU(backoff);
      sink = v;
    } while (looping());
  }

  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  private boolean looping() {
    return c++ < 100;
  }

  public static void main(String[] args) throws RunnerException, CommandLineOptionException {
    final CommandLineOptions cmdLineOpts = new CommandLineOptions(args);

    final int cpus = Runtime.getRuntime().availableProcessors();
    SortedSet<Integer> threadList = new TreeSet<Integer>() {{
      add(1);
      add(cpus / 16);
      add(cpus / 8);
      add(cpus / 4);
      add(cpus / 2);
      add(cpus);
    }};
    threadList.remove(0);
    for (int threads : threadList) {
      for (String s : new String[]{"threads", "backoff", "mfence, [err]", "addl(esp), [err]", "addl(esp-8), [err]", "addl(esp-CL-8), [err]", "addl(esp-RZ-8), [err]"}) {
        System.out.printf("%-25s", s + ",");
      }
      System.out.println();

      for (int backoff = 0; backoff <= 256; backoff = Math.max(1, backoff * 2)) {
        System.out.printf("%-25s", String.valueOf(threads) + ",");
        System.out.printf("%-25s", String.valueOf(backoff) + ",");
        for (int type = 0; type < 5; type++) {
          Options opts = new OptionsBuilder()
              .include(T07_VolatileBarrierBench.class.getSimpleName())
              .detectJvmArgs()
              .parent(cmdLineOpts)
              .jvmArgsAppend("-XX:+UnlockExperimentalVMOptions", "-XX:StoreLoadBarrier=" + type)
              .param("backoff", String.valueOf(backoff))
              .threads(threads)
              .verbosity(VerboseMode.SILENT)
              .build();

          try {
            RunResult res = new Runner(opts).runSingle();
            System.out.printf("%-25s", String.format("%.0f, [%.0f],", res.getPrimaryResult().getScore(), res.getPrimaryResult().getScoreError()));
          } catch (RunnerException e) {
            System.out.printf("%-25s", String.format("%.0f, [%.0f],", Double.NaN, Double.NaN));
          }
        }
        System.out.println();
      }
      System.out.println();
    }

  }

}
