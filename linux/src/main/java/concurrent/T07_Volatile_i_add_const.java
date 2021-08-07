/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */
package concurrent;

public class T07_Volatile_i_add_const {
  private static int k = 0;

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      /**
       *  0 iconst_0
       *  1 istore_0
       *  2 iload_0
       *  3 ldc #11 <10000000>
       *  5 if_icmpge 22 (+17)
       *
       *
       *
       *  8 getstatic #9 <concurrent/T07_Volatile_i_add_const.k>
       * 11 iconst_1
       * 12 iadd
       * 13 putstatic #9 <concurrent/T07_Volatile_i_add_const.k>
       *
       *
       * 16 iinc 0 by 1
       * 19 goto 2 (-17)
       * 22 return
       */
      for (int i = 0; i < 10000000; i++) k++;
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 10000000; i++) k++;
    });
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(k);
  }

}
