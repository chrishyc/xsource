package productconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentryNotify {
    public static final int FULL = 100;
    public static final int EMPTY = 0;
    public static final ReentrantLock LOCK = new ReentrantLock();
    public static final Condition FULL_CONDITION = LOCK.newCondition();
    public static final Condition EMPTY_CONDITION = LOCK.newCondition();
    public static Integer sharedData = 0;
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i <= 100; i++) {
            executorService.execute(new Product());
            executorService.execute(new Consumer());
        }
    }
    
    public static class Product implements Runnable {
        
        @Override
        public void run() {
            while (true) {
                try {
                    LOCK.lock();
                    while (sharedData == FULL) {
                        try {
                            FULL_CONDITION.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sharedData++;
                    System.out.println("producer:" + Thread.currentThread().getName() + ":" + sharedData);
                    EMPTY_CONDITION.signalAll();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }
    
    public static class Consumer implements Runnable {
        
        @Override
        public void run() {
            while (true) {
                try {
                    LOCK.lock();
                    while (sharedData == EMPTY) {
                        try {
                            EMPTY_CONDITION.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sharedData--;
                    System.out.println("consumer:" + Thread.currentThread().getName() + ":" + sharedData);
                    FULL_CONDITION.signalAll();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }
}
