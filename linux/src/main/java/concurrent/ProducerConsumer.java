package concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class ProducerConsumer {
  private List<Integer> list = new LinkedList<>();
  private Lock lock = new ReentrantLock();
  private Condition empty = lock.newCondition();
  private Condition full = lock.newCondition();
  private final int COUNT = 10;

  public void producer(int i) {
    try {
      lock.lock();
      while (list.size() >= COUNT) {
        try {
          full.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      list.add(i);
      empty.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public void consumer() {
    try {
      lock.lock();
      while (list.size() <= 0) {
        try {
          empty.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      list.remove(list.size() - 1);
      full.signalAll();
    } finally {
      lock.unlock();
    }
  }

}
