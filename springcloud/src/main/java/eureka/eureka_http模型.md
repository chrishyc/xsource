eureka http模型
##1.需求
eureka 节点通信框架
##2.方案
链式
##3.实现
![](/Users/chris/xsource/uml/eureka/EurekaHttpClient_hierarchy.png)
![](/Users/chris/xsource/uml/eureka/EurekaHttpClientDecorator_hierarchy.png)
EurekaHttpClientDecorator:装饰者模式

SessionedEurekaHttpClient:会话http客户端,经过一段时间会强制重连,防止客户端一直连着同一台服务器实例,10-30分钟随机会话周期

RetryableEurekaHttpClient:具有失败重试机制,重试三次.使用缓存记录请求失败的服务器

RedirectingEurekaHttpClient:请求码诶302时进行重定向,最多重定向10次

MetricsCollectingEurekaHttpClient:收集性能指标

EurekaHttpClient:接口

JerseyApplicationClient:

AbstractJerseyEurekaHttpClient:

JerseyReplicationClient:


##类型
工具类:TransportUtils,ClusterResolver,ServerStatusEvaluator
工厂类:EurekaHttpClientFactory,TransportClientFactory
配置类:EurekaTransportConfig
回调类:RequestExecutor

##数据结构
ConcurrentSkipListSet

##设计模式
###装饰器模式,模板方法模式:EurekaHttpClientDecorator
普通装饰器模式:构造函数传入被包装类,每个方法内调用此类以及附加逻辑
eureka http装饰器模式:
####优点:
1.基类提取出专门的一个抽象方法用于子类实现自定义逻辑,无需子类手动将增强逻辑植入每一个方法.
2.基类把子类要装饰的所有方法提前自行导入而无需延迟到子类定义时导入,子类只需关注自己的增强方法即可.
为了提前获取被包装类的引用,需要使用回调类RequestExecutor
```
@Override
    public EurekaHttpResponse<Applications> getApplications(final String... regions) {
        return execute(new RequestExecutor<Applications>() {
            @Override
            public EurekaHttpResponse<Applications> execute(EurekaHttpClient delegate) {
                return delegate.getApplications(regions);
            }

            @Override
            public RequestType getRequestType() {
                return RequestType.GetApplications;
            }
        });
    }
```
```
@Override
    protected <R> EurekaHttpResponse<R> execute(RequestExecutor<R> requestExecutor) {
        ...
        EurekaHttpClient eurekaHttpClient = eurekaHttpClientRef.get();
        if (eurekaHttpClient == null) {
            eurekaHttpClient = TransportUtils.getOrSetAnotherClient(eurekaHttpClientRef, clientFactory.newClient());
        }
        return requestExecutor.execute(eurekaHttpClient);
    }
```
SessionedEurekaHttpClient调用自身getApplications(url)时,
逻辑实际被自身的execute(RequestExecutor)方法代理了,execute(RequestExecutor)执行完增强逻辑后,
调用RequestExecutor.execute
而RequestExecutor.execute实际逻辑是调用被包装类RetryableEurekaHttpClient.getApplications(url)
代码逻辑为:
```
SessionedEurekaHttpClient.getApplications->
SessionedEurekaHttpClient.execute->
SessionedEurekaHttpClient.RequestExecutor.execute->
RetryableEurekaHttpClient.getApplications->
...
```
####缺点:
无法灵活指定包装类哪些方法增强,哪些方法不增强,会对所有方法增强.

###回调模式:
```
public interface RequestExecutor<R> {
        EurekaHttpResponse<R> execute(EurekaHttpClient delegate);

        RequestType getRequestType();
    }
```
###final模式:final class
###工厂模式:
```
public interface EurekaHttpClientFactory {

    EurekaHttpClient newClient();

    void shutdown();

}
```
httpclient子类导入被包装类工厂类,使用此工厂类生成被包装httpclient,
```
public SessionedEurekaHttpClient(String name, EurekaHttpClientFactory clientFactory, long sessionDurationMs) {
        ...
        this.clientFactory = clientFactory;
        ...
    }
```
```
@Override
    protected <R> EurekaHttpResponse<R> execute(RequestExecutor<R> requestExecutor) {
    	...
        EurekaHttpClient eurekaHttpClient = eurekaHttpClientRef.get();
        if (eurekaHttpClient == null) {
            eurekaHttpClient = TransportUtils.getOrSetAnotherClient(eurekaHttpClientRef, clientFactory.newClient());
        }
        return requestExecutor.execute(eurekaHttpClient);
    }
```

##语法
AtomicReference.getAndSet
AtomicReference.compareAndSet
final String... regions
private final
volatile
匿名类
##命名
randomizeSessionDuration
delta
Reconnect
numberOfRetries
ServerStatusEvaluator
ClusterResolver
delegate
quarantine:隔离
retain
threshold
canonicalClientFactory:规范化
RequestExecutor
currentSessionDurationMs
EurekaHttpClientDecorator
SessionedEurekaHttpClient
randomizeSessionDuration
delay
TransportUtils
Metrics
mappedStatus
shutdownMetrics
