package jvm;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_static_instance_seq extends parent {


  public T01_load_static_instance_seq() {
    System.out.println("T01_load_static_instance_seq()");
  }

  {
    System.out.println("T01_load_static_instance_seq {}");
  }

  public static void main(String[] args) {
    T01_load_static_instance_seq s = new T01_load_static_instance_seq();
  }
}

class parent {
  {
    System.out.println("parent {}");
  }

  public parent() {
    System.out.println("parent()");
  }
}
