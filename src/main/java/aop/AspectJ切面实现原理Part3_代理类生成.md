AspectJ切面实现原理Part3_代理类生成
##1.需求
找到候选bean的所有advisor,生成代理类
##2.方案


##3.实现

###类型
工具类:AopUtils
实体类:
PointcutAdvisor
ReflectionFastMatchInfo

###设计
ClassFilter
ExposeInvocationInterceptor
![](ExposeInvocationInterceptor.png)
###ProxyFactory
![](/Users/chris/xsource/uml/spring_aop/ProxyFactory.png
)
getCallbacks

###语法
private static final ThreadLocal<String> currentProxiedBeanName =
			new NamedThreadLocal<>("Name of currently proxied bean");

try finally

Advisor... advisors

###命名
obtainPointcutExpression
ReflectionFastMatchInfo
eligibleAdvisors
isFrozen