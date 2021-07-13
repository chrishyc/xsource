package concurrent;

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
 *  99.79    0.980881      326960         3           futex
 *   0.11    0.001092        1092         1           execve
 *   0.03    0.000250           5        45           mmap
 *   0.02    0.000242           5        41        29 openat
 *   0.01    0.000105           6        16           mprotect
 *   0.01    0.000078           3        24        21 stat
 *   0.01    0.000058          29         2           readlink
 *   0.01    0.000052          52         1           clone
 *   0.00    0.000035           3        11           read
 *   0.00    0.000032           2        12           close
 *   0.00    0.000027           2        12           fstat
 *   0.00    0.000022           2         8           pread64
 *   0.00    0.000021           7         3           munmap
 *   0.00    0.000011           5         2         1 access
 *   0.00    0.000008           2         3           brk
 *   0.00    0.000004           2         2           rt_sigaction
 *   0.00    0.000004           2         2         1 arch_prctl
 *   0.00    0.000002           2         1           rt_sigprocmask
 *   0.00    0.000002           1         2           getpid
 *   0.00    0.000002           2         1           set_tid_address
 *   0.00    0.000002           2         1           set_robust_list
 *   0.00    0.000002           2         1           prlimit64
 * ------ ----------- ----------- --------- --------- ----------------
 * 100.00    0.982932                   194        52 total
 *
 * strace统计系统调用:https://blog.csdn.net/erlang_hell/article/details/51392067
 */
public class T13_AtomicVsSyncVsLongAdder {
    static long count2 = 0L;
    static AtomicLong count1 = new AtomicLong(0L);
    static LongAdder count3 = new LongAdder();
    
    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[1000];
        
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
                            System.out.println("count3:" + count3.sum());
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
