package concurrent.cacheline;

import sun.misc.Contended;
import org.openjdk.jol.info.ClassLayout;

/**
 * jvm增加参数-XX:-RestrictContended
 * –XX:+PrintFieldLayout
 * 字段不能为static字段
 */
public class T05_CacheLinePadding {
    @Contended
     volatile long a;

    @Contended
     volatile long b;

    public static void main(String[] args) throws Exception {
        T05_CacheLinePadding t = new T05_CacheLinePadding();
        System.out.println(ClassLayout.parseInstance(t).toPrintable() + "\n" + "thread id:" + Thread.currentThread().getId());
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                t.a = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                t.b = i;
            }
        });
        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 100_0000);
    }
}
