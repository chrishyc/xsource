/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */
package concurrent;

import sun.misc.Contended;

/**
 * -XX:-RestrictContended
 *
 * TODO 本文现象比较特殊,待进一步调研
 */
public class T07_Volatile_cacheline implements Runnable {
    
    static class ObjectA {
        @Contended
        private long flag = 1;
        @Contended
        private long flag10 = 1;
    }
    
    public static int i = 0;
    
    private /**volatile*/ ObjectA a;
    
    
    public T07_Volatile_cacheline(ObjectA a) {
        this.a = a;
    }
    
    @Override
    public void run() {
        while (a.flag == 1 || a.flag10 == 1) {
            i++;
//            System.out.println(a.flag);
        }
    }
    
    public void stop() {
        a.flag = 0;
        a.flag10 = 0;
    }
    
    public static void main(String[] args) throws InterruptedException {
        T07_Volatile_cacheline test = new T07_Volatile_cacheline(new ObjectA());
//        System.out.println(ClassLayout.parseInstance(new ObjectA()).toPrintable() + "\n" + "thread id:" + Thread.currentThread().getId());
        Thread t = new Thread(test);
        t.start();
        
//        Thread.sleep(1);// 保证线程已启动
        for (int i=0;i<1000000000L;i++);
        test.stop();
        t.join();
        System.out.println(i);
    }
    
}
