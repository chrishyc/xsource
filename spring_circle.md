
### 分析spring循环依赖处理机制中的调用关系

#### 问题
循环依赖是指A依赖B，B依赖A。或者多个对象的依赖关系形成闭环  
![](https://github.com/chrishyc/xsource/blob/master/images/spring_circle_1.png)
这种情况下容器会无限执行，一直创建Bean，最终导致OOM.

为了解决此类问题，spring引入了缓存机制。spring循环引用问题分为:
```
a.原型构造函数循环依赖
b.原型set方法循环依赖
c.单例构造函数循环依赖
d.单例set方法循环依赖
```
为了避免构造函数this指针逃逸问题，spring只支持解决单例set方法循环依赖

#### 方案

假设A,B之间存在循环依赖问题。spring解决思路主要为:
```
使用三级缓存：
1.singletonFactories：方法工厂对象，此工厂对象主要用于获取对象/对象的增强代理
2.earlySingletonObjects：存放未填充注入的对象
3.singletonObjects：存放最终态的对象


A的单独创建过程为：
a.创建A的实例对象,以工厂对象形式存入singletonFactories
b1.对A对象注入B对象

在上述过程b中，由于注入B对象会导致创建B实例对象，会导致:
c1.创建B实例对象，以工厂对象形式存入singletonFactories
c2.对B实例对象注入A对象, 如果A需要代理增强，进行代理增强，此时的A对象为不完全对象A，A
    存入earlySingletonObjects
c3.如果B对象需要代理增强，进行代理增强，此时的B对象为完全对象B，B存入singletonObjects

b2.对A对象注入B对象完成，A存入singletonObjects

d.返回实例A
```

#### 实现(时序图)
![](https://github.com/chrishyc/xsource/blob/master/images/spring_circle_2.png)

#### 为什么用三级缓存而不是二级缓存?
主要考虑到有的对象需要AOP增加，形成代理类，所以使用ObjectFactory缓存来处理生成代理类的逻辑
```
protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
    Object exposedObject = bean;
    if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            // SmartInstantiationAwareBeanPostProcessor 这个后置处理器会在返回早期对象时被调用，如果返回的对象需要加强，那这里就会生成代理对象
            if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
                SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
                exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
            }
        }
    }
    return exposedObject;
}

```

