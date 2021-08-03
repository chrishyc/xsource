package concurrent;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Hashtable;

public class T26_Map_Hashtable {
    
    private Hashtable<String, String> table;
    
    
    @Before
    public void init() {
        table = new Hashtable<>();
    }
    
    /**
     * HashTable默认的初始大小为11，之后每次扩充为原来的2n+1
     * $A:质数,hash结果分散
     * $D:取模%运算是10进制运算,耗时
     */
    @Test
    public void testHashMod() throws IOException {
        String k = "chris";
        int hash = k.hashCode();
        int tableLen = 11;
        int h = (hash & 0x7FFFFFFF) % tableLen;
        tableLen = tableLen << 1 + 1;
        h = (hash & 0x7FFFFFFF) % tableLen;
        tableLen = tableLen << 1 + 1;
        h = (hash & 0x7FFFFFFF) % tableLen;
        tableLen = tableLen << 1 + 1;
        h = (hash & 0x7FFFFFFF) % tableLen;
        
        for (int i = 0; i < 1500; i++) {
            table.put("chris" + i, "chris");
        }
        System.in.read();
    }
}
