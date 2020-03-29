package start.dao;


import demo.pojo.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws IOException;

    //多条件组合查询：演示if
    public List<User> findByCondition(User user);


    //多值查询：演示foreach
    public List<User> findByIds(int[] ids);

    public List<User> findByIdsAndName(Map<String, Object> param);

    public long insert(User user);

    public long update(User user);

    public long delete(int id);
}
