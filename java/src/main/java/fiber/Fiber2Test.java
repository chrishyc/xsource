package fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Fiber2Test {
    public static void main(String[] args) throws Exception {
        
        long start = System.currentTimeMillis();
        Runnable r = () -> {
            try {
                int a = 0;
                fiberCalc();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        
        int size = 2;
        
        Thread[] threads = new Thread[size];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(r);
        }
        
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    
    static void fiberCalc() throws ExecutionException, InterruptedException {
        int size = 5000;
        
        Fiber<Void>[] fibers = new Fiber[size];
        
        for (int i = 0; i < fibers.length; i++) {
            fibers[i] = new Fiber<Void>((SuspendableRunnable) () -> calc());
        }
        
        for (int i = 0; i < fibers.length; i++) {
            fibers[i].start();
        }
        
        for (int i = 0; i < fibers.length; i++) {
            fibers[i].join();
        }
    }
    
    static void calc() {
        int result = 0;
        for (int m = 0; m < 10000; m++) {
            for (int i = 0; i < 200; i++) result += i;
        }
    }
}
