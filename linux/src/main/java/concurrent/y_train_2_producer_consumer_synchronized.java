package concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class y_train_2_producer_consumer_synchronized {
  private static List<Integer> list = new LinkedList<>();


  public synchronized void producer(int i) {
    while (list.size() >= 100) {
      try {
        System.out.println("producer wait");
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("producer:" + i);
    list.add(i);
    notifyAll();
  }

  public synchronized void consumer() {
    while (list.size() <= 0) {
      try {
        System.out.println("consumer wait");
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("consumer:" + list.get(list.size() - 1));
    list.remove(list.size() - 1);
    notifyAll();
  }

  public static void main(String[] args) {
    y_train_2_producer_consumer_synchronized agent = new y_train_2_producer_consumer_synchronized();
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
