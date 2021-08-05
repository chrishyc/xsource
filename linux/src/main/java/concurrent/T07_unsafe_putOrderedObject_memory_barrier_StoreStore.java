/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */
package concurrent;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class T07_unsafe_putOrderedObject_memory_barrier_StoreStore {
  public static class SomeThing {
    private int status;

    public SomeThing() {
      status = 1;
    }

    public int getStatus() {
      return status;
    }
  }

  private SomeThing object;

  private Object value;
  private static final Unsafe unsafe = getUnsafe();
  private static final long valueOffset;

  static {
    try {
      valueOffset = unsafe.objectFieldOffset(T07_unsafe_putOrderedObject_memory_barrier_StoreStore.class.getDeclaredField("value"));
    } catch (Exception ex) {
      throw new Error(ex);
    }
  }

  public void create() {
    SomeThing temp = new SomeThing();
    unsafe.putOrderedObject(this, valueOffset, null);    //将value赋null值只是一项无用操作，实际利用的是这条语句的内存屏障
    object = temp;
  }

  public SomeThing get() {
    while (object == null) {
      Thread.yield();
    }
    return object;
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

}
