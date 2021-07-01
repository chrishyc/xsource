package spring.springboot.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 1.引入shiro会影响其他项目的某些框架加载吗?
 * 2.引入新shiro会影响其他api path的校验吗?
 * 会影响,限制路径
 * 3.多个ShiroFilterFactoryBean中的filter会互相干扰吗?
 * 会生成多个独立的FactoryShiroFilter,他们的filter互不干扰
 * 但是每个请求都会经过所有的springShiroFilter,每个springShiroFilter的sessionManger
 * cacheManger机制不一样,promehteus进行无状态处理,不进行缓存
 * 其他的springShiroFilter可能缓存promehteus的账号
 * 为了避免多个FactoryShiroFilter验证互相干扰导致递归验证,每个FactoryShiroFilter应该屏蔽其他FactoryShiroFilter的验证路径
 *
 * <p>
 * 4.引入shiro后session存在哪?同一个用户会存几个session?session有缓存吗?缓存在哪?
 * session默认在内存map中,一个用户一个
 * <p>
 * 5.session应该无状态吗?
 * 最好无状态
 * 6.cache是否同一个?
 * cache是原型模型，每个securitymanager一个
 * 7.session是否同一个?
 * 每个securitymanager一个
 * <p>
 * 8.
 * 其他不影响我,其他排除我,我直接被其他人通过，因此我也没有在其他人那里留下缓存
 * 不影响其他，加入指定路径和拦截器，不加缓存，不缓存其他人
 * <p>
 * 9.如果其他系统先缓存session然后再验证,会有系统漏洞
 */
@Configuration
public class ShiroSpringWebConfig {

//    @Bean
//    public Filter myFilter() {
//        return (request, response, chain) -> System.out.println("hello filter, import by bean");
//    }
    
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        System.out.println("shiroFilter=============");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("myAnon", new AnonymousFilter());
        filterMap.put("myBasic", new BasicHttpAuthenticationFilter());
        bean.setFilters(filterMap);
        DefaultWebSecurityManager securityManager = securityManager();
        bean.setSecurityManager(securityManager);
        Map<String, String> filterMap1 = new LinkedHashMap<>();
        filterMap1.put("/springboot", "myAnon");
        filterMap1.put("/list", "myBasic");
//        filterMap1.put("/**", "myBasic");
        
        bean.setFilterChainDefinitionMap(filterMap1);
        return bean;
    }
    
    /**
     * org.apache.catalina.core.ApplicationFilterChain#internalDoFilter递归时
     * ShiroFilterFactoryBean中的filter不会调用递归方法,因此ShiroFilterFactoryBean
     * 作为递归过程最后一个filter
     * <p>
     * <p>
     * 错误,之前使用的是普通filter没有使用chain.doFilter才没有递归下去
     * <p>
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
        
        filterMap1.put("/springboot", "helloFilter");
        
        bean.setFilterChainDefinitionMap(filterMap1);
        return bean;
    }
    
    /**
     * 此方案无效,ShiroFilterFactoryBean中filter逻辑已结束，再次添加并没有添加到对应filter中
     *
     * @param context
     * @return
     */
//    @Bean
//    @ConditionalOnBean(ShiroFilterFactoryBean.class)
//    public Object shiroFilter$2(ApplicationContext context) {
//        System.out.println("shiroFilter$2=============");
//        ShiroFilterFactoryBean shiroFilterFactoryBean = (ShiroFilterFactoryBean) context.getBean("&shiroFilter");
//        shiroFilterFactoryBean.getFilters().put("helloFilter", new MyFilter$1());
//        shiroFilterFactoryBean.getFilterChainDefinitionMap().put("/list", "helloFilter");
//        return new Object();
//    }
    
    /**
     * 此方案有效
     *
     * @param context
     * @return
     */
//    @Bean
//    @ConditionalOnBean(ShiroFilterFactoryBean.class)
//    public Object shiroFilter$3(ApplicationContext context) {
//        System.out.println("shiroFilter$2=============");
//        try {
//            Class clazz = Class.forName("org.apache.shiro.spring.web.ShiroFilterFactoryBean$SpringShiroFilter");
//            System.out.println("clazzName:" + clazz.getName());
//            AbstractShiroFilter filter = (AbstractShiroFilter) context.getBean(clazz);
//            PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) filter.getFilterChainResolver();
//            resolver.getFilterChainManager().getFilters().put("helloFilter", new MyFilter$1());
//            resolver.getFilterChainManager().addToChain("/**", "helloFilter");
//        } catch (ClassNotFoundException e) {
//            System.out.println("error:" + e);
//        }
//        return new Object();
//    }
//
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        securityManager.setSessionManager(defaultSessionManager());
        return securityManager;
    }
    
    @Bean
    public DefaultWebSecurityManager securityManager$1() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm$1());
        securityManager.setSessionManager(new DefaultWebSessionManager());
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultWebSessionStorageEvaluator webSessionStorageEvaluator = (DefaultWebSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
//        webSessionStorageEvaluator.setSessionStorageEnabled(false);
        return securityManager;
    }
    
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
//        customRealm.setAuthenticationTokenClass(MyToken.class);
        return new CustomRealm();
    }
    
    @Bean
    public CustomRealm$1 customRealm$1() {
        CustomRealm$1 customRealm$1 = new CustomRealm$1();
//        customRealm$1.setCachingEnabled(false);
        return customRealm$1;
    }
    
    static public class MyToken implements AuthenticationToken {
        
        @Override
        public Object getPrincipal() {
            return null;
        }
        
        @Override
        public Object getCredentials() {
            return null;
        }
    }
    
    @Bean
    public DefaultWebSessionManager defaultSessionManager() {
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
//        defaultSessionManager.se(false);
        return defaultSessionManager;
    }
    
    
    class MyFilter extends BasicHttpAuthenticationFilter {
        
        @Override
        protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
            return super.isAccessAllowed(request, response, mappedValue);
        }
        
        
        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
            System.out.println("==========MyFilter==========");
            return super.onAccessDenied(request, response);
        }
        
        @Override
        protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
            subject.getSession().stop();
            return super.onLoginSuccess(token, subject, request, response);
        }
    }
    
    class MyFilter$1 extends BasicHttpAuthenticationFilter {
        
        @Override
        protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
            return super.isAccessAllowed(request, response, mappedValue);
        }
        
        
        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
            System.out.println("==========MyFilter111111==========");
            return super.onAccessDenied(request, response);
        }
        
        @Override
        protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
            subject.getSession().stop();
            return super.onLoginSuccess(token, subject, request, response);
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
    
    public class AuthProxyFilter implements Filter {
        
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            String username = servletRequest.getParameter("user");
            String password = servletRequest.getParameter("password");
            if ("/springboot".equals(servletRequest.getRequestURI()) && "chris".equals(username) && "123".equals(password)) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
    }
    
}
