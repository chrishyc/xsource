package jvm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T02_compile_03_suger_generic<E> {
  public void test_erase(Object item) {
    //报错,前端编译期拆除
//      if(item instanceof E){
//
//      }
  }

  public void test_raw() {
    ArrayList<Integer> ilist = new ArrayList<Integer>();
    ArrayList<String> slist = new ArrayList<String>();
    /**
     * 裸类型
     */
    ArrayList list; //
    list = ilist;
    list = slist;
  }

  @Test
  public void test_convert() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("hello", "你好");
    map.put("how are you?", "吃了没?");
    /**
     * 编译后,这里会强转
     */
    System.out.println(map.get("hello"));
    System.out.println(map.get("how are you?"));

    /**
     * 不支持原始类型,因为原始类型不能转object
     */
//    ArrayList<int> ilist = new ArrayList<int>();
//    ArrayList<long> llist = new ArrayList<long>();
//    ArrayList list;
//    list = ilist;
//    list = llist;
  }


  public static String method(List<Integer> list) {
    System.out.println("invoke method(List<Integer> list)");
    return "";
  }
}
