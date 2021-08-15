package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
public class y_train_1_NotifyHoldingLock { //wait notify

  volatile List lists = new ArrayList();

  public void add(Object o) {
    lists.add(o);
  }

  public int size() {
    return lists.size();
  }

  public static void main(String[] args) {
    y_train_1_NotifyHoldingLock c = new y_train_1_NotifyHoldingLock();

    final Object lock = new Object();
    AtomicBoolean entered = new AtomicBoolean(false);
    new Thread(() -> {
      System.out.println("t1 enter");
      synchronized (lock) {
        try {
          System.out.println("t1 enter before wait");
          if (!entered.get()) lock.wait();
          System.out.println("t1 enter after wait");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
          c.add(new Object());
          System.out.println("add " + i);

          if (c.size() == 5) {
            lock.notify();//不会释放锁
            try {
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "t1").start();
    new Thread(() -> {
      synchronized (lock) {
        System.out.println("t2 enter");
        entered.set(true);
        if (c.size() != 5) {
          try {
            System.out.println("t2 enter before wait");
            lock.notify();
            lock.wait();
            System.out.println("t2 enter after wait");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("t2 exit");
      }

    }, "t2").start();

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }


  }
}
