package start;

import demo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import start.dao.IOrderDao;
import start.dao.IUserDao;
import start.pojo.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    @Test
    public void test1() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            List<User> list = userDao.findAll();
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test2() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            User user = new User();
            user.setId(2);
            user.setUsername("chris");
            user.setPassword("qwepoi");
            long ret = userDao.insert(user);
            System.out.println(ret);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test3() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            User user = new User();
            user.setId(2);
            user.setUsername("chris2");
            user.setPassword("222222");
            long ret = userDao.update(user);
            System.out.println(ret);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test4() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            User user = new User();
            user.setId(2);
            user.setUsername("chris2");
            user.setPassword("222222");
            long ret = userDao.delete(user.getId());
            System.out.println(ret);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test5() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            user.setUsername("User1");
//            user.setPassword("222222");
            List<User> list = userDao.findByCondition(user);
            System.out.println(list);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test6() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            int[] ids = new int[]{1, 2, 5};
            List<User> list = userDao.findByIds(ids);
            System.out.println(list);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test7() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            Map<String, Object> map = new HashMap<>();
            map.put("chris", "User1");
            int[] ids = new int[]{1, 2, 5};
            map.put("ids", ids);
            List<User> list = userDao.findByIdsAndName(map);
            System.out.println(list);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test8() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            IOrderDao orderDao = sqlSession.getMapper(IOrderDao.class);
            List<Order> list = orderDao.findAll();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
