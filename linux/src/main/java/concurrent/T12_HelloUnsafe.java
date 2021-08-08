package concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe使用
 * https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html
 */
public class T12_HelloUnsafe {
  static class M {
    private M() {
    }

    int i = 0;
  }

  public static Unsafe getUnsafe() {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      return (Unsafe) f.get(null);
    } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args) throws InstantiationException {
    Unsafe unsafe = T12_HelloUnsafe.getUnsafe();
    M m = (M) unsafe.allocateInstance(M.class);
    m.i = 9;
    System.out.println(m.i);
  }
}


