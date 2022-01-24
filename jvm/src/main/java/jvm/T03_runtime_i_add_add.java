package jvm;

import org.junit.Test;

import java.io.IOException;

public class T03_runtime_i_add_add {

  /**
   * 0 bipush -98
   * 2 istore_1
   * 3 iload_1
   * 4 iinc 1 by 1
   * 7 istore_1
   * 8 return
   */
  @Test
  public void testIAddAdd() {
    int i = -98;
    i = i++;
    System.out.println(i);
  }

  /**
   * 0 bipush -98
   * 2 istore_1
   * 3 iinc 1 by 1
   * 6 iload_1
   * 7 istore_1
   * 8 return
   */
  @Test
  public void testAddAddI() {
    int i = -98;
    i = ++i;
  }

  @Test
  public void testIntField() {
    int i = 129;
    putField(i);
  }

  /**
   * 0 iload_1
   * 1 istore_2
   * 2 return
   *
   * @param i
   */
  public void putField(int i) {
    int a = i;
  }

  @Test
  public void testAAddB() {
    aAb(1, 2);
  }

  /**
   * 0 iload_1
   * 1 iload_2
   * 2 iadd
   * 3 istore_3
   * 4 return
   *
   * @param a
   * @param b
   */
  public void aAb(int a, int b) {
    int c = a + b;
  }

  static class Init {
    private int a;
    private Object o;
    private String s;

    public Init(String s) {
      this.s = s;
    }

    public Init(int a, Object o) {
      this.a = a;
      this.o = a;
    }

    public int getA() {
      return a;
    }
  }

  /**
   * 0 new #4 <jvm/T01_class_instruction_i_add_add$Init>
   * 3 dup
   * 4 iconst_1
   * 5 new #5 <java/lang/Object>
   * 8 dup
   * 9 invokespecial #1 <java/lang/Object.<init>>
   * 12 invokespecial #6 <jvm/T01_class_instruction_i_add_add$Init.<init>>
   * 15 astore_1
   * 16 aload_1
   * 17 invokevirtual #7 <jvm/T01_class_instruction_i_add_add$Init.getA>
   * 20 pop
   * 21 return
   */
  @Test
  public void testInit() {
    Init init = new Init(1, new Object());
    init.getA();
  }


  @Test
  public void testString() throws IOException {
    String s = "123456";
    Init init = new Init(s);
    System.in.read();
  }


}
