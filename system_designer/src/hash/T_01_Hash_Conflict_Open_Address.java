package hash;

/**
 * 开放定址法:https://zhuanlan.zhihu.com/p/356419799
 */
public class T_01_Hash_Conflict_Open_Address {

  public static void main(String[] args) {
    ThreadLocal<Integer> myThreadLocal = ThreadLocal.withInitial(() -> Integer.MAX_VALUE);
    for (int i = 0; i < 100; i++) {
      myThreadLocal.set(i);
    }
    for (int i = 0; i < 100; i++) {
      myThreadLocal.get();
    }
  }
}
