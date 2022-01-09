package jvm;

public class T03_runtime_02_instance {
  public static void main(String[] args) {
    T t = new T();
    System.out.println(t.count);
  }

  static class T {


    public int count = 2;

    /**
     * 按申明顺序
     */ {
      count = 4;
      int a = 1;
    }

    public T() {
      count++;
    }
  }
}
