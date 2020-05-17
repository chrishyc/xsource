package distributed.id;

import org.junit.Test;

import java.util.UUID;

/**
 * 分布式id的几个性能指标:
 * 1.保证生成的 ID 全局唯一
 * 2.整个服务没有单点(单服务器实例崩溃导致分布式崩溃)
 * <p>
 * 3.生成的 ID 最好不大于 64 bits
 * 4.生成 ID 的速度有要求,(例如,在一个高吞吐量的场景中, 需要每秒生成几万个 ID)
 * 5.最好包含时间维度信息,uuid是随机信息，信息利用率不好
 * 6.
 */
public class IdTest {
    /**
     * 可使用周期:16^32=2^128≈3.4 x 10^38≈100亿年/每纳秒产生1兆个UUID
     * <p>
     * 规则:8-4-4-4-12,xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx
     * M表示UUID版本，目前只有五个版本，即只会出现1，2，3，4，5，
     * 数字 N的一至三个最高有效位表示 UUID 变体，目前只会出现8，9，a，b四种情况
     * <p>
     * 版本1(基于时间空间):mac+timestamp,会有安全隐私问题
     * <p>
     * 版本3(基于namespace+url+md5):由用户指定1个namespace和1个具体的字符串，通过MD5散列，来生成1个UUID
     * UUID.nameUUIDFromBytes("myString".getBytes("UTF-8")).toString(),md5碰撞
     * <p>
     * 版本4(基于随机数),有概率重复
     * <p>
     * 版本5(版本3+sha1)
     * <p>
     * uuid不会用作数据库主键:过长,不连续
     */
    @Test
    public void testUUID() {
        
        
        UUID uuid3 = UUID.nameUUIDFromBytes("chris".getBytes());
        System.out.println(uuid3.toString());
        
        
        UUID uuid4 = UUID.randomUUID();
        System.out.println(uuid4.toString());
        
    }
    
    /**
     * 我们可 以单独的创建一个Mysql数据库 DISTRIBUTE_ID，在这个数据库中创建一张表，这张表的ID设置为自增，
     * 其他地方 需要全局唯一ID的时候，就模拟向这个Mysql数据库的这张表中模拟插入一条记录，
     * 此时ID会自 增，然后我们可以通过Mysql的select last_insert_id() 获取到刚刚这张表中自增生成的ID.
     * <p>
     * 局限:性能不好,每秒生产数量少，有单点问题
     */
    @Test
    public void testMysqlAutoId() {
    
    }
    
    
    /**
     * 1.格式:0-00000000 00000000 00000000 00000000 00000000 0-00000000 00-00000000 0000
     * a.1位，不用。二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0
     * b.41位，用来记录时间戳之差（毫秒）,可以设置初始时间，记录的是从初始时间到当前时间的差距.
     * 41位可表示(2^41 - 1)ms≈69年
     * c.10位, datacenterId + workerId，可灵活配置
     * d.12位，序列号，用来记录同毫秒内产生的不同id,可表示2^12-1≈4095个
     *
     * QPS:(2^12 - 1)*1000≈400万
     *
     * 局限
     */
    @Test
    public void testSnowFlake() {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }
    }
    
    @Test
    public void testRedisIncr() {
    
    }
}
