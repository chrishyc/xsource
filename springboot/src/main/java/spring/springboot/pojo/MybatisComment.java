package spring.springboot.pojo;

import lombok.Data;

/**
 * @author chris
 */
@Data
public class MybatisComment {
    private Integer id;
    private String content;
    private String author;
    private Integer aId;
}
