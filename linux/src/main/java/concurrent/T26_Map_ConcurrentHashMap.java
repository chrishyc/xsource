package concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class T26_Map_ConcurrentHashMap {
    public static void main(String[] args) {
        Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
    }
    
    @Test
    public void testConstructor() throws IOException {
        Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
        System.in.read();
    }
    
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
    
    @Test
    public void testPut() {
        Map<String, String> m = new java.util.concurrent.ConcurrentHashMap<>();
        m.put("name", "chris");
    }
    
    /**
     * table = {ConcurrentHashMap$HashEntry[128]@797}
     * count = 86
     * modCount = 86
     * threshold = 96
     * loadFactor = 0.75
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
}
