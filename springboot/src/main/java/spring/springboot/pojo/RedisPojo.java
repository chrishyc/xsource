package spring.springboot.pojo;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

/**
 * @author chris
 */
@Data
@RedisHash("persons")
public class RedisPojo {
    @Id
    private String id;
    @Indexed
    private String firstName;
    @Indexed
    private String lastName;
    private RedisAddress address;
}
