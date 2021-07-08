package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T04_ThreadStatus {
    public static void main(String[] args) throws InterruptedException {
        /**
         * "main" #1 prio=5 os_prio=31 tid=0x00007fc41e00a800 nid=0x1003 waiting on condition [0x000070000b218000]
         *    java.lang.Thread.State: TIMED_WAITING (sleeping)
         * 	at java.lang.Thread.sleep(Native Method)
         * 	at concurrent.MyThread.main(MyThread.java:5)
         */
//        Thread.sleep(10000);
        /**
         * "main" #1 prio=5 os_prio=31 tid=0x00007fb6e700a800 nid=0xb03 runnable [0x0000700006260000]
         *    java.lang.Thread.State: RUNNABLE
         * 	at concurrent.MyThread.main(MyThread.java:12)
         *
         *    Locked ownable synchronizers:
         * 	- None
         */
//        for (long i = 0; i < 100000_00000_0000L; i++) {
//            Object o = new Object();
//        }
        
        /**
         * "main" #1 prio=5 os_prio=31 tid=0x00007f930300d000 nid=0xe03 waiting on condition [0x000070000a960000]
         *    java.lang.Thread.State: TIMED_WAITING (sleeping)
         * 	at java.lang.Thread.sleep(Native Method)
         * 	at concurrent.MyThread.main(MyThread.java:25)
         * 	- locked <0x00000007956a97f0> (a java.lang.Class for concurrent.MyThread)
         *
         *    Locked ownable synchronizers:
         * 	- None
         */
//        synchronized (MyThread.class) {
//            Thread.sleep(10000);
//            System.out.println("hello");
//        }
        /**
         * "main" #1 prio=5 os_prio=31 tid=0x00007ffb9080b000 nid=0xe03 runnable [0x0000700001dd6000]
         *    java.lang.Thread.State: RUNNABLE
         * 	at concurrent.MyThread.main(MyThread.java:40)
         * 	- locked <0x000000079761c218> (a java.lang.Class for concurrent.MyThread)
         */
//        synchronized (MyThread.class) {
//            for (long i = 0; i < 100000_00000_0000L; i++) {
//                System.out.println("hello");
//            }
//        }
        /**
         * "pool-1-thread-2" #11 prio=5 os_prio=31 tid=0x00007fc003114000 nid=0x4303 waiting on condition [0x00007000046bf000]
         *    java.lang.Thread.State: WAITING (parking)
         * 	at sun.misc.Unsafe.park(Native Method)
         * 	- parking to wait for  <0x00000007956c3cd8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
         * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
         * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
         * 	at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
         * 	at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         *
         *    Locked ownable synchronizers:
         * 	- None
         *
         * "pool-1-thread-1" #10 prio=5 os_prio=31 tid=0x00007fc00390c000 nid=0x4503 waiting on condition [0x00007000045bc000]
         *    java.lang.Thread.State: WAITING (parking)
         * 	at sun.misc.Unsafe.park(Native Method)
         * 	- parking to wait for  <0x00000007956c3cd8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
         * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
         * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
         * 	at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
         * 	at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         *
         *    Locked ownable synchronizers:
         * 	- None
         */
        
        /**
         *"pool-1-thread-2" #11 prio=5 os_prio=31 tid=0x00007ff491061000 nid=0x5503 waiting for monitor entry [0x000070000b970000]
         *    java.lang.Thread.State: BLOCKED (on object monitor)
         * 	at concurrent.MyThread.lambda$main$0(MyThread.java:90)
         * 	- waiting to lock <0x0000000746405400> (a java.lang.Class for concurrent.MyThread)
         * 	at concurrent.MyThread$$Lambda$1/1791741888.run(Unknown Source)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         *
         *    Locked ownable synchronizers:
         * 	- <0x000000074641aef8> (a java.util.concurrent.ThreadPoolExecutor$Worker)
         *
         * "pool-1-thread-1" #10 prio=5 os_prio=31 tid=0x00007ff4909c0000 nid=0x4003 runnable [0x000070000b86d000]
         *    java.lang.Thread.State: RUNNABLE
         * 	at java.io.FileOutputStream.writeBytes(Native Method)
         * 	at java.io.FileOutputStream.write(FileOutputStream.java:326)
         * 	at java.io.BufferedOutputStream.flushBuffer(BufferedOutputStream.java:82)
         * 	at java.io.BufferedOutputStream.flush(BufferedOutputStream.java:140)
         * 	- locked <0x0000000746408610> (a java.io.BufferedOutputStream)
         * 	at java.io.PrintStream.write(PrintStream.java:482)
         * 	- locked <0x0000000746408640> (a java.io.PrintStream)
         * 	at sun.nio.cs.StreamEncoder.writeBytes(StreamEncoder.java:221)
         * 	at sun.nio.cs.StreamEncoder.implFlushBuffer(StreamEncoder.java:291)
         * 	at sun.nio.cs.StreamEncoder.flushBuffer(StreamEncoder.java:104)
         * 	- locked <0x0000000746408628> (a java.io.OutputStreamWriter)
         * 	at java.io.OutputStreamWriter.flushBuffer(OutputStreamWriter.java:185)
         * 	at java.io.PrintStream.write(PrintStream.java:527)
         * 	- eliminated <0x0000000746408640> (a java.io.PrintStream)
         * 	at java.io.PrintStream.print(PrintStream.java:669)
         * 	at java.io.PrintStream.println(PrintStream.java:806)
         * 	- locked <0x0000000746408640> (a java.io.PrintStream)
         * 	at concurrent.MyThread.lambda$main$0(MyThread.java:91)
         * 	- locked <0x0000000746405400> (a java.lang.Class for concurrent.MyThread)
         * 	at concurrent.MyThread$$Lambda$1/1791741888.run(Unknown Source)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         *
         *    Locked ownable synchronizers:
         * 	- <0x00000007464252b0> (a java.util.concurrent.ThreadPoolExecutor$Worker)
         */
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 1000_000_000L; i++) {
//            executorService.execute(() -> {
//                synchronized (MyThread.class) {
//                    System.out.println("hello");
//                }
//            });
//        }
    
        /**
         * "pool-1-thread-2" #11 prio=5 os_prio=31 tid=0x00007fe1d8180000 nid=0x4403 in Object.wait() [0x0000700008066000]
         *    java.lang.Thread.State: WAITING (on object monitor)
         * 	at java.lang.Object.wait(Native Method)
         * 	- waiting on <0x000000074379b6d0> (a java.lang.Class for concurrent.MyThread)
         * 	at java.lang.Object.wait(Object.java:502)
         * 	at concurrent.MyThread.lambda$main$0(MyThread.java:140)
         * 	- locked <0x000000074379b6d0> (a java.lang.Class for concurrent.MyThread)
         * 	at concurrent.MyThread$$Lambda$1/1791741888.run(Unknown Source)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         *
         *    Locked ownable synchronizers:
         * 	- <0x00000007437a96f8> (a java.util.concurrent.ThreadPoolExecutor$Worker)
         */
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 1000_000_000L; i++) {
            executorService.execute(() -> {
                synchronized (T04_ThreadStatus.class) {
                    try {
                        System.out.println("hello");
                        T04_ThreadStatus.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        
    }
}
