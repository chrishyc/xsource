package concurrent;

/**
 * javap -v -p
 *
 * private final int a;
 *     descriptor: I
 *     flags: ACC_PRIVATE, ACC_FINAL
 *     ConstantValue: int 1
 */
public class T07_final_memory_barrier {

  private final int a = 1;
}
