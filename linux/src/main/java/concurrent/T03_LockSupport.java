package concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 实验:https://blog.csdn.net/anlian523/article/details/106752414
 */
public class T03_LockSupport {

  @Test
  public void testParkBeforeUnPark() throws InterruptedException, IOException {
    Thread t = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        System.out.println(i);
        if (i == 5) {
          System.out.println("before park:" + System.currentTimeMillis());
          LockSupport.park();
          System.out.println("after unpark:" + System.currentTimeMillis());
        }
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    t.start();
    TimeUnit.SECONDS.sleep(10);
    LockSupport.unpark(t);
    System.in.read();
  }

  @Test
  public void testUnParkBeforePark() throws IOException {
    Thread t = new Thread(() -> {
      LockSupport.unpark(Thread.currentThread());
      for (int i = 0; i < 10; i++) {
        System.out.println(i);
        if (i == 5) {
          System.out.println("before park:" + System.currentTimeMillis());
          LockSupport.park();
          System.out.println("after unpark:" + System.currentTimeMillis());
        }
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    t.start();

    System.in.read();
  }


  /**
   * blocker的好处在于，在诊断问题的时候能够知道park的原因
   * <p>
   * "Thread-0" #10 prio=5 os_prio=31 tid=0x00007fb6522d8000 nid=0x3c03 waiting on condition [0x00007000058bb000]
   * java.lang.Thread.State: WAITING (parking)
   * at sun.misc.Unsafe.park(Native Method)
   * - parking to wait for  <0x00000007963591b0> (a concurrent.T20_TestLockSupport)
   * at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
   * at concurrent.T20_TestLockSupport.lambda$testBlockerOnObject$2(T20_TestLockSupport.java:62)
   * at concurrent.T20_TestLockSupport$$Lambda$1/431687835.run(Unknown Source)
   * at java.lang.Thread.run(Thread.java:748)
   *
   * @throws IOException
   */
  @Test
  public void testBlockerOnObject() throws IOException {
    Thread t = new Thread(() -> {
      // UNSAFE.putObject(t, parkBlockerOffset, arg);x
      LockSupport.park(new T03_LockSupport());
    });
    t.start();
    System.in.read();
  }

  /**
   * "Thread-0" #10 prio=5 os_prio=31 tid=0x00007f88173b2000 nid=0x3f03 waiting on condition [0x000070000c626000]
   * java.lang.Thread.State: WAITING (parking)
   * at sun.misc.Unsafe.park(Native Method)
   * at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
   * at concurrent.T20_TestLockSupport.lambda$testPark$3(T20_TestLockSupport.java:71)
   * at concurrent.T20_TestLockSupport$$Lambda$1/431687835.run(Unknown Source)
   * at java.lang.Thread.run(Thread.java:748)
   *
   * @throws IOException
   */
  @Test
  public void testPark() throws IOException {
    Thread t = new Thread(() -> {
      LockSupport.park();
    });
    t.start();
    System.in.read();
  }

  @Test
  public void testInterrupt() throws IOException {
    Thread.currentThread().interrupt();
    LockSupport.park();  //消耗掉permit后，直接返回了
    System.out.println(" unpark as comsume permit");
    System.in.read();
  }

  @Test
  public void testInterrupt1() throws IOException {
    Thread.currentThread().interrupt();
    LockSupport.park();  //消耗掉permit后，直接返回了
    System.out.println(" unpark  as comsume permit");
    LockSupport.park();  //因为中断状态 == true，直接返回了
    System.out.println(" unpark as interrupt=true");
    LockSupport.park();  //同上
    System.out.println(" unpark as interrupt=true");
    System.in.read();
  }

  @Test
  public void testSleepInterrupt() throws IOException {
    Thread.currentThread().interrupt();
    try {
      Thread.sleep(1000);//消耗掉中断状态
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    LockSupport.park();  //消耗掉permit
    LockSupport.park();  //因为此时permit为0且中断状态为false，所以阻塞
  }
}
