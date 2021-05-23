package productconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreAcq {
    public static final Semaphore mutex = new Semaphore(1);
    public static final Semaphore full = new Semaphore(10);
    public static final Semaphore empty = new Semaphore(0);
    public static int sharedData = 0;
    
    
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
            try {
                /**
                 * 顺序不能换,不能让范围大的mutex依赖范围小的full
                 */
                full.acquire();
                mutex.acquire();
                sharedData++;
                System.out.println("producer:" + Thread.currentThread().getName() + ":" + sharedData);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.release();
                empty.release();
            }
        }
    }
    
    public static class Consumer implements Runnable {
        
        @Override
        public void run() {
            try {
                empty.acquire();
                mutex.acquire();
                sharedData--;
                System.out.println("consumer:" + Thread.currentThread().getName() + ":" + sharedData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.release();
                full.release();
            }
        }
    }
}
