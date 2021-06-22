package spring.springboot.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class FilterConfig {
    
    @Bean
    public Filter myFilter() {
        return (request, response, chain) -> System.out.println("hello filter, import by bean");
    }
    
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        System.out.println("shiroFilter=============");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("oauth2", new MimeFilter());
        bean.setFilters(filterMap);
        
        bean.setSecurityManager(securityManager);
        Map<String, String> filterMap1 = new LinkedHashMap<>();
        
        filterMap1.put("/**", "oauth2");
        
        bean.setFilterChainDefinitionMap(filterMap1);
        return bean;
    }
    
    @Bean({"shiroFilter1"})
    public ShiroFilterFactoryBean shiroFilter$1(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        return bean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }
    
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }
    
    public class MimeFilter implements Filter{
    
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("MyFilter=============");
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
    
}
