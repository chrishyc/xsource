package spring.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spring.springboot.pojo.Comment;
import spring.springboot.repository.CommentRepository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chris
 */
@Service
public class ApiCommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RedisTemplate redisTemplate;


    // 使用API方式进行缓存：先去缓存中查找，缓存中有，直接返回，没有，查询数据库
    public Comment findCommentById(Integer id){
        Object o = redisTemplate.opsForValue().get("comment_" + id);
        if(o!=null){
            //查询到了数据，直接返回
            return (Comment) o;
        }else {
            //缓存中没有，从数据库查询
            Optional<Comment> byId = commentRepository.findById(id);
            if(byId.isPresent()){
                Comment comment = byId.get();
                //将查询结果存到缓存中，同时还可以设置有效期为1天
                redisTemplate.opsForValue().set("comment_" + id,comment,1, TimeUnit.DAYS);
                return  comment;
            }


        }

        return  null;
    }



    //更新方法
    public Comment updateComment(Comment comment){
        commentRepository.updateComment(comment.getAuthor(),comment.getId());
        //将更新数据进行缓存更新
        redisTemplate.opsForValue().set("comment_" + comment.getId(),comment);
        return comment;
    }

    //删除方法
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
        redisTemplate.delete("comment_" + id);
    }






}
