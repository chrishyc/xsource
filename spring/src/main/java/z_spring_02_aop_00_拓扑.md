#应用场景
如事务管理、安全检查、缓存、对象池管理等
AOP 实现的关键就在于 AOP 框架自动创建的 AOP 代理
AOP 代理则可分为静态代理和动态代理两大类，其中静态代理是指使用 AOP 框架提供的命令进行编译，从而在编译阶段就可生成 AOP 代理类，
因此也称为编译时增强；而动态代理则在运行时借助于 JDK 动态代理、CGLIB 等在内存中”临时”生成 AOP 动态代理类，因此也被称为运行时增强
#拓扑
##Aspect
##Pointcut
##Advice
##AnnotationAwareAspectJAutoProxyCreator
面配置文件中的 AnnotationAwareAspectJAutoProxyCreator 是一个 Bean 后处理器（BeanPostProcessor），该 Bean 后处理器将会为容器中 Bean 生成 AOP 代理
