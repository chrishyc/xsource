/**
 * 两个线程同步模型
 */
package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

// park unpark

public class y_train_1_LockSupport {

  volatile List lists = new ArrayList();

  public void add(Object o) {
    lists.add(o);
  }

  public int size() {
    return lists.size();
  }
  public int minNonZeroProduct(int p) {
    long m=(long)Math.pow(2,p-1);
    m=m-1;
    long base=(long)Math.pow(2,p);
    base=base-2;
    long last=(long)Math.pow(2,p);
    last=last-1;
    long ret=(long)Math.pow(base,m)*last;
    return (int) (ret%(1000000007L));
  }
  public static void main(String[] args) {
    y_train_1_LockSupport c = new y_train_1_LockSupport();

    Thread t2 = new Thread(() -> {
      if (c.size() != 5) {
        // 有可能unpark先执行
        LockSupport.park();
      }
    }, "t2");

    t2.start();

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        c.add(new Object());
        System.out.println("add " + i);

        if (c.size() == 5) {
          LockSupport.unpark(t2);
        }
      }

    }, "t1").start();

  }
}
