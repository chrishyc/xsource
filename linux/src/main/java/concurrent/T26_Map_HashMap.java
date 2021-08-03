package concurrent;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class T26_Map_HashMap {
    
    private Map<String, String> map;
    
    @Before
    public void init() {
        map = new HashMap<>();
    }
    
    /**
     * 为了加快取模运算,使用二进制运算,例如h&1111
     * 也是因为使用了二进制运算,因此需要以2为倍数扩容
     *
     * $A:二进制取模运算加快了取模速度
     * $D:但导致hash冲突增加,二进制取模主要是截取二进制后几位,未考虑高位,因此即使高位不同也会hash冲突
     *
     * 方案:增加hash 扰动,将高位也考虑进来
     */
    @Test
    public void testHashMod() {
        String k = "chris";
        int h = 0;
        h ^= k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        h ^= h ^ (h >>> 7) ^ (h >>> 4);
        int ret = h & (16 - 1);
    }
    
}
