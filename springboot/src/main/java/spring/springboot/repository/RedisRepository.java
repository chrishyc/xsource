package spring.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springboot.pojo.RedisPojo;

import java.util.List;

/**
 * @author chris
 */
public interface RedisRepository extends CrudRepository<RedisPojo,String> {
    List<RedisPojo> findAllBy(String city);
}
