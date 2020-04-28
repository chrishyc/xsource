package spring.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import spring.springboot.pojo.MybatisComment;

/**
 * @author chris
 */
@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM t_comment WHERE id =#{id}")
    MybatisComment findById(Integer id);
}
