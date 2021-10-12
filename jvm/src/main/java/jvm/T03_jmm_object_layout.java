package jvm;

/**
 * 查看内存布局HSDB工具
 * sudo java -cp ,:/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/lib/sa-jdi.jar sun.jvm.hotspot.HSDB
 */
public class T03_jmm_object_layout {
  public static void main(String[] args) {
    Foo foo = new Foo();
  }

  static class Foo {

  }
}
