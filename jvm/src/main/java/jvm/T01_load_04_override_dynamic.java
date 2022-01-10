package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_04_override_dynamic {


  static class _360 {
  }

  static class QQ {
  }

  public static class Father {
    public void hardChoice(QQ arg) {
      System.out.println("father choose qq");
    }

    public void hardChoice(_360 arg) {
      System.out.println("father choose 360");
    }
  }

  public static class Son extends Father {
    public void hardChoice(QQ arg) {
      System.out.println("son choose qq");
    }

    public void hardChoice(_360 arg) {
      System.out.println("son choose 360");
    }
  }

  public static void main(String[] args) {
    Father father = new Father();
    Father son = new Son();
    father.hardChoice(new _360());
    son.hardChoice(new QQ());
  }
}
