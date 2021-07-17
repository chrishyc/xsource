package shiro;

import com.alibaba.druid.pool.DruidDataSource;
import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

/**
 * https://www.w3cschool.cn/shiro/xgj31if4.html
 */
public class ShiroTest {
    @Test
    public void testHelloworld() throws InterruptedException {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("chris", "123456");
        subject.login(token);
        Session session = subject.getSession();
        
        
        System.out.println("session:" + subject.getSession().getId() + ";time:" + session.getTimeout());
        Thread.sleep(10000);
        //6、退出
//        session.stop();
//        subject.logout();
        System.out.println("session1:" + subject.getSession().getId() + ";time:" + session.getTimeout());
    }
    
    @Test
    public void test() {
        
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        
        //设置authenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        
        //设置authorizer
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);
        
        //设置Realm
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/shiro");
        ds.setUsername("root");
        ds.setPassword("00000000");
        
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));
        
        //将SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);
        
        Subject subject = SecurityUtils.getSubject();
        
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
        
        Assert.assertTrue(subject.isAuthenticated());
        
        
    }
}
