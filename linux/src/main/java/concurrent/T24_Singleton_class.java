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

public class T24_Singleton_class {
    
    private T24_Singleton_class() {
        System.out.println("single");
    }
    
    private static class Inner {
        private static T24_Singleton_class s = new T24_Singleton_class();
    }
    
    public static T24_Singleton_class getSingle() {
        return Inner.s;
    }
    
    
    
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(100000);
    }
    
}
