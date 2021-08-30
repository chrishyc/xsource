AspectJ切面实现原理Part2_Advisor生成
##1.需求
使用BeanPostProcessor对bean容器实例进行处理,将处理完的实例返回给容器做了目标实例使用
##2.方案
使用@Import Registrar注入BeanPostProcessor#AnnotationAwareAspectJAutoProxyCreator
用于为候选实例对象生成代理对象
1.BeanPostProcessor处理实例对象时,会获取所有注解@Aspect的类,将其中有@Before,@AfterReturning等注解的方法元数据缓存为advisor.需要从容器检索@AspectJ的bean,以及将相应的方法生成为advisor
2.在对每个类进行BeanPostProcessor处理时,判断该类是否有方法匹配advisor缓存,如果匹配到,将匹配到的advisor缓存放入拦截器,用于逻辑增强
3.增强时机有@Before,@AfterReturning等类别,执行时通过迭代器+装饰器模式实现不同增强时机的逻辑.
5.将生成的代理类返回给容器工厂

##3.实现
advice:增强逻辑
```
 <tx:advice id="txAdvice" transaction-manager="transactionManager1"/>
```
pointcut切面
```
<aop:pointcut id="transactionPointcut" expression="execution(* com.sychine.tower.service..*Impl.*(..))"/>
```

advisor,增强逻辑+切面
```
 <aop:advisor advice-ref="txAdvice" pointcut="execution(* tx.xml.XmlService.*(..))"/>
```

###BeanFactoryAspectJAdvisorsBuilderAdapter检索工具类设计
@AspectJ工具类,从容器检索@AspectJ注解bean
BeanFactoryAdvisorRetrievalHelper.findAdvisorBeans
BeanFactoryAspectJAdvisorsBuilder.buildAspectJAdvisors,
查找有@AspectJ的class,并查找其中有这几个注解的方法
```
private static final Class<?>[] ASPECTJ_ANNOTATION_CLASSES = new Class<?>[] {
			Pointcut.class, Around.class, Before.class, After.class, AfterReturning.class, AfterThrowing.class};
```

AspectJAnnotationParameterNameDiscoverer

###ReflectiveAspectJAdvisorFactory,BeanFactoryAspectInstanceFactory对象工厂类设计
getAdvisor
AspectJAdvisor对象工厂类
![](/Users/chris/xsource/uml/spring_aop/ReflectiveAspectJAdvisorFactory.png)
LazySingletonAspectInstanceFactoryDecorator


###Advisor设计

![](/Users/chris/xsource/uml/spring_aop/AspectJExpressionPointcut.png)
![](/Users/chris/xsource/uml/spring_aop/AspectJAroundAdvice.png)
![](/Users/chris/xsource/uml/spring_aop/InstantiationModelAwarePointcutAdvisorImpl.png)
InstantiationModelAwarePointcutAdvisorImpl
AspectJExpressionPointcut
AspectJAroundAdvice
JoinPoint

##类型
工厂类:ReflectiveAspectJAdvisorFactory
实体类:
AspectJAroundAdvice,
AspectJMethodBeforeAdvice,
AspectJAfterAdvice,
AspectJAfterThrowingAdvice,
InstantiationModelAwarePointcutAdvisorImpl
AspectJExpressionPointcut

##语法
ConcurrentHashMap
volatile
transient

##设计模式
空实现:private static final Advice EMPTY_ADVICE = new Advice() {};



##命名
Infrastructure
Conversion
Converting
ClassFilter
proceed
Discoverer
argumentsIntrospected
aspectFactoryCache
isEligibleBean
validate
lazySingleton
InstantiationModelAware
AspectJExpression
instantiate
Synthetic
Default
isLazilyInstantiated