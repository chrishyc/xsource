package concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.locks.LockSupport;

public class T03_Interrupt {
    public static void main(String[] args) {
    
    }
    
    @Test
    public void testSleep() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
        System.in.read();
    }
    
    @Test
    public void testWait() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                synchronized (this){
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
        System.in.read();
    }
    
    @Test
    public void testJoin() throws IOException, InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        Thread thread = new Thread(() -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        
        System.in.read();
    }
    
    /**
     * 不可中断
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testPark() throws IOException, InterruptedException {
        Thread thread = new Thread(() -> {
            LockSupport.park();
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        
        System.in.read();
    }
    
}
