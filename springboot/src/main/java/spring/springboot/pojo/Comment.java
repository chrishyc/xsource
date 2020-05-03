package spring.springboot.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chris
 */
@Data
@Entity(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String author;
    @Column(name = "a_id")
    private Integer aId;
}
