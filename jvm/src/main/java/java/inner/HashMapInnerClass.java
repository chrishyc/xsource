package java.inner;

import java.util.HashMap;
import java.util.Map;

/**
 * 内部类OOM问题，指针泄露
 * https://www.cnblogs.com/vipstone/p/12937582.html
 */
public class HashMapInnerClass {
    private String outerRef = "leak_memory";
    private static Map<String, String> map111 = new HashMap<String, String>() {
        {
            put("hello", "world");
//            put("waring", outerRef);
        }
    };
    
    private Map<String, String> map222 = new HashMap<String, String>() {
        {
            put("hello", "world");
//            put("waring", outerRef);
        }
    };
    
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
//        HashMapInnerClass map = new HashMapInnerClass();
//        Thread.sleep(60000);
//        Field field = map.map111.getClass().getDeclaredField("this$0");
//        field.setAccessible(true);
//        System.out.println(field.get(map.map111));
//        System.gc();
        
        for (int i = 0; i < 1000000; i++) {
            HashMapInnerClass map = new HashMapInnerClass();
            System.gc();
            map = null;
            Thread.sleep(10);
        }
        
    }
}
