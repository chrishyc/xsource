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
[设计模式分析](https://blog.csdn.net/qinyushuang/article/details/54925444)
[](https://www.cnblogs.com/jinsdu/p/4456824.html)
[](https://www.cnblogs.com/jinsdu/p/4709270.html)

###基本概念:
Client
Request
RunnerBuilder：Runner构造器
RunNotifier：事件通知器
Runner：执行器
Statement：运行时上下文
###设计模式
责任链模式:RunnerBuilder，负责生成runner，AllDefaultPossibilitiesBuilder

工厂方法模式、职责链：用例启动，Client在创建Request后会调用RunnerBuilder（工厂方法的抽象类）来创建Runner，
默认的实现是AllDefaultPosibilitiesBuilder，根据不同的测试类定义（@RunWith的信息）返回Runner。
AllDefaultPosibilitiesBuilder使用职责链模式来创建Runner，部分代码如下。
代码A是AllDefaultPosibilitiesBuilder的主要构造逻辑构造了一个
【IgnoreBuilder->AnnotatedBuilder->SuitMethodBuilder->JUnit3Builder->JUnit4Builder】的职责链
，构造Runner的过程中有且只有一个handler会响应请求。代码B是Junit4Builder类实现会返回一个BlockJUnit4ClassRunner对象
，这个是JUnit4的默认Runner。


###


## 自定义runner
https://www.codenong.com/b-junit-4-custom-runners/

## junit扩展

## junit spring springboot
https://www.yuque.com/binarylei/spring/junit
