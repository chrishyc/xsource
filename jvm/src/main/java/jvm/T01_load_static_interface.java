package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_static_interface {


  static public interface ConstClass {
    public static final String HELLOWORLD = "hello world";
    public static  String var = "hello world";
  }


  public static void main(String[] args) {
    System.out.println(ConstClass.HELLOWORLD);
  }
}
