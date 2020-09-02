获取eureka server中注册的service服务
##1.需求
eureka client获取eureka server中注册的service服务
##2.方案
Eureka Client启动时,实例化DiscoveryClient时,发送REST请求给Eureka Server,获取eureka server的服务清单,同步给自己
##3.实现
@EnableAutoConfiguration会将EurekaClientAutoConfiguration import生成beanDefinition,EurekaClientAutoConfiguration会
生成DiscoveryClient实例(EurekaClientConfiguration#eurekaClient),DiscoveryClient构造函数会请求配置文件中的eureka server url,
拉取apps信息给自己:DiscoveryClient#fetchRegistry(boolean forceFullRegistryFetch)

###类型:
启动类:DiscoveryClient
实体类:Applications,Application,AwsEndpoint,InstanceInfo,EurekaHttpResponse
策略类:EndpointRandomizer

配置类:EurekaClientConfig,DNSBasedAzToRegionMapper,PropertyBasedAzToRegionMapper
监测类:ThresholdLevelsMetric,Monitors

工具类:InstanceRegionChecker,EurekaHttpResolver,ConfigClusterResolver,

ApplicationsResolver,ClusterResolver,AsyncResolver,ServerStatusEvaluators
任务类:ScheduledExecutorService,ThreadPoolExecutor,TimedSupervisorTask

传输类:EurekaTransport,SessionedEurekaHttpClient,RetryableEurekaHttpClient,RedirectingEurekaHttpClient,EurekaHttpClient

工厂类:EurekaHttpClientFactory

###数据结构
SynchronousQueue
ConcurrentHashMap
ConcurrentLinkedQueue

###设计模式
Provider提供者
```
public interface Provider<T> {
}
```

fallback机制:
```
NotImplementedRegistryImpl
```

基类模式:CloudEurekaClient->DiscoveryClient

工厂模式:
```

```

###语法:
注解:
ConditionalOnProperty注解,生成beanDefinition时使用
@ConditionalOnProperty(prefix = "eureka.dashboard", name = "enabled",matchIfMissing = true)
检索策略:@ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
@EnableConfigurationProperties,生成实例时注入属性变量,BeanPostProcessor
@AutoConfigureBefore


静态代码块:static {}

volatile
synchronized
AtomicLong
AtomicReference,set
compareAndSet


###命名
EndpointRandomizer
backupRegistryInstance
healthCheck
registryStalenessMonitor
HostnameVerifier
Jersey1TransportClientFactories
providedJerseyClient
EurekaHttpResolver
filterAndShuffle
getAndStoreFullRegistry
getAndUpdateDelta
getAndStoreFullRegistry