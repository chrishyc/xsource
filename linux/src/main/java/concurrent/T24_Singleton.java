/**
 * 线程安全的单例模式：
 * <p>
 * 阅读文章：http://www.cnblogs.com/xudong-bupt/p/3433643.html
 * <p>
 * 更好的是采用下面的方式，既不用加锁，也能实现懒加载
 *
 * @author 马士兵
 */
package concurrent;

import java.util.Arrays;

public class T24_Singleton {
    
    private T24_Singleton() {
        System.out.println("single");
    }
    
    private static class Inner {
        private static T24_Singleton s = new T24_Singleton();
    }
    
    public static T24_Singleton getSingle() {
        return Inner.s;
    }
    
    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                System.out.println(T24_Singleton.getSingle());
            });
        }
        
        Arrays.asList(ths).forEach(o -> o.start());
    }
    
}
