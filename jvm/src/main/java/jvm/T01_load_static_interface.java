package jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * -XX:+TraceClassLoading
 */
@Slf4j
public class T01_load_static_interface {


  static public interface ConstClass {
    public static final String HELLOWORLD = "hello world";
    public static String var = "hello world";
  }


  public static void main(String[] args) {
    ConstClass constClass = new ConstClass() {

    };
    log.info("ConstClass:{}", constClass);
  }
}
