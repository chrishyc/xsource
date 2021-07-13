/**
 * ���ͬ��������ĸ���Ч�ķ�����ʹ��AtomXXX��
 * AtomXXX�౾��������ԭ���Եģ������ܱ�֤�����������������ԭ���Ե�
 *
 * @author mashibing
 */
package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;


public class T11_AtomicInteger {
    /*volatile*/ //int count1 = 0;
    
    AtomicInteger count = new AtomicInteger(0);
    
    /**
     * public final int getAndAddInt(Object var1, long var2, int var4) {
     *     int var5;
     *     do {
     *       var5 = this.getIntVolatile(var1, var2);
     *     } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
     *
     *     return var5;
     *   }
     */
    /*synchronized*/ void m() {
        for (int i = 0; i < 10000; i++)
            //if count1.get() < 1000
            count.incrementAndGet(); //count1++
    }
    
    static void aba() {
        new Thread(() -> {
            index.compareAndSet(10, 11);
            index.compareAndSet(11, 10);
            System.out.println(Thread.currentThread().getName() +
                    "： 10->11->10");
        }, "张三").start();
        
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = index.compareAndSet(10, 12);
                System.out.println(Thread.currentThread().getName() +
                        ": index是预期的10嘛，" + isSuccess
                        + "   设置的新值是：" + index.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "李四").start();
    }
    
    private static AtomicInteger index = new AtomicInteger(10);
    static AtomicStampedReference<Integer> stampRef = new AtomicStampedReference<>(10, 1);
    
    static void abaStamp() {
        int stamp = stampRef.getStamp();
        new Thread(() -> {
            
            System.out.println(Thread.currentThread().getName()
                    + " 第1次版本号： " + stamp);
            stampRef.compareAndSet(10, 11, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()
                    + " 第2次版本号： " + stampRef.getStamp());
            stampRef.compareAndSet(11, 10, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()
                    + " 第3次版本号： " + stampRef.getStamp());
        }, "张三").start();
        
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()
                        + " 第1次版本号： " + stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = stampRef.compareAndSet(10, 12,
                        stamp, stampRef.getStamp() + 1);
                System.out.println(Thread.currentThread().getName()
                        + " 修改是否成功： " + isSuccess + " 当前版本 ：" + stampRef.getStamp());
                System.out.println(Thread.currentThread().getName()
                        + " 当前实际值： " + stampRef.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "李四").start();
    }
    
    public static void main(String[] args) {
        T11_AtomicInteger t = new T11_AtomicInteger();
        
        List<Thread> threads = new ArrayList<Thread>();
        
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }
        
        threads.forEach((o) -> o.start());
        
        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        System.out.println(t.count);
        
//        aba();
        
        abaStamp();
    }
    
}
