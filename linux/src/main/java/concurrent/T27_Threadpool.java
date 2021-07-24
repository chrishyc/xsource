/**
 * 认识Executor
 */
package concurrent;

import com.google.common.util.concurrent.*;
import com.sun.istack.internal.Nullable;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class T27_Threadpool implements Executor {
    
    @Test
    public void testCallable() throws ExecutionException, InterruptedException, IOException {
        Callable<String> c = () -> {
            try{
//                Thread.sleep(2000);
//                LockSupport.park();
            }catch (Exception e){
                System.out.print(e);
            }
            return "Hello Callable";
        };
        ExecutorService service = new ThreadPoolExecutor(2, 3,
                10L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        Future<String> future = service.submit(c);
        Future<String> future1 = service.submit(c);
        Future<String> future2 = service.submit(c);
        Future<String> future3 = service.submit(c);
        Future<String> future4 = service.submit(c);
        Thread.sleep(3000);
        service.shutdown();
//        Thread.sleep(5000);
//        Future<String> future5 = service.submit(c);
//        Future<String> future5 = service.submit(c);
        System.out.println(future.get());
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        System.out.println(future4.get());
//        System.out.println(future5.get());
        
        System.in.read();
    }
    
    @Test
    public void testFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        }); //new Callable () { Integer call();}
        
        new Thread(task).start();
        
        System.out.println(task.get());
    }
    
    /**
     * 假设你能够提供一个服务
     * 这个服务查询各大电商网站同一类产品的价格并汇总展示
     */
    @Test
    public void testCompletableFuture() {
        long start, end;

        /*start = System.currentTimeMillis();

        priceOfTM();
        priceOfTB();
        priceOfJD();

        end = System.currentTimeMillis();
        System.out.println("use serial method call! " + (end - start));*/
        
        start = System.currentTimeMillis();
        
        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceOfJD());
        
        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();
        
        CompletableFuture.supplyAsync(() -> priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(str -> "price " + str)
                .thenAccept(System.out::println);
        
        
        end = System.currentTimeMillis();
        System.out.println("use completable future! " + (end - start));
        
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testListenableFuture() {
        ListeningExecutorService service =
                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));
        ListenableFuture<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 8;
            }
        });
        
        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer integer) {
                System.out.println(integer);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, service);
        
        service.shutdown();
    }
    
    
    @Test
    public void testSingleThreadPool() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(() -> {
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
    }
    
    @Test
    public void testCachedThreadPool() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);
        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        
        TimeUnit.SECONDS.sleep(80);
        
        System.out.println(service);
    }
    
    @Test
    public void testFixedThreadPool() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
//        getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        final int cpuCoreNum = 4;
        
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
        
        MyTask t1 = new MyTask(1, 80000); //1-5 5-10 10-15 15-20
        MyTask t2 = new MyTask(80001, 130000);
        MyTask t3 = new MyTask(130001, 170000);
        MyTask t4 = new MyTask(170001, 200000);
        
        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);
        
        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    
    @Test
    public void testRejectedExecutionHandler() {
        ExecutorService service = new ThreadPoolExecutor(4, 4,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6),
                Executors.defaultThreadFactory(),
                new MyHandler());
    }
    
    @Test
    public void testScheduledExecutorService() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void execute(Runnable command) {
        //new Thread(command).run();
        command.run();
        
    }
    
    private static double priceOfTM() {
        delay();
        return 1.00;
    }
    
    private static double priceOfTB() {
        delay();
        return 2.00;
    }
    
    private static double priceOfJD() {
        delay();
        return 3.00;
    }

    /*private static double priceOfAmazon() {
        delay();
        throw new RuntimeException("product not exist!");
    }*/
    
    private static void delay() {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("After %s sleep!\n", time);
    }
    
    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;
        
        MyTask(int s, int e) {
            this.startPos = s;
            this.endPos = e;
        }
        
        @Override
        public List<Integer> call() throws Exception {
            List<Integer> r = getPrime(startPos, endPos);
            return r;
        }
        
    }
    
    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
    
    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) results.add(i);
        }
        
        return results;
    }
    
    static class MyHandler implements RejectedExecutionHandler {
        
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //log("r rejected")
            //save r kafka mysql redis
            //try 3 times
            if (executor.getQueue().size() < 10000) {
                //try put again();
            }
        }
    }
}

