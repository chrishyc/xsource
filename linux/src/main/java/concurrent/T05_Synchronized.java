/**
 *
 */

package concurrent;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * javac /Users/chris/workspace/xsource/linux/src/main/java/concurrent/T05_Synchronized.java
 * 查看字节码指令:javap -v /Users/chris/workspace/xsource/linux/src/main/java/concurrent/T05_Synchronized.class
 *
 * 查看对象布局:java -jar /Users/chris/workspace/xsource/linux/src/main/resources/jol-cli.jar    internals java.util.HashMap
 * 锁偏移:https://www.cnblogs.com/LemonFive/p/11246086.html
 *
 *
 *
 * OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
 *       0     4                    (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 * 输出的第一行内容和锁状态内容对应
 * unused:1 | age:4 | biased_lock:1 | lock:2
 *      0           0000             0                01     代表A对象正处于无锁状态
 *
 *
 * 0 01 无锁
 * 1 01 偏向锁
 * 0 00 轻量级锁
 * 0 10 重量级锁
 * 0 11 GC标记
 *
 */
public class T05_Synchronized {
    String name;
    double balance;
    
    /**
     * flags: ACC_PUBLIC, ACC_SYNCHRONIZED
     * @param name
     * @param balance
     */
    public synchronized void set(String name, double balance) {
        System.out.println(ClassLayout.parseInstance(this).toPrintable() + "\n" + "thread id:" + Thread.currentThread().getId());
        System.out.println("=========================================");
        this.name = name;

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        this.balance = balance;
    }
    
    public static synchronized void set(double balance) {
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * public void set(java.lang.String);
     *     descriptor: (Ljava/lang/String;)V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=3, locals=5, args_size=2
     *          0: ldc           #9                  // class concurrent/T05_Synchronized
     *          2: dup
     *          3: astore_2
     *          4: monitorenter
     *          5: aload_0
     *          6: aload_1
     *          7: putfield      #2                  // Field name:Ljava/lang/String;
     *         10: ldc2_w        #3                  // long 2000l
     *         13: invokestatic  #5                  // Method java/lang/Thread.sleep:(J)V
     *         16: goto          24
     *         19: astore_3
     *         20: aload_3
     *         21: invokevirtual #7                  // Method java/lang/InterruptedException.printStackTrace:()V
     *         24: aload_0
     *         25: aload_0
     *         26: getfield      #8                  // Field balance:D
     *         29: putfield      #8                  // Field balance:D
     *         32: aload_2
     *         **33: monitorexit**
     *         34: goto          44
     *         37: astore        4
     *         39: aload_2
     *         **40: monitorexit**
     *         41: aload         4
     *         43: athrow
     *         44: return
     * @param name
     */
    public void set(String name) {
        synchronized (T05_Synchronized.class) {
            this.name = name;
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.balance = balance;
        }
    }
    
    public void set(String name, String age) {
        synchronized (this) {
            this.name = name;
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.balance = balance;
        }
    }
    
    public /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }
    
    
    public static void main(String[] args) {
        T05_Synchronized a = new T05_Synchronized();
        System.out.println(ClassLayout.parseInstance(a).toPrintable() + "\n" + "thread id:" + Thread.currentThread().getId());
        System.out.println("=========================================");
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        a.set("zhangsan", 100.0);
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(a.getBalance("zhangsan"));
        
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(a.getBalance("zhangsan"));
    }
}
