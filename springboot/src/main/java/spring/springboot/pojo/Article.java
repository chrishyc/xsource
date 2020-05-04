package spring.springboot.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author chris
 */
@Data
@Entity(name = "t_article")
public class Article {
    @Id
    private Integer id;
    private String title;
    private String content;
    private String created;
    private String modified;
    private String categories;
    private String tags;
    @Column(name = "allow_comment")
    private int allowComment;
    private String thumbnail;
}
