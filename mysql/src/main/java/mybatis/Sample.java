package mybatis;

import mybatis.dao.IUserDao;
import mybatis.pojo.User;
import mybatis.sqlSession.SqlSession;
import mybatis.sqlSession.SqlSessionFactory;
import mybatis.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.List;

public class Sample {
    @Test
    public void testQuery() throws Exception {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("张三");


        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        List<User> all = userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }
    }

    @Test
    public void testInsert() throws PropertyVetoException, DocumentException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);
        user.setUsername("张三");
        user.setPassword("333");
        user.setBirthday(new Date());


        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        int ret = userDao.add(user);
        System.out.println(ret);
    }

    @Test
    public void testUpdate() throws PropertyVetoException, DocumentException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);
        user.setUsername("张四");
        user.setPassword("333");
        user.setBirthday(new Date());


        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        int ret = userDao.update(user);
        System.out.println(ret);
    }

    @Test
    public void testDelete() throws PropertyVetoException, DocumentException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);
        user.setUsername("张四");
        user.setPassword("333");
        user.setBirthday(new Date());


        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        int ret = userDao.delete(user);
        System.out.println(ret);
    }

}
