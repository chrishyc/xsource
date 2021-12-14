#初始化流程
#容器常见接口
##BeanFactory
getBean
##ApplicationContext
![](.z_spring_01_ioc_01_容器初始化_BeanFactory_ApplicationContext_容器父子关系_环境上下文_资源解析器_bean列表_发布定义事件_images/b6454099.png)

##AbstractApplicationContext
refresh()
##ClassPathXmlApplicationContext
![](.z_spring_01_ioc_01_容器初始化_BeanFactory_接口初始化_images/02bdfb9d.png)
##AnnotationConfigApplicationContext
#启动方式
##springboot
main函数new
##web项目
ContextLoaderListener

#父子层次关系

#环境上下文

#资源解析器

#发布订阅事件
