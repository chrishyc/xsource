package productconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Blockqueue {
    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(100);
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
            try {
                blockingQueue.put(1);
                System.out.println("producer:" + Thread.currentThread().getName() + ":" + sharedData);
                sharedData++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static class Consumer implements Runnable {
        
        @Override
        public void run() {
            try {
                blockingQueue.take();
                System.out.println("consumer:" + Thread.currentThread().getName() + ":" + sharedData);
                sharedData--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
