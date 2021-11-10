public class A_02_CountBloomFilter<E> extends A_01_BloomFilter1<E> {
    protected int count[];
    protected int c_m = 0; // 当前使用的位数
    
    public A_02_CountBloomFilter(int m, int n_max, int k) {
        super(m, n_max, k);
        this.count = new int[super.m];
    }
    
    public A_02_CountBloomFilter(double fpp, int n_max) {
        super(fpp, n_max);
        this.count = new int[super.m];
    }
    
    @Override
    public void add(byte[] bytes) {
        int[] hashes = createHashes(bytes, k);
        for (int hash : hashes) {
            bitset.set(Math.abs(hash % m), true); //使用K个Hash函数映射到1位
            if (count[Math.abs(hash % m)] == 0) {
                c_m++;
            }
            count[Math.abs(hash % m)]++;
        }
        n++;//添加了一个元素
    }
    
    /**
     * 移除元素
     *
     * @param element
     */
    public void remove(E element) {
        if (element != null) {
            remove(element.toString().getBytes(charset));
        }
    }
    
    /**
     * 移除字节数组
     *
     * @param bytes
     */
    public void remove(byte[] bytes) {
        int[] hashes = createHashes(bytes, k);
        for (int hash : hashes) {
            if (--count[Math.abs(hash % m)] == 0) { //如果数据为空，则将标志位也归位
                c_m--;
                bitset.clear(Math.abs(hash % m));
            }
        }
        n--;
    }
    
    @Override
    public void clear() {
        this.count = new int[super.m];
        super.clear();
    }
    
    public int[] getCount() {
        return this.count;
    }
}
