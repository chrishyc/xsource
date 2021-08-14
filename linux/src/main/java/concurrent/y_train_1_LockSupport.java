/**
 *
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

  public static void main(String[] args) {
    y_train_1_LockSupport c = new y_train_1_LockSupport();

    Thread t2 = new Thread(() -> {
      if (c.size() != 5) {
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
