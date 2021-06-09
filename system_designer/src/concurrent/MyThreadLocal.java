package concurrent;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * https://www.liaoxuefeng.com/wiki/1252599548343744/1306581251653666
 *
 * threadlocal使用
 * threadlocal概念模型
 * threadlocal对象模型
 * threadlocal实现模型
 * threadlocal注意事项
 * threadlocal问题
 */
public class MyThreadLocal {
    private ThreadLocal<Long> myThreadLocal = ThreadLocal.withInitial(() -> Long.MAX_VALUE);
    
    private ThreadLocal<List<String>> myThreadLocal1 = ThreadLocal.withInitial(() -> Lists.newArrayList("hello"));
    
    @Test
    public void test() {
        for (int i = 0; i <= 5; i++) {
            new Thread(() -> {
                myThreadLocal.set(System.currentTimeMillis());
                System.out.println(Thread.currentThread().getName() + ":" + myThreadLocal.get());
            }).start();
        }
    }
    
    public static class MyRunnable implements Runnable {
        
        private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":before:threadLocal:" + threadLocal.get());
            threadLocal.set((int) (Math.random() * 100D));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            
            }
            System.out.println(Thread.currentThread().getName() + ":after:threadLocal:" + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + ":mainLocal:" + mainLocal.get());
        }
    }
    
    private static InheritableThreadLocal<Integer> mainLocal = new InheritableThreadLocal<Integer>() {
//        @Override
//        protected Integer initialValue() {
//            return 89;
//        }
//
        @Override
        protected Integer childValue(Integer parentValue) {
            return 77;
        }
    };
    
    public static void main(String[] args) throws InterruptedException {
        
        Executor executor = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 20; i++) {
            MyRunnable sharedRunnableInstance = new MyRunnable();
            executor.execute(sharedRunnableInstance);
        }
    }
    
}
