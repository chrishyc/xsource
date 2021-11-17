import org.junit.Test;
import redis.clients.jedis.Jedis;

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
     */
    @Test
    public void testPool() {
    
    }
}
