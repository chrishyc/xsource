#容器常见接口
##BeanFactory
getBean
##ApplicationContext
![](.z_spring_01_ioc_01_容器初始化_BeanFactory_ApplicationContext_容器父子关系_环境上下文_资源解析器_bean列表_发布定义事件_images/b6454099.png)
##DefaultListableBeanFactory

##AbstractApplicationContext
refresh()
##ClassPathXmlApplicationContext
![](.z_spring_01_ioc_01_容器初始化_BeanFactory_接口初始化_images/02bdfb9d.png)
```asp
super()->实例化ResourcePatternResolver
refresh()->obtainFreshBeanFactory->loadBeanDefinitions(加载xml)
```
##AnnotationConfigApplicationContext
```asp
this()->实例化AnnotatedBeanDefinitionReader->实例化environment,创建@Conditional评估器,
        注入ConfigurationClassPostProcessor的BeanDefinition,AutowiredAnnotationBeanPostProcessor的BeanDefinition,CommonAnnotationBeanPostProcessor的BeanDefinition
        实例化ClassPathBeanDefinitionScanner扫描器,将@Component加入过滤注解列表中
        
register()->new AnnotationConfigApplicationContext(AppConfig.class);将AppConfig注入BeanDefinition,后序会扫描AppConfig的注解

refresh()->invokeBeanFactoryPostProcessors()->调用ConfigurationClassPostProcessor注入BeanDefinition,调用PropertySourcesPlaceholderConfigurer解析${}
           registerBeanPostProcessors()->实例化this()中注入的BeanPostProcessor的BeanDefinition
```
##
#父子层次关系
