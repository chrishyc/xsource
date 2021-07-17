package concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class T18_TestReadWriteLock {
    static Lock lock = new ReentrantLock();
    private static int value;
    
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();
    
    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(2000);
            System.out.println("read over!");
            //模拟读取操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("read unlock!");
        }
    }
    
    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(2000);
            value = v;
            System.out.println("write over!");
            //模拟写操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("write unlock!");
        }
    }
    
    @Test
    public void testReadRead() {
        Runnable readR = () -> read(readLock);
        for (int i = 0; i < 2; i++) new Thread(readR).start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * "Thread-2" #14 prio=5 os_prio=31 tid=0x00007fd765200000 nid=0x5703 waiting on condition [0x0000700002da8000]
     *    java.lang.Thread.State: WAITING (parking)
     * 	at sun.misc.Unsafe.park(Native Method)
     * 	- parking to wait for  <0x00000007962ac2b8> (a java.util.concurrent.locks.ReentrantReadWriteLock$NonfairSync)
     * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireShared(AbstractQueuedSynchronizer.java:967)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireShared(AbstractQueuedSynchronizer.java:1283)
     * 	at java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock.lock(ReentrantReadWriteLock.java:727)
     * 	at concurrent.T18_TestReadWriteLock.read(T18_TestReadWriteLock.java:21)
     * 	at concurrent.T18_TestReadWriteLock.lambda$testReadWriteRead$1(T18_TestReadWriteLock.java:61)
     * 	at concurrent.T18_TestReadWriteLock$$Lambda$1/2050404090.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "Thread-1" #13 prio=5 os_prio=31 tid=0x00007fd7659d9000 nid=0xa903 waiting on condition [0x0000700002ca5000]
     *    java.lang.Thread.State: WAITING (parking)
     * 	at sun.misc.Unsafe.park(Native Method)
     * 	- parking to wait for  <0x00000007962ac2b8> (a java.util.concurrent.locks.ReentrantReadWriteLock$NonfairSync)
     * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
     * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
     * 	at java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock.lock(ReentrantReadWriteLock.java:943)
     * 	at concurrent.T18_TestReadWriteLock.write(T18_TestReadWriteLock.java:35)
     * 	at concurrent.T18_TestReadWriteLock.lambda$testReadWriteRead$2(T18_TestReadWriteLock.java:62)
     * 	at concurrent.T18_TestReadWriteLock$$Lambda$2/266437232.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "Thread-0" #12 prio=5 os_prio=31 tid=0x00007fd7663d0800 nid=0x3c03 at breakpoint[0x0000700002ba2000]
     *    java.lang.Thread.State: RUNNABLE
     * 	at concurrent.T18_TestReadWriteLock.read(T18_TestReadWriteLock.java:28)
     * 	at concurrent.T18_TestReadWriteLock.lambda$testReadWriteRead$1(T18_TestReadWriteLock.java:61)
     * 	at concurrent.T18_TestReadWriteLock$$Lambda$1/2050404090.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     */
    @Test
    public void testReadWriteRead() {
        Runnable readR = () -> read(readLock);
        Runnable writeR = () -> write(writeLock, new Random().nextInt());
        new Thread(readR).start();// Thread-0
        new Thread(writeR).start();// Thread-1
        new Thread(readR).start();// Thread-2
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testReadWriteReadWrite() {
        Runnable readR = () -> read(readLock);
        Runnable writeR = () -> write(writeLock, new Random().nextInt());
        new Thread(readR).start();
        new Thread(writeR).start();
        new Thread(readR).start();
        new Thread(writeR).start();
    }
    
    
    public static void main(String[] args) {
        //Runnable readR = ()-> read(lock);
//        Runnable readR = () -> read(readLock);
//
//        //Runnable writeR = ()->write(lock, new Random().nextInt());
//        Runnable writeR = () -> write(writeLock, new Random().nextInt());
//
//        for (int i = 0; i < 18; i++) new Thread(readR).start();
//        for (int i = 0; i < 2; i++) new Thread(writeR).start();
    
    
    }
}
