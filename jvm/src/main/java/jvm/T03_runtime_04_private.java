package jvm;

public class T03_runtime_04_private {
  private T03_runtime_04_private() {
    say();
  }

  private void say() {
    System.out.println("abc");
  }

  public static void main(String[] args) {

  }
}
