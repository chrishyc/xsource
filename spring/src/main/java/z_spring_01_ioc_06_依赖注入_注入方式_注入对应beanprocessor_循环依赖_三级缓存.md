#注入方式
```asp
@Autowired:类型注入
@Qualifier:指定名称,单独不能使用,需要和autowired搭配
@Resource:@Resource,不申明则是先通过Name注入,然后通过type注入

```
#注入注解的对应beanprocessor
```asp
1.@Autowired ->  AutowiredAnnotationBeanPostProcessor
2.@Resource  ->  CommonAnnotationBeanPostProcessor
```
#注入时机
postProcessPropertyValues

#循环依赖问题
[](https://zhuanlan.zhihu.com/p/84267654)
##临界知识
对象对外暴露经历的过程:实例化、属性注入
对象提前暴露:实例化后直接暴露,引用逃逸
属性注入:代理类、普通类
##三级缓存
![](.z_spring_01_ioc_05_依赖注入_注入方式_注入对应beanprocessor_循环依赖_三级缓存_images/cbe39dd4.png)
[](https://www.cnblogs.com/semi-sub/p/13548479.html)
[](https://zhuanlan.zhihu.com/p/259255169)
[](https://zhuanlan.zhihu.com/p/84267654)
