PostProcessorRegistrationDelegate
##1.需求:
后置处理器用于将内部引用回调给外部.内部引用不同对应的后置处理器也不同.

##2.方案:
遍历beanDefinition获取后置处理器beanDefinition,并生成实例对象注入到bean工厂实例map中,然后invoke后置处理器的对应方法.


##3.实现:
1.关键概念:
a.对于后置处理器类型实例化流程为:
构造函数instantiateBean->
设置属性值populateBean->
回调开始->
invokeAwareMethods(BeanFactoryAware)->
invokeAwareInterfaces(setApplicationContext)->
InitializingBean(afterPropertiesSet)->
BeanDefinitionRegistryPostProcessor->
BeanFactoryPostProcessor(postProcessBeanFactory)->


b.触发与注册PostProcessor

工具类:PostProcessorRegistrationDelegate


2.设计模式:

3.语法:
final类
private PostProcessorRegistrationDelegate()构造函数
private static final类

4.命名:
Registration
Delegate
Configurable
Listable
Registry
ordered
clearMetadataCache
register
internal
Checker


