package spring.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springboot.pojo.Comment;

/**
 * @author chris
 */
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
