package concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 要求用线程顺序打印A1B2C3....Z26
 * <p>
 * 临界知识:
 * 先通知后阻塞,先后顺序:
 * 1.阻塞(后执行),执行逻辑,通知
 * 2.执行逻辑,通知then阻塞
 */
public class y_train_3_alternate_print {

  @Test
  public void testWaitNotify() throws IOException {
    final Object lock = new Object();
    new Thread(() -> {
      for (int i = 1; i <= 26; i++) {
        synchronized (lock) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.print(i);
          lock.notify();
        }
      }
    }).start();
    new Thread(() -> {
      for (char i = 'A'; i <= 'Z'; i++) {
        synchronized (lock) {
          System.out.print(i);
          lock.notify();
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      }
    }).start();
    System.in.read();
  }

  @Test
  public void testReentrylock() throws IOException {
    Lock lock = new ReentrantLock();
    Condition digit = lock.newCondition();
    Condition letter = lock.newCondition();
    new Thread(() -> {
      for (int i = 1; i <= 26; i++) {
        try {
          lock.lock();
          digit.await();
          System.out.print(i);
          letter.signal();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }

      }
    }).start();
    new Thread(() -> {
      for (char i = 'A'; i <= 'Z'; i++) {
        try {
          lock.lock();
          System.out.print(i);
          digit.signal();
          try {
            letter.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } finally {
          lock.unlock();
        }
      }
    }).start();
    System.in.read();
  }

  @Test
  public void testCAS() throws IOException {
    AtomicInteger cas = new AtomicInteger(1);
    new Thread(() -> {
      for (int i = 1; i <= 26; i++) {
        while (cas.get() == 1) ;
        System.out.print(i);
        cas.set(1);
      }
    }).start();
    new Thread(() -> {
      for (char i = 'A'; i <= 'Z'; i++) {
        System.out.print(i);
        cas.set(0);
        while (cas.get() == 0) ;
      }
    }).start();
    System.in.read();
  }

  volatile int cas = 1;

  @Test
  public void testVolatile() throws IOException {

    new Thread(() -> {
      for (int i = 1; i <= 26; i++) {
        while (cas == 1) ;
        System.out.print(i);
        cas = 1;
      }
    }).start();
    new Thread(() -> {
      for (char i = 'A'; i <= 'Z'; i++) {
        System.out.print(i);
        cas = 0;
        while (cas == 0) ;
      }
    }).start();
    System.in.read();
  }

  Thread t1 = null;
  Thread t2 = null;

  @Test
  public void testPark() throws IOException {

    t1 = new Thread(() -> {
      for (int i = 1; i <= 26; i++) {
        LockSupport.park();
        System.out.print(i);
        LockSupport.unpark(t2);
      }
    });
    t2 = new Thread(() -> {
      for (char i = 'A'; i <= 'Z'; i++) {
        System.out.print(i);
        LockSupport.unpark(t1);
        LockSupport.park();
      }
    });
    t1.start();
    t2.start();
    System.in.read();
  }

  @Test
  public void testBlockQueue() throws IOException {
    BlockingQueue<Integer> take = new LinkedBlockingQueue<>(1);
    BlockingQueue<Integer> take1 = new LinkedBlockingQueue<>(1);
    t1 = new Thread(() -> {
      for (int i = 1; i <= 26; i++) {
        try {
          take.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.print(i);
        take1.offer(1);
      }
    });
    t2 = new Thread(() -> {
      for (char i = 'A'; i <= 'Z'; i++) {
        System.out.print(i);
        take.offer(1);
        try {
          take1.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    });
    t1.start();
    t2.start();
    System.in.read();
  }
}
