package jvm;

public class T03_runtime_03_instruction {
  int a = 1;
  int b = 2;

  public T03_runtime_03_instruction() {
    b++;
  }

  public static void main(String[] args) {
    int i = 8;
    i = i++;
    System.out.println(i);
  }
}
