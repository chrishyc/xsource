##概念模型
![](https://shiro.apache.org/assets/images/ShiroFeatures.png)
对象:当前用户(subject),数据源(realm),会话周期,资源权限
操作:身份认证,会话管理,资源权限控制

Authentication:身份认证,principals,principals

Authorization:资源授权

Session Manager:会话管理

Cryptography:加密

Caching:缓存

Concurrency:并发

##实现模型
![](https://shiro.apache.org/assets/images/ShiroBasicArchitecture.png)
Subject:当前用户

SecurityManager:facade控制器,subject+realm作为入参被SecurityManager验证,相当于 SpringMVC 中的 DispatcherServlet 
或者 Struts2 中的 FilterDispatcher;是 Shiro 的心脏;所有具体的交互都通过 SecurityManager 进行控制;
它管 理着所有 Subject、且负责进行认证和授权、及会话、缓存的管理

Realm:数据源,以认为是安全实体数据源，即用于获取安全实体的; 可以是 JDBC 实现，也可以是 LDAP 实现，或者内存实现等

![](https://shiro.apache.org/assets/images/ShiroArchitecture.png)

Authenticator:认证器，负责主体认证的，这是一个扩展点，如果用户觉得 Shiro 默认的 不好，可以自定义实现;其需要认证策略
(Authentication Strategy)，即什么情况下算用户 认证通过了


Authrizer:授权器，或者访问控制器，用来决定主体是否有权限进行相应的操作;即控制 着用户能访问应用中的哪些功能;


SessionManager:如果写过 Servlet 就应该知道 Session 的概念，Session 呢需要有人去管理 它的生命周期，这个组件就是 SessionManager

SessionDAO:DAO 大家都用过，数据访问对象，用于会话的 CRUD，比如我们想把 Session 保存到数据库，那么可以实现自己的 SessionDAO，
通过如 JDBC 写到数据库;比如想把 Session放到Memcached中，可以实现自己的Memcached SessionDAO;另外SessionDAO 中可以使用 Cache 进行缓存

CacheManager:缓存控制器，来管理如用户、角色、权限等的缓存的;因为这些数据基本 上很少去改变，放到缓存中后可以提高访问的性能

Cryptography:密码模块，Shiro 提高了一些常见的加密组件用于如密码加密/解密的。
![](/Users/chris/workspace/xsource/auth/src/main/resources/images/shiro_authenticator.png)


##当前线程解绑?

##shiro组件间的关系,shiro-core,shiro-web,shiro-spring的区别?
###shiro-web
shiro-web引入了javax.servlet.api
shiro实现了ServletContextListener，Filter，shiro的配置文件shiro.ini和门面SecurityManager,
在ServletContextListener和Filter执行时初始化
```
<listener>
        <listener-class>org.apache.shiro.web.env.EnvironmentLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>shiroEnvironmentClass</param-name>
        <param-value>org.apache.shiro.web.env.IniWebEnvironment</param-value><!-- 默认先从/WEB-INF/shiro.ini，如果没有找classpath:shiro.ini -->
    </context-param>
    <context-param>
        <param-name>shiroConfigLocations</param-name>
        <param-value>classpath:shiro.ini</param-value>
    </context-param>
    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.apache.shiro.web.servlet.ShiroFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```
###shiro-spring
###filter代理
DelegatingFilterProxy, DelegatingFilterProxy就是一个对于servlet filter的代理，用这个类的好处主要是通过Spring容器来管理servlet filter的生命周期
[](https://blog.csdn.net/fly910905/article/details/95062258)

其实Spring中，web应用启动的顺序是：listener->filter->servlet，先初始化listener，然后再来就filter的初始化，再接着才到我们的dispathServlet的初始化
```
@Configuration
public class WebConfig {
 
  @Bean
    public Filter tokenAuthFilter() {
 
        return new TokenAuthFilter();
    }
    /**
     * 注册filter，统一处理api开头的请求
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean tokenAuthFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // DelegatingFilterProxy把servlet 容器中的filter同spring容器中的bean关联起来
        registration.setFilter(new DelegatingFilterProxy("tokenAuthFilter"));
        registration.addUrlPatterns("/api/*");
        registration.setName("tokenAuthFilter");
        registration.setOrder(1);
        return registration;
    }
 
}

```
###FactoryBean
ShiroFilterFactoryBean
```
public interface FactoryBean<T> {

	@Nullable
	T getObject() throws Exception;

	@Nullable
	Class<?> getObjectType();

	default boolean isSingleton() {
		return true;
	}

```

###spring添加filter的三种方式
[](https://stackoverflow.com/questions/19825946/how-can-i-add-a-filter-class-in-spring-boot)
```
There are three ways to add your filter,

Annotate your filter with one of the Spring stereotypes such as @Component
Register a @Bean with Filter type in Spring @Configuration
Register a @Bean with FilterRegistrationBean type in Spring @Configuration
```

##springboot filter添加servletcontext源码
[](https://www.cnblogs.com/youzhibing/p/9866690.html)


##shiro filter认证流程
![](/Users/chris/workspace/xsource/auth/src/main/java/kerberos/shiro_filter_auth.png)
1.session获取
2.session存储


subject session关系
threadcontext绑定解绑
session在哪?
securty线程
获取当前线程subject

shiro

https://blog.csdn.net/lblblblblzdx/article/details/87001023

https://juejin.cn/post/6844903493480906766

0.shirofilter创建 final Subject subject = createSubject(request, response);解析session是否auth，
，subjectcontext创建，上下文工具类，负责从resolvePrincipals()中获取用户身份,从resolveAuthenticated获取用户身份认证
，获取session放入subjectcontext，null为空时设置为空,获取sessionid,从retrieveSessionFromDataSource中获取

1.subject.execute绑定 subject注入线程
2.call,执行链路

3.


4.subject被后面的subject顶替,同一个线程


5.身份认证，子filter,subject.login,验证token

6.验证通过，在当前线程subjectcontext记录auth=true，subjectcontext有map记录

7.创建subject,拷贝subjectcontext到subject，把subject的信息回写session,createSubject#save



filter 
url关系

filter和requst关系


外部filter 

内部filter

session


session subjuct关系更新在内部filter中，外部filter不处理


get url



session不存在？

shiro自己的session ，http的session

session生成和使用时机，业务url请求
session加密?session管理
