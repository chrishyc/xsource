## 原生方式
###基本概念
2.TestCase:测试实现类,每个测试用例方法都生成一个测试实现类的实例
3.TestSuite:测试类集合,把TestCase放入其中
4.结果:TestResult类
5.启动测试类:TestRunner类
6.断言:TestCase继承自该类
7.监听器:TestListener接口
测试运行监听器，通过事件机制处理测试中产生的事件，主要用于测试结果的收集
8.mock

https://blog.csdn.net/chenjiazhu/article/details/78092789
https://my.oschina.net/itblog/blog/1550931
###设计模式
[单元测试设计模式](https://www.cnblogs.com/kavenmo/articles/58744.html)
命令模式：TestResult是receiver,TestCase是command,Runner是invoke

组合模式：TestSuite是容器，TestCase是叶子

模板方法模式:TestCase.runBare




## 注解方式
[](https://www.cnblogs.com/jinsdu/p/4456824.html)
[](https://www.cnblogs.com/jinsdu/p/4709270.html)
[junit设计模型分析](https://my.oschina.net/pangyangyang/blog/153320)

###基本概念:
Client
Request
RunnerBuilder：Runner构造器
RunNotifier：事件通知器
Runner：执行器，@RunWith指定执行器，默认执行器BlockJUnit4ClassRunner
Suit:套件,组合测试
Statement：运行时上下文
lifecycle:before/after生命周期
###功能模型
描述模型:是对要执行的测试用例的描述,Request,测试用例class
运行时模型:JUnit中可执行的模型,TestClass,Statement
结果模型:JUnit中用于描述用例的类,Description,Result,Failure

###设计模式
责任链模式:RunnerBuilder，负责生成runner，AllDefaultPossibilitiesBuilder

工厂方法模式、职责链：用例启动，Client在创建Request后会调用RunnerBuilder（工厂方法的抽象类）来创建Runner，
默认的实现是AllDefaultPosibilitiesBuilder，根据不同的测试类定义（@RunWith的信息）返回Runner。
AllDefaultPosibilitiesBuilder使用职责链模式来创建Runner，部分代码如下。
代码A是AllDefaultPosibilitiesBuilder的主要构造逻辑构造了一个
【IgnoreBuilder->AnnotatedBuilder->SuitMethodBuilder->JUnit3Builder->JUnit4Builder】的职责链
，构造Runner的过程中有且只有一个handler会响应请求。代码B是Junit4Builder类实现会返回一个BlockJUnit4ClassRunner对象
，这个是JUnit4的默认Runner。

外观模式:TestClass

策略模式:TestRule

###


## 自定义runner
https://www.codenong.com/b-junit-4-custom-runners/
SpringJUnit4ClassRunner
MockitoJUnitRunner

## junit5 扩展机制
[官网](https://junit.org/junit5/docs/current/user-guide/#extensions)
Extensions can be registered declaratively via @ExtendWith, programmatically via @RegisterExtension,
or automatically via Java’s ServiceLoader mechanism.
 
在JUnit 5出来之前，我们如果想对JUnit 4 的核心功能进行扩展，往往都会使用自定义Runner 和 @Rule。
自定义Runner 通常是 BlockJUnit4ClassRunner 的子类，用于实现 JUnit 中没有直接提供的某种功能。
eg.spring-test框架的SpringJUnit4ClassRunner, 和mock框架的MockitoJUnitRunner

为了解决 Runner 的限制，JUnit 4.7 引入了 @Rule 。一个测试类可声明多个 @Rule ，
这些规则可在类级别和测试方法级别上运行，但是它只能在测试运行之前或之后执行指定操作。
如果我们想在此之外的时间点进行扩展，@Rule也无法满足我们的要求

基于这一准则，JUnit 5 中定义了许多扩展点，每个扩展点都对应一个接口。我们可以定义自己的扩展可以实现其中的某些接口

 
[简介](https://blog.csdn.net/qq_35448165/article/details/108684339)

[](/Users/chris/workspace/xsource/java/src/main/java/junit/images/junit5扩展回调.png)

###Extension Context
Extension Context包含了测试方法的上下文信息
###Store
###Namespace

## junit spring springboot
https://www.yuque.com/binarylei/spring/junit
[junit4 spring分析](https://www.yuque.com/binarylei/spring/spring-testing)
[](https://juejin.cn/post/6844903897299959822#heading-2)
