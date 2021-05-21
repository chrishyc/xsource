## 原生方式
1.基本概念
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

[单元测试设计模式](https://www.cnblogs.com/kavenmo/articles/58744.html)
命令模式：TestResult是receiver,TestCase是command,Runner是invoke

组合模式：TestSuite是容器，TestCase是叶子

模板方法模式:TestCase.runBare
## 注解方式
https://www.cnblogs.com/jinsdu/p/4456824.html

https://www.cnblogs.com/jinsdu/p/4709270.html

## 自定义runner
https://www.codenong.com/b-junit-4-custom-runners/

## junit扩展

## junit spring springboot
https://www.yuque.com/binarylei/spring/junit
