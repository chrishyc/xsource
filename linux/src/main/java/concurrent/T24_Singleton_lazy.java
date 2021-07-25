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

public class T24_Singleton_lazy {
    private static volatile T24_Singleton_lazy s;
    
    private T24_Singleton_lazy() {
        System.out.println("single");
    }
    
    public static T24_Singleton_lazy getSingle() {
        if (s == null) {
            synchronized (T24_Singleton_lazy.class) {
                if (s == null) {
                    s = new T24_Singleton_lazy();
                }
            }
        }
        return s;
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(100000);
    }
    
}
