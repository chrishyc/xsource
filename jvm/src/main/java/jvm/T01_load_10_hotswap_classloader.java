package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_10_hotswap_classloader extends ClassLoader {


  public T01_load_10_hotswap_classloader() {
    super(T01_load_10_hotswap_classloader.class.getClassLoader());
  }

  public Class loadByte(byte[] classByte) {
    return defineClass(null, classByte, 0, classByte.length);
  }


  public static void main(String[] args) {
    System.out.println("");
  }
}
