package concurrent;

/**
 * store 写，load读
 * 会导致
 */
public class T07_Volatile_memory_barrier_StoreLoad {
  public static class SomeThing {
    private int status;

    public SomeThing() {
      status = 1;
    }

    public int getStatus() {
      return status;
    }
  }

  private volatile SomeThing object;

  public void create() {
    object = new SomeThing();
  }

  public SomeThing get() {
    while (object == null) {
      Thread.yield(); //不加这句话可能会在此出现无限循环
    }
    return object;
  }

}
