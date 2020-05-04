package spring.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springboot.pojo.Article;

/**
 * @author chris
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
