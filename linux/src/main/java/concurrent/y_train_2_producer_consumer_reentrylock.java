package concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class y_train_2_producer_consumer_reentrylock {
  private static List<Integer> list = new LinkedList<>();
  private Lock lock = new ReentrantLock();
  private Condition empty = lock.newCondition();
  private Condition full = lock.newCondition();

  public void producer(int i) {
    try {
      lock.lock();
      while (list.size() >= 100) {
        try {
          System.out.println("producer wait");
          full.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("producer:" + i);
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
          System.out.println("consumer wait");
          empty.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("consumer:" + list.get(list.size() - 1));
      list.remove(list.size() - 1);
      full.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    y_train_2_producer_consumer_reentrylock agent = new y_train_2_producer_consumer_reentrylock();
    ExecutorService produce = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 1000L; i++) {
      int finalI = i;
      produce.execute(() -> agent.producer(finalI));
    }

    ExecutorService consumer = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 1000L; i++) {
      consumer.execute(agent::consumer);
    }
  }
}
