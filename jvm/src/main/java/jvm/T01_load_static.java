package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX: +TraceClassLoading
 */
public class T01_load_static {
  static {
    System.out.println("T01_load_static init!");
  }

  public static int value = 123;


  static class SuperClass {
    static {
      System.out.println("SuperClass init!");
    }

    public static int value = 123;
  }

  static class SubClass extends SuperClass {
    static {
      System.out.println("SubClass init!");
    }

  }

  /**
   * 非主动使用类字段演示,通过其子类来引用父类中定义的静态字段，
   * 只会触发 父类的初始化而不会触发子类的初始化
   **/
  public static void main(String[] args) {
    System.out.println(SubClass.value);
  }
}
