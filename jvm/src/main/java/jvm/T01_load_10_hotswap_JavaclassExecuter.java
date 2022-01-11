package jvm;

import java.lang.reflect.Method;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_10_hotswap_JavaclassExecuter  {


  /**
   * 执行外部传过来的代表一个Java类的Byte数组<br>
   * 将输入类的byte数组中代表java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类 * 执行方法为该类的static main(String[] args)方法，输出结果为该类向System.out/err输出的信息
   *
   * @param classByte 代表一个Java类的Byte数组
   * @return 执行结果
   */
  public static String execute(byte[] classByte) {
    T01_load_10_hotswap_HackSystem.clearBuffer();
    T01_load_10_hotswap_ClassModifier cm = new T01_load_10_hotswap_ClassModifier(classByte);
    byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "org/fenixsoft/classloading/execute/HackSy");
    T01_load_10_hotswap_classloader loader = new T01_load_10_hotswap_classloader();
    Class clazz = loader.loadByte(modiBytes);
    try {
      Method method = clazz.getMethod("main", new Class[]{String[].class});
      method.invoke(null, new String[]{null});
    } catch (Throwable e) {
      e.printStackTrace(T01_load_10_hotswap_HackSystem.out);
    }
    return T01_load_10_hotswap_HackSystem.getBufferString();
  }
}
