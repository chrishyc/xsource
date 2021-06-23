package spring.springboot.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 1.引入shiro会影响其他项目的某些框架加载吗?
 * 2.引入新shiro会影响其他api path的校验吗?
 *   会影响,限制路径
 * 3.多个ShiroFilterFactoryBean中的filter会互相干扰吗?会生成多个独立的FactoryShiroFilter,他们的filter互不干扰
 *   只能使用一个ShiroFilterFactoryBean，多个无效，不会递归调用
 *
 * 4.引入shiro后session存在哪?同一个用户会存几个session?session有缓存吗?缓存在哪?
 *
 * 5.session应该无状态吗?
 */
@Configuration
public class FilterConfig {

//    @Bean
//    public Filter myFilter() {
//        return (request, response, chain) -> System.out.println("hello filter, import by bean");
//    }
    
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        System.out.println("shiroFilter=============");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("oauth2", new MyFilter());
        bean.setFilters(filterMap);
        
        bean.setSecurityManager(securityManager());
        Map<String, String> filterMap1 = new LinkedHashMap<>();
        
        filterMap1.put("/**", "oauth2");
        
        bean.setFilterChainDefinitionMap(filterMap1);
        return bean;
    }
    
    /**
     * org.apache.catalina.core.ApplicationFilterChain#internalDoFilter递归时
     * ShiroFilterFactoryBean中的filter不会调用递归方法,因此ShiroFilterFactoryBean
     * 作为递归过程最后一个filter
     *
     * FilterRegistrationBean会进行排序,ShiroFilter优先级最低
     *
     * @return
     */
    @Bean({"shiroFilter1"})
    public ShiroFilterFactoryBean shiroFilter$1() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("helloFilter", new MyFilter$1());
        bean.setFilters(filterMap);
        bean.setSecurityManager(securityManager$1());
        Map<String, String> filterMap1 = new LinkedHashMap<>();
        
        filterMap1.put("/**", "helloFilter");
        
        bean.setFilterChainDefinitionMap(filterMap1);
        return bean;
    }
    
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectFactory(new StatelessDefaultSubjectFactory());
        securityManager.setRealm(customRealm());
        return securityManager;
    }
    
    @Bean
    public DefaultWebSecurityManager securityManager$1() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectFactory(new StatelessDefaultSubjectFactory());
        securityManager.setRealm(customRealm$1());
        return securityManager;
    }
    
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }
    
    @Bean
    public CustomRealm$1 customRealm$1() {
        return new CustomRealm$1();
    }
    
    class MyFilter implements Filter{
    
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("==========MyFilter==========");
        }
    }
    
    class MyFilter$1 implements Filter{
        
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("==========MyFilter111111==========");
        }
    }
    
    public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
        public Subject createSubject(SubjectContext context) {
            //不创建 session
//            context.setSessionCreationEnabled(false);
            return super.createSubject(context);
        }
    }
    
    public class CustomRealm extends AuthorizingRealm {
        
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            return null;
        }
        
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
            String username = (String) token.getPrincipal();
            if (!"javaboy".equals(username)) {
                throw new UnknownAccountException("账户不存在!");
            }
            return new SimpleAuthenticationInfo(username, "123", getName());
        }
    }
    
    public class CustomRealm$1 extends AuthorizingRealm {
        
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            return null;
        }
        
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
            String username = (String) token.getPrincipal();
            if (!"chris".equals(username)) {
                throw new UnknownAccountException("账户不存在!");
            }
            return new SimpleAuthenticationInfo(username, "123", getName());
        }
    }
}
