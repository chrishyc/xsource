# 一、简答题
## 1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？
1)根据不同的条件拼接SQL语句  
2)主要有
- if
- choose (when, otherwise)
- trim (where, set)
- foreach   

3)动态sql主要分为  
1.解析节点  
2.根据入参来拼接节点sql文本

1.解析节点

```
[XMLMapperBuilder]
生成mapper中所有sql语句节点
buildStatementFromContext(context.evalNodes("select|insert|update|delete"));

[XMLStatementBuilder]
[XMLScriptBuilder]
节点使用了组合模式，遍历所有节点，如果节点为ELEMENT_NODE类型则递归遍历，直至节点为TEXT_NODE则添加到节点集合。
最后保存在DynamicSqlSource中，DynamicSqlSource保存在MappedStatement中
```

2.拼接动态sql文本
```
[Executor]执行查询语句时，先执行
BoundSql boundSql = ms.getBoundSql(parameterObject);
每个节点会调用自己的SqlNode执行拼接逻辑
public boolean apply(DynamicContext context) {
    contents.forEach(node -> node.apply(context));
    return true;
  }
比如IfSqlNode会判断test的内容和入参
@Override
  public boolean apply(DynamicContext context) {
    if (evaluator.evaluateBoolean(test, context.getBindings())) {
      contents.apply(context);
      return true;
    }
    return false;
  }
 以此判断是否需要加入IfSqlNode的text文本内容
 遍历完成后对sql文本进行加工处理
 public void applyAll() {
      sqlBuffer = new StringBuilder(sqlBuffer.toString().trim());
      String trimmedUppercaseSql = sqlBuffer.toString().toUpperCase(Locale.ENGLISH);
      if (trimmedUppercaseSql.length() > 0) {
        applyPrefix(sqlBuffer, trimmedUppercaseSql);
        applySuffix(sqlBuffer, trimmedUppercaseSql);
      }
      delegate.appendSql(sqlBuffer.toString());
    }
最后处理后的sql提供给执行器执行
```
## 2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
###  1.支持延迟加载  
延时加载配置：
```
<setting name="lazyLoadingEnabled" value="true"/>
mapper中association、collection支持延时加载
对于1对1，1对多，或者多对多的关联语句，可以将外层查询和关联查询分离，按需查询关联的实体类。

```
测试延迟加载时遇到延迟加载失效的问题。debug或者println时发现延迟加载失效。原因是触发关联对象的tostring等方法，会导致加载，禁用触发方法即可
```
<setting name="lazyLoadTriggerMethods" value=""/>
```
### 2.实现原理
配置lazyLoadingEnabled=true后，在执行查询语句时，会对查询的返回结果对象A生成对应的代理类，对A中关联的对象B，会记录在ResultLoaderMap中，对应一个loader。

当调用对象A的get方法获取B时,A的代理类中会判断是否存在此属性的loader,存在，则调用此loader，执行sql查询过程

可以分为三部分理解  
1.配置ResultMapping
```
获取lazy配置
configuration.setLazyLoadingEnabled(booleanValueOf(props.getProperty("lazyLoadingEnabled"), false));

记录在resultmapping中
resultMapping.lazy = configuration.isLazyLoadingEnabled();

```

2.JavassistProxyFactory生成代理对象
A调用方法时，会查询数据库执行queryFromDatabase，此过程会生成结果集,发现是lazyLoadingEnabled且是复合结果集，则会生成对应的结果集代理
```
JavassistProxyFactory

public Object createProxy(Object target, ResultLoaderMap lazyLoader, Configuration configuration, ObjectFactory objectFactory, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    return EnhancedResultObjectProxyImpl.createProxy(target, lazyLoader, configuration, objectFactory, constructorArgTypes, constructorArgs);
  }
```
3.执行方法获取关联对象  
代理对象get方法获取关联对象时,会从ResultLoaderMap中获取lazyLoader，lazyLoader用户加载关联的对象。lazyLoader.loan方法就是重新执行sql语句获取对应结果的过程
```
if (lazyLoader.size() > 0 && !FINALIZE_METHOD.equals(methodName)) {
              if (aggressive || lazyLoadTriggerMethods.contains(methodName)) {
                lazyLoader.loadAll();
              } else if (PropertyNamer.isSetter(methodName)) {
                final String property = PropertyNamer.methodToProperty(methodName);
                lazyLoader.remove(property);
              } else if (PropertyNamer.isGetter(methodName)) {
                final String property = PropertyNamer.methodToProperty(methodName);
                if (lazyLoader.hasLoader(property)) {
                  lazyLoader.load(property);
                }
              }
            }
```


## 3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？

### 1.Mybatis都有哪些Executor执行器
executor是mybatis执行sql语句的执行器，采用了模板方法设计模式  
1)核心为BaseExecutor.  
2)默认的执行器为SimpleExecutor   
3)配置了cacheEnabled=true,即二级缓存则会启用CachingExecutor，这里用了装饰者模式，CachingExecutor装饰了SimpleExecutor。每次执行sql语句，会先从二级缓存中获取，然后再从SimpleExecutor中获取  
4)ReuseExecutor,每次在执行一条SQL语句时，它都会去判断之前是否存在基于该SQL缓存的Statement对象，存在而且之前缓存的Statement对象对应的Connection还没有关闭的时候就继续用之前的Statement对象，否则将创建一个新的Statement对象，并将其缓存起来。  
5)BatchExecutor批处理执行器
```
public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    executorType = executorType == null ? defaultExecutorType : executorType;
    executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    Executor executor;
    if (ExecutorType.BATCH == executorType) {
      executor = new BatchExecutor(this, transaction);
    } else if (ExecutorType.REUSE == executorType) {
      executor = new ReuseExecutor(this, transaction);
    } else {
      executor = new SimpleExecutor(this, transaction);
    }
    if (cacheEnabled) {
      executor = new CachingExecutor(executor);
    }
    executor = (Executor) interceptorChain.pluginAll(executor);
    return executor;
  }
```

## 4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？

1.存储结构
一级缓存:PerpetualCache
二级缓存:默认PerpetualCache，可自定义，实现Cache接口，并配置.可使用RedisCache等自定义缓存

2.范围  
一级缓存:两个范围statement级别，session级别
二级缓存:Mapper级别，即namespace级别 

3.失效场景  
1)一级缓存是session级别，session只能读取内部结果，其他session执行更新操作，本session无感知，会造成脏读   
2)二级缓存是本地缓存，对于分布式场景，需要结合分布式缓存进行使用，不然容易造成脏读。多表查询时，极大可能会出现脏数据  

## 5、简述Mybatis的插件运行原理，以及如何编写一个插件？
### 1)编写插件
```
配置拦截器
<plugins>
        <!--        <plugin interceptor="start.intercept.MyPlugin"/>-->
        <plugin interceptor="com.github.pagehelper.PageHelper">
            <property name="dialect" value="mysql"/>
        </plugin>
        <plugin
                interceptor="tk.mybatis.mapper.mapperhelper.MapperInterceptor">
            <property name="mappers"
                      value="tk.mybatis.mapper.common.Mapper"/>
        </plugin>
    </plugins>
    
实现拦截器和拦截目标方法，以及intercept
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class Interceptor implements org.apache.ibatis.plugin.Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

}

```

### 2)实现原理  
配置插件类，并实现插件类.mybatis在加载配置类创建Executor，创建ParameterHandler，创建ResultSetHandler，创建StatementHandler时判断interceptors容器是否有针对这四种类的拦截器，有则通过Proxy代理方式实现针对这四种的代理类，可以在intercept中实现插件逻辑.最后执行sql语句时通过代理类执行

```
private void pluginElement(XNode parent) throws Exception {
    if (parent != null) {
      for (XNode child : parent.getChildren()) {
        String interceptor = child.getStringAttribute("interceptor");
        Properties properties = child.getChildrenAsProperties();
        Interceptor interceptorInstance = (Interceptor) resolveClass(interceptor).newInstance();
        interceptorInstance.setProperties(properties);
        configuration.addInterceptor(interceptorInstance);
      }
    }
  }
  
  生成代理类
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }
  
  代理类invoke
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      Set<Method> methods = signatureMap.get(method.getDeclaringClass());
      if (methods != null && methods.contains(method)) {
        return interceptor.intercept(new Invocation(target, method, args));
      }
      return method.invoke(target, args);
    } catch (Exception e) {
      throw ExceptionUtil.unwrapThrowable(e);
    }
  }
  
  代理类插件逻辑主要在intercept方法中
  public interface Interceptor {

  Object intercept(Invocation invocation) throws Throwable;

  default Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  default void setProperties(Properties properties) {
    // NOP
  }

}
```

