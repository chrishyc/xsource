package distributed.id;

/**
 * twitter的snowflake算法 -- java实现
 *
 * @author beyond
 * @date 2016/11/26
 */
public class SnowFlake {
    
    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;
    
    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数
    
    /**
     * 每一部分的最大值
     *
     * -1L ^ (-1L << 5L)结果是31，2^5 - 1的结果也是31
     * 所以在代码中，-1L ^ (-1L << 5L)的写法是利用位运算计算出5位能表示的最大正整数是多少,避免
     * 使用指数运算
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    
    /**
     * 步长，用于处理时钟回拨
     */
    private static long stepSize = 2 << 9;
    /** 基础序列号, 每发生一次时钟回拨, basicSequence += stepSize */
    private long basicSequence = 0L;
    
    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    
    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳
    
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }
    
    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        /**
         * 发生时钟huibo
         *
         *在获取当前 Timestamp 时, 如果获取到的时间戳比前一个已生成 ID 的 Timestamp 还要小怎么办?
         * Snowflake 的做法是继续获取当前机器的时间, 直到获取到更大的 Timestamp 才能继续工作
         * (在这个等待过程中, 不能分配出新的 ID)
         *
         * 明确要求 "You should use NTP to keep your system clock accurate".
         * 而且最好把 NTP 配置成不会向后调整的模式. 也就是说, NTP 纠正时间时, 不会向后回拨机器时钟.
         *
         * 1.
         */
        if (currStmp < lastStmp) {
            // 此方案假设该服务QPS不会超过1000*(2^9)
            handleMovedBackwards(currStmp);
//            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增,用mask防止溢出
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = basicSequence;
        }
        
        lastStmp = currStmp;
        
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }
    
    private long handleMovedBackwards(long currStmp) {
        basicSequence += stepSize;
        if (basicSequence == MAX_SEQUENCE + 1) {
            basicSequence = 0;
            currStmp = getNextMill();
        }
        sequence = basicSequence;
        
        lastStmp = currStmp;
        
        return (currStmp - START_STMP) << TIMESTMP_LEFT
                | machineId << DATACENTER_LEFT
                | sequence;
    }
    
    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }
    
    private long getNewstmp() {
        return System.currentTimeMillis();
    }
    
    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }
        
    }
}
