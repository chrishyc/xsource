package distributed.id;

import org.junit.Test;

import java.util.UUID;

/**
 * 分布式id的几个性能指标:
 * 1.保证生成的 ID 全局唯一
 * 2.整个服务没有单点(单服务器实例崩溃导致分布式崩溃)
 *
 * 3.生成的 ID 最好不大于 64 bits
 * 4.生成 ID 的速度有要求,(例如,在一个高吞吐量的场景中, 需要每秒生成几万个 ID)
 * 5.最好包含时间维度信息,uuid是随机信息，信息利用率不好
 * 6.
 */
public class IdTest {
    /**
     * 可使用周期:16^32=2^128≈3.4 x 10^38≈100亿年/每纳秒产生1兆个UUID
     *
     * 规则:8-4-4-4-12,xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx
     * M表示UUID版本，目前只有五个版本，即只会出现1，2，3，4，5，
     * 数字 N的一至三个最高有效位表示 UUID 变体，目前只会出现8，9，a，b四种情况
     *
     * 版本1(基于时间空间):mac+timestamp,会有安全隐私问题
     *
     * 版本3(基于namespace+url+md5):由用户指定1个namespace和1个具体的字符串，通过MD5散列，来生成1个UUID
     * UUID.nameUUIDFromBytes("myString".getBytes("UTF-8")).toString(),md5碰撞
     *
     * 版本4(基于随机数),有概率重复
     *
     * 版本5(版本3+sha1)
     *
     * uuid不会用作数据库主键:过长,不连续
     */
    @Test
    public void testUUID(){
        
    
        UUID uuid3 = UUID.nameUUIDFromBytes("chris".getBytes());
        System.out.println(uuid3.toString());
    
    
        UUID uuid4 = UUID.randomUUID();
        System.out.println(uuid4.toString());
    
    }
    
    /**
     * 我们可 以单独的创建一个Mysql数据库，在这个数据库中创建一张表，这张表的ID设置为自增，
     * 其他地方 需要全局唯一ID的时候，就模拟向这个Mysql数据库的这张表中模拟插入一条记录，
     * 此时ID会自 增，然后我们可以通过Mysql的select last_insert_id() 获取到刚刚这张表中自增生成的ID.
     *
     * 局限:性能不好,每秒生产数量少，有单点问题
     */
    @Test
    public void testMysqlAutoId(){
    
    }
}
