package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_static_arr {


  /**
   * 这段代码里面触发了另一个名为“[T01_load_static.SuperClass”的类的初始化阶段，
   * 它是一个由虚拟机自动生成的、直接继承于java.lang.Object的子类，创建动作由 字节码指令newarray触发
   **/
  public static void main(String[] args) {
    T01_load_static.SuperClass[] arr = new T01_load_static.SuperClass[10];
  }
}
