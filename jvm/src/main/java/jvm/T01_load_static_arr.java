package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_static_arr {


  /**
   * 这段代码里面触发了另一个名为“[T01_load_static.SuperClass”的类的初始化阶段，
   * 它是一个由虚拟机自动生成的、直接继承于java.lang.Object的子类，创建动作由 字节码指令newarray触发
   **/
  public static void main(String[] args) {
//    T01_load_static.SuperClass[] arr = new T01_load_static.SuperClass[10];
    int[] arr1 = new int[]{1, 2, 3};
    System.out.println(convert(arr1));
  }

  public static List<Integer> convert(int[] arr) {
    List<Integer> ans = new ArrayList<>();
    if (arr == null || arr.length == 0) return ans;
    for (int i = 0; i < arr.length; i++) {
      int count = 1;
      while (i < arr.length - 1 && arr[i] == arr[i + 1]) {
        count++;
        i++;
      }
      ans.add(count);
      ans.add(arr[i]);
    }
    return ans;
  }
}
