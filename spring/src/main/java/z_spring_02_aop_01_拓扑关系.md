#核心逻辑对象
[](https://blog.csdn.net/changudeng1992/article/details/80625134)
##aspect切面
##advice
通知，标识逻辑织入的位置,常见的有5种位置
##pointcut
切入点，标识对什么方法进入代理
##advisor
通知器，是通知与切入点的集合
advisor=advice+pointcut
##join point

#核心实现对象
##Advised
已经织入完成的通知者集合，包含在代理对象中，代理对象已经可以直接使用
![](.z_spring_02_aop_01_拓扑关系_images/6bd63a21.png)
advised=list[advisor]
##advice
5个通知器类型
##advisor
具体通知器advisor=advice+pointcut

##MethodInterceptor(CGLIb )
![](.z_spring_02_aop_01_拓扑关系_images/71e3e79e.png)
AspectJAfterAdvice
##MethodInvocation
责任链
##AdvisorAdapter
