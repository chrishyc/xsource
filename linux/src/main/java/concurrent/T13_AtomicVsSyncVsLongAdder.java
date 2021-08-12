package concurrent;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * strace -c  java T13_AtomicVsSyncVsLongAdder
 * Atomic: 100000000 time 1442
 * Sync: 100000000 time 2313
 * LongAdder: 100000000 time 853
 * % time     seconds  usecs/call     calls    errors syscall
 * ------ ----------- ----------- --------- --------- ----------------
 * 99.79    0.980881      326960         3           futex
 * 0.11    0.001092        1092         1           execve
 * 0.03    0.000250           5        45           mmap
 * 0.02    0.000242           5        41        29 openat
 * 0.01    0.000105           6        16           mprotect
 * 0.01    0.000078           3        24        21 stat
 * 0.01    0.000058          29         2           readlink
 * 0.01    0.000052          52         1           clone
 * 0.00    0.000035           3        11           read
 * 0.00    0.000032           2        12           close
 * 0.00    0.000027           2        12           fstat
 * 0.00    0.000022           2         8           pread64
 * 0.00    0.000021           7         3           munmap
 * 0.00    0.000011           5         2         1 access
 * 0.00    0.000008           2         3           brk
 * 0.00    0.000004           2         2           rt_sigaction
 * 0.00    0.000004           2         2         1 arch_prctl
 * 0.00    0.000002           2         1           rt_sigprocmask
 * 0.00    0.000002           1         2           getpid
 * 0.00    0.000002           2         1           set_tid_address
 * 0.00    0.000002           2         1           set_robust_list
 * 0.00    0.000002           2         1           prlimit64
 * ------ ----------- ----------- --------- --------- ----------------
 * 100.00    0.982932                   194        52 total
 * <p>
 * strace统计系统调用:https://blog.csdn.net/erlang_hell/article/details/51392067
 */
public class T13_AtomicVsSyncVsLongAdder {
  static long count2 = 0L;
  static AtomicLong count1 = new AtomicLong(0L);
  static LongAdder count3 = new LongAdder();

  /**
   * thread id:1
   * concurrent.T13_AtomicVsSyncVsLongAdder$Cell object internals:
   * OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
   * 0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
   * 4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
   * 8     4        (object header)                           c3 f2 00 f8 (11000011 11110010 00000000 11111000) (-134155581)
   * 12     4        (alignment/padding gap)
   * 16     8   long Cell.value                                1
   * Instance size: 24 bytes
   * Space losses: 4 bytes internal + 0 bytes external = 4 bytes total
   */
  @sun.misc.Contended
  static final class Cell {
    volatile long value;
    public static final long a = 1;
    public static final long b = 2;

    Cell(long x) {
      value = x;
    }
  }

  public static void main(String[] args) throws Exception {
    Thread[] threads = new Thread[1000];
    System.out.println(ClassLayout.parseInstance(count3).toPrintable() + "\n" + "thread id:" + Thread.currentThread().getId());

    Cell cell = new Cell(1);
    System.out.println(ClassLayout.parseInstance(cell).toPrintable() + "\n" + "thread id:" + Thread.currentThread().getId());


    for (int i = 0; i < threads.length; i++) {
      threads[i] =
          new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
              count1.incrementAndGet();
//                            System.out.println("count1:" + count1.get());
            }
          });
    }

    long start = System.currentTimeMillis();

    for (Thread t : threads) t.start();

    for (Thread t : threads) t.join();

    long end = System.currentTimeMillis();

    //TimeUnit.SECONDS.sleep(10);

    System.out.println("Atomic: " + count1.get() + " time " + (end - start));
    //-----------------------------------------------------------
    Object lock = new Object();

    for (int i = 0; i < threads.length; i++) {
      threads[i] =
          new Thread(new Runnable() {
            @Override
            public void run() {

              for (int k = 0; k < 100000; k++)
                synchronized (lock) {
                  count2++;
//                                    System.out.println("count2:" + count2);
                }
            }
          });
    }

    start = System.currentTimeMillis();

    for (Thread t : threads) t.start();

    for (Thread t : threads) t.join();

    end = System.currentTimeMillis();


    System.out.println("Sync: " + count2 + " time " + (end - start));


    //----------------------------------
    for (int i = 0; i < threads.length; i++) {
      threads[i] =
          new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
              count3.increment();
//                            System.out.println("count3:" + count3.sum());
            }
          });
    }

    start = System.currentTimeMillis();

    for (Thread t : threads) t.start();

    for (Thread t : threads) t.join();

    end = System.currentTimeMillis();

    //TimeUnit.SECONDS.sleep(10);

    System.out.println("LongAdder: " + count1.longValue() + " time " + (end - start));

  }

  static void microSleep(int m) {
    try {
      TimeUnit.MICROSECONDS.sleep(m);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
