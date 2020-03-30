package start;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Test
    public void test9() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            List<User> list = userDao.findAllWithOrders();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testCache1() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            List<User> list = userDao.findByCondition(user);
            System.out.println(list);
            List<User> list1 = userDao.findByCondition(user);
            System.out.println(list1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * sqlsession一级
     * <p>
     * 一级缓存的脏读
     * mapper一级
     *
     * @throws IOException
     */
    @Test
    public void testCache2() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
//        SqlSession sqlSession3 = sqlSessionFactory.openSession(true);
        try {
            User test = new User();
            test.setId(1);

            IUserDao userDao = sqlSession1.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            List<User> list = userDao.findByCondition(test);
            System.out.println("before update:" + list);
//            sqlSession1.close();

//            IUserDao userDao3 = sqlSession3.getMapper(IUserDao.class);
//            user.setUsername("ccc");
//            userDao3.update(user);
//            System.out.println();

            IUserDao userDao2 = sqlSession2.getMapper(IUserDao.class);
            List<User> list1 = userDao2.findByCondition(user);
            user.setUsername("*****");
            userDao2.update(user);


            System.out.println("user1 after update:" + userDao.findByCondition(test));

            System.out.println("user2 after update:" + userDao2.findByCondition(test));


//            System.out.println("after update:" + list1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            sqlSession2.close();
        }
    }

    /**
     * mybatis二级缓存
     * commit
     *
     * @throws IOException
     */
    @Test
    public void testCache3() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        try {
            IUserDao userDao = sqlSession1.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            List<User> list = userDao.findByCondition(user);
            System.out.println(list);
            // 二级缓存只有commit后才会生效
            sqlSession1.commit();

            IUserDao userDao2 = sqlSession2.getMapper(IUserDao.class);
            List<User> list1 = userDao2.findByCondition(user);
            System.out.println(list1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession1.close();
            sqlSession2.close();
        }
    }

    /**
     * mybatis二级缓存
     * update
     *
     * @throws IOException
     */
    @Test
    public void testCache4() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 这里autoCommit和二级缓存sqlsession二级缓存commit是两码事
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession3 = sqlSessionFactory.openSession(true);
        try {
            IUserDao userDao = sqlSession1.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            List<User> list = userDao.findByCondition(user);
            System.out.println(list);
            // 二级缓存只有commit后才会生效
            sqlSession1.commit();

            IUserDao userDao2 = sqlSession2.getMapper(IUserDao.class);
            List<User> list1 = userDao2.findByCondition(user);
            System.out.println("after commit:" + list1);

            IUserDao userDao3 = sqlSession3.getMapper(IUserDao.class);
            User test = new User();
            test.setId(1);
            test.setPassword("$%^$^U&U((*^%$");
            // 会设置二级缓存无效
            userDao3.update(test);

            // 清除对应的二级缓存
            sqlSession3.commit();


            List<User> list3 = userDao2.findByCondition(user);
            System.out.println("after update:" + list3);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession1.close();
            sqlSession2.close();
        }
    }

    /**
     * mybatis-redis缓存
     *
     * @throws IOException
     */
    @Test
    public void testCache5() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession3 = sqlSessionFactory.openSession(true);
        try {
            IUserDao userDao = sqlSession1.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            List<User> list = userDao.findByCondition(user);
            System.out.println(list);
            sqlSession1.close();

            IUserDao userDao3 = sqlSession3.getMapper(IUserDao.class);
            user.setUsername("ccc");
            userDao3.update(user);
            System.out.println();

            IUserDao userDao2 = sqlSession2.getMapper(IUserDao.class);
            List<User> list1 = userDao2.findByCondition(user);
            System.out.println(list1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession2.close();
        }
    }

    @Test
    public void testInterceptor() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        try {
            IUserDao userDao = sqlSession1.getMapper(IUserDao.class);
            User user = new User();
            user.setId(1);
            List<User> list = userDao.findByCondition(user);
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession1.close();
        }
    }

    @Test
    public void pageHelperTest() throws IOException {

        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        try {

            PageHelper.startPage(1, 1);

            IUserDao userDao = sqlSession1.getMapper(IUserDao.class);


            List<User> list = userDao.findAll();
            System.out.println(list);


            PageInfo<User> pageInfo = new PageInfo<>(list);
            System.out.println("总条数：" + pageInfo.getTotal());
            System.out.println("总页数：" + pageInfo.getPages());
            System.out.println("当前页：" + pageInfo.getPageNum());
            System.out.println("每页显示的条数：" + pageInfo.getPageSize());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession1.close();
        }
    }

}
