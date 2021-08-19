package jvm;

import org.junit.Test;

public class T01_class_instruction_i_add_add {

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
}
