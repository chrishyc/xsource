package spring.springboot.pojo;

import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;

/**
 * @author chris
 */
@Data
public class RedisAddress {
    @Indexed
    private String city;
    @Indexed
    private String country;
}
