package byteclass.jdk;

public class Apple  {

  public void eat() {
    System.out.println("apple:" + this);
    show();
  }

  public void show() {
    System.out.println("apple:" + this);
    System.out.println("<<<<show method is invoked");
  }
}
