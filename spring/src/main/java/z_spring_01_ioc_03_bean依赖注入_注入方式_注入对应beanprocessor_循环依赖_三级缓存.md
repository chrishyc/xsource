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
