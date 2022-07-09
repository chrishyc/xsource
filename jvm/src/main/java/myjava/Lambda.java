package myjava;

public class Lambda {
  public static void main(String[] args) {
    // invokedynamic,不会产生新的类,Lambda表达式被封装成了主类的一个私有方法，并通过invokedynamic指令进行调用
    new Thread(()-> System.out.println("this is a lambda demo"));
    // invokespecial,
    new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("this is an Anonymous class demo");
      }
    });
  }
}
