package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_static_final {


  static public class ConstClass {
    static {
      System.out.println("ConstClass init!");
    }

    public static final String HELLOWORLD = "hello world";
  }

  /**
   * 但其实在编译阶段通过常量传播优化，已经将此常量的值“hello world”直接存储在NotInitialization类的常量池中，
   * 以后NotInitialization对常量 ConstClass.HELLOWORLD的引用，实际都被转化为NotInitialization类对自身常量池的引用了。
   * 也就是 说，实际上NotInitialization的Class文件之中并没有ConstClass类的符号引用入口，这两个类在编译成 Class文件后就已不存在任何联系了
   *
   * 0 getstatic #2 <java/lang/System.out>
   * 3 ldc #4 <hello world>
   * 5 invokevirtual #5 <java/io/PrintStream.println>
   * 8 return
   **/
  public static void main(String[] args) {
    System.out.println(ConstClass.HELLOWORLD);
  }
}
