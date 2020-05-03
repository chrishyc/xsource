package spring.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import spring.springboot.pojo.Comment;
import spring.springboot.repository.CommentRepository;

import java.util.Optional;

/**
 * @author chris
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;



    //@Cacheable:  将该方法查询结果comment存放在springboot默认缓存中
     //cacheNames: 起一个缓存命名空间  对应缓存唯一标识

    // value: 缓存结果  key:默认在只有一个参数的情况下，key值默认就是方法参数值 如果没有参数或者多个参数的情况：simpleKeyGenerate
    // 查询方法
    @Cacheable(cacheNames = "comment",unless = "#result==null")
    public Comment findCommentById(Integer id){
        Optional<Comment> byId = commentRepository.findById(id);
        if(byId.isPresent()){
            Comment comment = byId.get();
            return  comment;
        }
        return  null;
    }



    //更新方法
    @CachePut(cacheNames = "comment",key = "#result.id")
    public Comment updateComment(Comment comment){
        commentRepository.updateComment(comment.getAuthor(),comment.getId());
        return comment;
    }

    //删除方法
    @CacheEvict(cacheNames = "comment")
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
    }






}
