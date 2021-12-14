#临界知识
贫血模式vs充血模式
#原型
![](.z_spring_01_ioc_04_bean实例化_原型_单例_线程安全_images/83308188.png)
[](https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch04s04.html)
1.原型bean被定义为在每次注入时都会创建一个新的对象
2.原型对象也会经历beanProcessor的处理
3.原型bean不会被容器持有和管理,不会进入容器map
```asp
There is one quite important thing to be aware of when deploying a bean in the prototype scope, in that the lifecycle of
 the bean changes slightly. Spring does not manage the complete lifecycle of a prototype bean: the container instantiates, 
 configures, decorates and otherwise assembles a prototype object, hands it to the client and then has no further knowledge 
 of that prototype instance. This means that while initialization lifecycle callback methods will be called on all objects 
 regardless of scope, in the case of prototypes, any configured destruction lifecycle callbacks will not be called. 
 It is the responsibility of the client code to clean up prototype scoped objects and release any expensive resources 
 that the prototype bean(s) are holding onto. (One possible way to get the Spring container to release resources used by
  prototype-scoped beans is through the use of a custom bean post-processor which would hold a reference to the beans that need to be cleaned up.)

```
#单例
创建

#init方法
@PostConstruct
方法就是初始化后调用的方法
![](.z_spring_01_ioc_04_bean实例化_原型_单例_init方法_destroy方法_images/0490552a.png)
#destroy方法
![](.z_spring_01_ioc_04_bean实例化_原型_单例_init方法_destroy方法_images/86fb62b8.png)
