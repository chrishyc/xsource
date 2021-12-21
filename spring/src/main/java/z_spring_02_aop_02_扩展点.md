#AOP开关
##@EnableAspectJAutoProxy
通过@import注入 处理AOP的BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)
![](.z_spring_02_aop_02_扩展点_images/a428adcd.png)
##<aop:aspectj-autoproxy proxy-target-class="true"/>
使用xml namespace机制,申明aspectj-autoproxy时注入处理AOP的BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)
![](.z_spring_02_aop_02_扩展点_images/cba771cc.png)
![](.z_spring_02_aop_02_扩展点_images/2ef6d7b1.png)

namespace解析位于bean生命周期中的loadBeanDefinitions();
![](.z_spring_01_ioc_00_bean全流程_bean生命周期_images/877daaf6.png)
#扩展点AnnotationAwareAspectJAutoProxyCreator
![](.z_spring_02_aop_01_拓扑关系_images/5ac833b0.png)
![](.z_spring_02_aop_01_拓扑关系_images/aop创建代理对象前的准备工作.jpg)
##postProcessBeforeInstantiation
##postProcessAfterInitialization
