package concurrent;

import sun.misc.Unsafe;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * unsafe使用
 * https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html
 * unsafe原理
 * https://blog.csdn.net/zyzzxycj/article/details/89877863
 */
public class T12_Unsafe {
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

  @Test
  public void testAllocateInstance() throws InstantiationException {
    Unsafe unsafe = T12_Unsafe.getUnsafe();
    M m = (M) unsafe.allocateInstance(M.class);
    m.i = 9;
    System.out.println(m.i);
  }

  static class Guard {
    /**
     * final字段不可以,hotspot会校验是否为final?
     *
     * @throws NoSuchFieldException
     */
    private int ACCESS_ALLOWED = 1;

    public int giveAccess() {
      return ACCESS_ALLOWED;
    }
  }


  @Test
  public void testMemoryAccess() throws NoSuchFieldException {
    Guard guard = new Guard();
    System.out.println(guard.giveAccess());   // false, no access

    // bypass
    Field field = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
    T12_Unsafe.getUnsafe().putInt(guard, T12_Unsafe.getUnsafe().objectFieldOffset(field), 42); // memory corruption
    System.out.println(guard.giveAccess()); // true, access granted

  }


  @Test
  public void testAddressAccess() {
    //通过allocateMemory方法分配一个8具有8个字节的内存空间
    long address = T12_Unsafe.getUnsafe().allocateMemory(8);

    //给这个内存中存放一个本地指针
    T12_Unsafe.getUnsafe().putAddress(address, 1);
    //获取指定内存地址的指针
    System.out.println(T12_Unsafe.getUnsafe().getAddress(address));
    //打印通过putAddress存放的本地指针的大小
    System.out.println(T12_Unsafe.getUnsafe().addressSize());
    //打印内存页面的大小，通常为4k,即4096   //关于内存页面不了解的可以看下操作系统相关的知识
    System.out.println(T12_Unsafe.getUnsafe().pageSize());
    //释放内存
    T12_Unsafe.getUnsafe().freeMemory(address);
  }

}


