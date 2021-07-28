/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */
package concurrent;

import sun.misc.Contended;

public class T07_Volatile_cacheline implements Runnable {
    
    static class ObjectA {
        @Contended
        private long flag = 1;
        ;
        @Contended
        private long flag10 = 1;
    }
    
    private volatile ObjectA a;
    
    
    public T07_Volatile_cacheline(ObjectA a) {
        this.a = a;
    }
    
    @Override
    public void run() {
        while (a.flag == 1 || a.flag10 == 1) {
//            i++;
//            System.out.println(a.flag);
        }
    }
    
    public void stop() {
        a.flag = 0;
        a.flag10 = 0;
    }
    
    public static void main(String[] args) throws InterruptedException {
        T07_Volatile_cacheline test = new T07_Volatile_cacheline(new ObjectA());
        Thread t = new Thread(test);
        t.start();
        
        Thread.sleep(1000);// 保证线程已启动
        test.stop();
        t.join();
    }
    
}
