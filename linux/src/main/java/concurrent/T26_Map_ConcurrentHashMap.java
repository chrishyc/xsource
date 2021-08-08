package concurrent;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class T26_Map_ConcurrentHashMap {
  public static void main(String[] args) {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
  }

  @Before
  public void init() {

  }

  @Test
  public void testConstructor() throws IOException {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    System.in.read();
  }

  /**
   * 为了加快取模运算,使用二进制运算,例如h&1111
   * 也是因为使用了二进制运算,因此需要以2为倍数扩容
   * <p>
   * $A:二进制取模运算加快了取模速度
   * $D:但导致hash冲突增加,二进制取模主要是截取二进制后几位,未考虑高位,因此即使高位不同也会hash冲突
   * <p>
   * 方案:增加hash 扰动,将高位也考虑进来
   */
  @Test
  public void testHashMod() {
    String k = "chris";
    int h = 0;
    h ^= k.hashCode();
    h += (h << 15) ^ 0xffffcd7d;
    h ^= (h >>> 10);
    h += (h << 3);
    h ^= (h >>> 6);
    h += (h << 2) + (h << 14);
    System.out.println(h ^ (h >>> 16));
  }

  /**
   * hash高4位作为segment的hash索引
   * 低4位作为segment中table的索引
   * <p>
   * segment初始化,cas赋值
   * UNSAFE.getObjectVolatile  +   UNSAFE.compareAndSwapObject
   * <p>
   * 抢segment锁,64次后阻塞
   * <p>
   * 插入新节点:头结点更改,lock+putOrderedObject
   * <p>
   * segment table扩容:2倍扩容,拷贝优化
   */
  @Test
  public void testPut() {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    m.put("name", "chris");
  }

  /**
   * UNSAFE.getObjectVolatile
   * 无锁,数据能保证一致性
   * put中rehash过程,创建newTable,将数据复制到newTable,然后将newTable赋值给table,而table是volatile类型,过程和COW类似
   * <p>
   * transient volatile HashEntry<K,V>[] table;
   * <p>
   * volatile V value;
   * volatile HashEntry<K,V> next;
   */
  @Test
  public void testGet() {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    m.get("name");
  }

  /**
   * table = {ConcurrentHashMap$HashEntry[128]@797}
   * count = 86
   * modCount = 86
   * threshold = 96
   * loadFactor = 0.75
   * <p>
   * count>tab.length*load.factor时,segment会进行扩展
   * segment.len=segment.len<<1;扩展为2倍,主要为了方便二进制取模运算
   * 例如:2^n -1= 1111111..111, 10101100111 & 11111 = 111
   *
   * @throws IOException
   */
  @Test
  public void testCapacityExpand() throws IOException {
    Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    for (int i = 0; i < 1500; i++) {
      m.put("name" + i, "chris");
    }
    System.in.read();
  }

  @Test
  public void testCapacityExpandCopyOpt() {

  }
}
