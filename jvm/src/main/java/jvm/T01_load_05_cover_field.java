package jvm;

import java.util.ArrayList;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_05_cover_field {


  static class Father {
    public int money = 1;

    /**
     *  0 aload_0(this为son)
     *  1 invokespecial #1 <java/lang/Object.<init>>
     *  4 aload_0
     *  5 iconst_1
     *  6 putfield #2 <jvm/T01_load_05_cover_field$Father.money>
     *  9 aload_0
     * 10 iconst_2
     * 11 putfield #2 <jvm/T01_load_05_cover_field$Father.money>
     * 14 aload_0
     * 15 invokevirtual #3 <jvm/T01_load_05_cover_field$Father.showMeTheMoney>
     * 18 return
     */
    public Father() {
      money = 2;
      showMeTheMoney();
    }

    public void showMeTheMoney() {
      System.out.println("I am Father, i have $" + money);
    }
  }

  static class Son extends Father {
    public int money = 3;

    public Son() {
      money = 4;
      showMeTheMoney();
    }

    public void showMeTheMoney() {
      System.out.println("I am Son, i have $" + money);
    }
  }

  /**
   * I am Son, i have $0
   * I am Son, i have $4
   * This gay has $2
   * @param args
   */
  public static void main(String[] args) {
    Father gay = new Son();
    System.out.println("This gay has $" + gay.money);
  }
}
