package distributed.id;

import org.junit.Test;

import java.util.UUID;

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
}
