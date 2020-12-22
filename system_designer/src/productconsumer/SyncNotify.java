package productconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * got:
 * 1.productor/consumer以runnable形式展示
 * 2.object wait&notifyAll,不能使用if需要使用while
 * 3.sharedData值传递会变成本地值
 */
public class SyncNotify {
    public static final int FULL = 100;
    public static final int EMPTY = 0;
    public static final String LOCK = "LOCK";
    public static Integer sharedData = 0;
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Productor());
            executorService.execute(new Consumer());
        }
    }
    
    public static class Productor implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    while (sharedData == FULL) {
                        try {
                            System.out.println("============");
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sharedData++;
                    LOCK.notifyAll();
                    System.out.println("producer:" + Thread.currentThread().getName() + ":" + sharedData);
                }
            }
        }
    }
    
    public static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    while (sharedData == EMPTY) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sharedData--;
                    LOCK.notifyAll();
                    System.out.println("consumer:" + Thread.currentThread().getName() + ":" + sharedData);
                }
            }
        }
    }
}
