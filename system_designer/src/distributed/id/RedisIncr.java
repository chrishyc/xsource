package distributed.id;

import redis.clients.jedis.Jedis;

public class RedisIncr {
    public static long nextId() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        try {
            long id = jedis.incr("id");
            System.out.println("从redis中获取的分布式id为:" + id);
            return id;
        } finally {
            jedis.close();
        }
    }
}
