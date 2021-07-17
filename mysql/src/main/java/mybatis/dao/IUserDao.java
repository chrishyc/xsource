package mybatis.dao;


import mybatis.pojo.User;

import java.io.IOException;
import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws IOException;

    //多条件组合查询：演示if
    public List<User> findByCondition(User user);


    //多值查询：演示foreach
    public List<User> findByIds(int[] ids);

    public int add(User user);

    public int update(User user);

    public int delete(User user);

}
