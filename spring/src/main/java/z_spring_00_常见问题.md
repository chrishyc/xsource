#谈谈Spring IOC的理解，原理与实现?
##思路
```asp
2,总:

控制反转(IOC,应用和框架角度):理论思想,原来的对象是由使用者来进行控制，有了spring之后，可以把整个对象交给spring来帮我们进行管理
依赖注入(对象角度):DI,把对应的属性的值注入到具体的对象中,(举例常见依赖注入,抛出技术点上钩,@Autowired,@Qualifier,@Resource,@Value)

8,分:

1、一般聊ioc容器的时候要涉及到容器的创建过程（beanFactory,DefaultListableBeanFactory）,向bean工厂中设置一些参数（BeanPostProcessor,Aware接口的子类）等等属性
2、加载解析bean对象，准备要创建的bean对象的定义对象beanDefinition,(xml或者注解的解析过程)
3、beanFactoryPostProcessor的处理，此处是扩展点，PlaceHolderConfigurSupport,ConfigurationClassPostProcessor
4、BeanPostProcessor的注册功能，方便后续对bean对象完成具体的扩展功能
5、通过反射的方式讲BeanDefinition对象实例化成具体的bean对象
6、bean对象的初始化过程（填充属性，调用aware子类的方法，调用BeanPostProcessor前置处理方法，调用init-mehtod方法，调用BeanPostProcessor的后置处理方法）
7、生成完整的bean对象，通过getBean方法可以直接获取,(循环引用)
8、销毁过程
9、面试官，这是我对ioc的整体理解，包含了一些详细的处理过程，您看一下有什么问题，可以指点我一下（允许你把整个流程说完）
```

##技术点
###常见依赖注入注解
```asp
1.哪里能用
2.注入时使用的标识符
```
@Autowired

@Qualifier

@Resource

@Value


###容器创建的关键类和接口
```asp
beanFactory
DefaultListableBeanFactory


ClassPathXmlApplicationContext
AnnotationConfigApplicationContext
AnnotationConfigServletWebServerApplicationContext
```

###bean解析的关键类和接口
```asp

```

###beanFactoryPostProcessor常见扩展

###BeanPostProcessor常见举例

###bean对象的初始化过程

#谈谈IOC的底层实现
##思路
1.工作原理
2.源码过程
3.数据结构
4.设计模式
5.设计思想

```asp
总:创建beanFactory,解析xml、注解生成beanDefinition,然后通过反射实例化对象,然后BeanPostProcessor扩展点完成注入,然后初始化,initializingBean

```
##技术点
###工作原理
反射
##源码
createBeanFactory，
getBean,
doGetBean,
createBean,
doCreateBean,
createBeanInstance(getDeclaredConstructor,newinstance),
populateBean,
initializingBean
```asp
1、先通过createBeanFactory创建出一个Bean工厂（DefaultListableBeanFactory）

2、开始循环创建对象，因为容器中的bean默认都是单例的，所以优先通过getBean,doGetBean从容器中查找，找不到的话，

3、通过createBean,doCreateBean方法，以反射的方式创建对象，一般情况下使用的是无参的构造方法（getDeclaredConstructor，newInstance）

4、进行对象的属性填充populateBean

5、进行其他的初始化操作（initializingBean）
```

##数据结构
使用三级缓存,concurrentHashMap
##设计模式
单例,工厂模式

#描述一下bean生命周期
```asp
背图：记住图中的流程

在表述的时候不要只说图中有的关键点，要学会扩展描述

1、实例化bean：反射的方式生成对象

2、填充bean的属性：populateBean(),循环依赖的问题（三级缓存）

3、调用aware接口相关的方法：invokeAwareMethod(完成BeanName,BeanFactory,BeanClassLoader对象的属性设置)

4、调用BeanPostProcessor中的前置处理方法：使用比较多的有（ApplicationContextPostProcessor,设置ApplicationContext,Environment,ResourceLoader,EmbeddValueResolver等对象）

5、调用initmethod方法：invokeInitmethod(),判断是否实现了initializingBean接口，如果有，调用afterPropertiesSet方法，没有就不调用

6、调用BeanPostProcessor的后置处理方法：spring的aop就是在此处实现的，AbstractAutoProxyCreator

​		注册Destuction相关的回调接口：钩子函数

7、获取到完整的对象，可以通过getBean的方式来进行对象的获取

8、销毁流程，1；判断是否实现了DispoableBean接口，2，调用destroyMethod方法
```
