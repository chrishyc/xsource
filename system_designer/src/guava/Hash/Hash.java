package guava.Hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Hash {
    
    /**
     * md5,sha,murmur比较
     */
    @Test
    public void test() {
        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.newHasher()
                .putLong(10L)
                .putString("chris", Charsets.UTF_8)
                .hash();
        System.out.println(hc.asLong());
    }
    
    @Test
    public void testMurmurHash(){
        HashFunction hf = Hashing.murmur3_128(); // 32bit version available as well
        HashCode hc = hf.newHasher()
                .putLong(10L)
                .putString("chris", Charsets.UTF_8)
                .hash();
        System.out.println(hc.asLong());
    }
    
    /**
     * https://llimllib.github.io/bloomfilter-tutorial/
     * 1.布隆过滤器容器大小
     * 2.hash位数选择/hash函数个数选择
     * 3.假阳性计算
     */
    @Test
    public void testBloomFilter() {
        int size = 1000000;
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size,0.0001);
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        
        for (int i = 0; i < size; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("有坏人逃脱了");
            }
        }
        
        List<Integer> list = new ArrayList<Integer>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("有误伤的数量：" + list.size());
    }
}
