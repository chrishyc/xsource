package concurrent;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class T26_Map_ConcurrentHashMap_18 {
  public static void main(String[] args) {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
  }

  @Before
  public void init() {

  }

  @Test
  public void testConstructor() throws IOException {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>(3);
    System.in.read();
  }


  @Test
  public void testHashMod() {

  }


  /**
   * table初始化
   * initTable()
   * U.compareAndSwapInt(this, SIZECTL, sc, -1)
   */
  @Test
  public void testInitTable() {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    m.put("name", "chris");
  }

  /**
   * table[i]初始化
   * U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE)
   * <p>
   * U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v)
   */
  @Test
  public void initTableElementI() {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    m.put("name", "chris");
  }

  /**
   * table[i]放入第二个元素
   * <p>
   * synchronized (f) + volatile Node<K,V> next;
   * <p>
   * pred.next = new Node<K,V>(hash, key,value, null);
   */
  @Test
  public void testPut() {
    Map<Integer, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    m.put(Integer.parseUnsignedInt("10000000000000000000000000000000", 2), "chris");
    m.put(Integer.parseUnsignedInt("00000000000000001000000000000000", 2), "chris");
  }

  /**
   * table[i]链表超过阈值>=8,进行扩容
   *
   * <64 直接扩容数组
   * >=64
   * @throws IOException
   */
  @Test
  public void testCapacityExpend() throws IOException {
    Map<Integer, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    hashConflict(0, m, new char[32]);
    System.in.read();
  }
  /**
   * table[i]链表超过阈值,转化为红黑树
   *
   * @throws IOException
   */
  @Test
  public void testTreeNode() throws IOException {
    Map<Integer, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    hashConflict(0, m, new char[32]);
    System.in.read();
  }

  @Test
  public void testGet() {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    m.get("name");
  }


  public void hashConflict(int start, Map<Integer, String> m, char[] arr) {
    if (start >= 16) {
      m.put(Integer.parseUnsignedInt(new String(arr), 2), "chris");
      return;
    }
    arr[start] = '1';
    arr[start + 16] = '0';
    hashConflict(start + 1, m, arr);
    arr[start] = '0';
    arr[start + 16] = '1';
    hashConflict(start + 1, m, arr);
  }

  @Test
  public void testCapacityExpandCopyOpt() {

  }

  @Test
  public void testSize() {

  }
}
