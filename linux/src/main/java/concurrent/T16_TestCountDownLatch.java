package concurrent;

import java.util.concurrent.CountDownLatch;

public class T16_TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
//        usingJoin();
        usingCountDownLatch();
    }
    
    private static void usingCountDownLatch() throws InterruptedException {
        
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(100);
//        new Thread(() -> {
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//        new Thread(() -> {
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 10000; j++) result += j;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                
                result = 2;
            });
        }
        
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        new Thread(()-> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        latch.await();
        Thread.sleep(Integer.MAX_VALUE);
        
        System.out.println("end latch");
    }
    
    private static void usingJoin() {
        Thread[] threads = new Thread[100];
        
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 10000; j++) result += j;
            });
        }
        
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("end join");
    }
}
