package jvm;

import org.junit.Test;

/**
 * 查看内存布局HSDB工具
 * sudo java -cp ,:/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/lib/sa-jdi.jar sun.jvm.hotspot.HSDB
 */
public class T02_classloaderLevel {
    public static void main(String[] args) {
        Foo foo = new Foo();
    }
    
    static class Foo {
    
    }
    
    @Test
    public void testClassLoaderLevel() {
        System.out.println(String.class.getClassLoader());
        System.out.println(sun.awt.HKSCS.class.getClassLoader());
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());
        System.out.println(T02_classloaderLevel.class.getClassLoader());
        
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader().getClass().getClassLoader());
        System.out.println(T02_classloaderLevel.class.getClassLoader().getClass().getClassLoader());
        
        System.out.println(new T02_MyClassLoader().getParent());
        System.out.println(ClassLoader.getSystemClassLoader());
    }
    
    @Test
    public void testClassLoaderScope() {
        String pathBoot = System.getProperty("sun.boot.class.path");
        System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));
        
        System.out.println("--------------------");
        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println(pathExt.replaceAll(";", System.lineSeparator()));
        
        System.out.println("--------------------");
        String pathApp = System.getProperty("java.class.path");
        System.out.println(pathApp.replaceAll(";", System.lineSeparator()));
    }
    
    public void test() {
        System.out.println("Hello 111112222444");
    }
}
