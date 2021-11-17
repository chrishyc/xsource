import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * https://juejin.cn/post/6941292700456976398
 */
public class T_05_redis_connectPool {
    
    /**
     * https://juejin.cn/post/6941292700456976398
     *
     * @throws InterruptedException
     */
    @Test
    public void testNoPool() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = jedis.get("a");
                if (!result.equals("1")) {
                    System.out.println("Expect b to be 2 but found " + result);
                    return;
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = jedis.get("b");
                if (!result.equals("2")) {
                    System.out.println("Expect b to be 2 but found " + result);
                    return;
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }
    
    /**
     * GenericObjectPool.borrowObject
     * JedisCluster会池化很多连接，如果连接超时，会随机选择一个节点重连
     */
    @Test
    public void testPool() {
        String clusterNodes="127.0.0.1:6379;127.0.0.1:6380;127.0.0.1:6381";
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        String[] nodes = clusterNodes.split(";");
        String[] var9 = nodes;
        int var10 = nodes.length;
    
        for(int var11 = 0; var11 < var10; ++var11) {
            String node = var9[var11];
            String[] hostport = node.split(":");
            jedisClusterNodes.add(new HostAndPort(hostport[0], Integer.valueOf(hostport[1])));
        }
    
        JedisCluster jc = new JedisCluster(jedisClusterNodes, 2, 3, 2, null);
    }
}
