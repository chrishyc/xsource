package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_01_override {


  static abstract class Human {
  }

  static class Man extends Human {
  }

  static class Woman extends Human {
  }

  public void sayHello(Human guy) {
    System.out.println("hello,guy!");
  }

  public void sayHello(Man guy) {
    System.out.println("hello,gentleman!");
  }

  public void sayHello(Woman guy) {
    System.out.println("hello,lady!");
  }

  private void privateMethod() {

  }

  public final void finalMethod() {

  }

  public static void main(String[] args) {
    Human man = new Man();
    Human woman = new Woman();
    T01_load_01_override sr = new T01_load_01_override();
    sr.sayHello(man);
    sr.sayHello(woman);
    sr.privateMethod();
    sr.finalMethod();
  }
}
