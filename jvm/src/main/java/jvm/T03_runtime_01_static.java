package jvm;

public class T03_runtime_01_static {
  public static void main(String[] args) {
    System.out.println(T.count);
  }

  static class T {


    public static T t = new T();
    public static int count = 2;

    /**
     * 按申明顺序
     */
    static {
      count = 4;
      int a = 1;
    }

    private T() {
      count++;
    }
  }
}
