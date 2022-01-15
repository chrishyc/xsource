package jvm;

import org.junit.Test;

import java.util.Calendar;

/**
 * -XX:+TraceClassLoading
 */
public class T01_load_11_unload_class {


  public static void main(String[] args) throws Exception {
    unload();
    System.gc();//实际场景不这么用
    Thread.sleep(10000);

  }

  @Test
  public void test(){
    long curTime = System.currentTimeMillis();
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    System.out.println(calendar.getTimeInMillis());
  }

  public static void unload() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    ClassLoader userClassLoader = new ClassLoader() {
    };
    Class<?> clazz = userClassLoader.loadClass("jvm.T01_load_01_override");
    Object object = clazz.newInstance();
    System.out.println("class HashCode: " + clazz.hashCode());
    System.out.println(object.getClass().getClassLoader());
    System.out.println("-----------------------");
    //让类和自定义类加载器不再互相引用


    userClassLoader = new ClassLoader() {
    };
    clazz = userClassLoader.loadClass("jvm.T01_load_01_override");//不能是当前类
    object = clazz.newInstance();
    System.out.println("class HashCode: " + clazz.hashCode());
    System.out.println(object.getClass().getClassLoader());
    userClassLoader = null;
  }
}
