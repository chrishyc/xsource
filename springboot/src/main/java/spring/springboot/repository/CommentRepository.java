package spring.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import spring.springboot.pojo.Comment;

/**
 * @author chris
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update t_comment c set c.author = ?1 where  c.id=?2", nativeQuery = true)
    int updateComment(String author, Integer id);
    
}
