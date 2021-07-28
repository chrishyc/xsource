package concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * add=lock+volatile
 * get=volatile,保证整个读的数据一致性，丢失一定的实时性
 * 如果将 array 数组设定为 volitile 的， 对 volatile 变量写 happens-before 读，读线程不是能够感知到 volatile 变量的变化
 * 原因是，这里 volatile 的修饰的仅仅只是数组引用，数组中的元素的修改是不能保证可见性的。因此 COW 采用的是新旧两个数据容器，
 * 通过第 5 行代码将数组引用指向新的数组。
 */
public class T25_Collection_list_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> lists =
                //new ArrayList<>(); //
                //new Vector();
                new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[10];
        
        for (int i = 0; i < ths.length; i++) {
            Runnable task = () -> {
                for (int i1 = 0; i1 < 1000; i1++) lists.add("a" + r.nextInt(10000));
                System.out.println(Thread.currentThread().getName() + ":" + "==========finish========");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + lists.size());
            };
            ths[i] = new Thread(task);
        }
        
        runAndComputeTime(ths);
        
        System.out.println(lists.size());
    }
    
    static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t -> t.start());
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
        
    }
}

