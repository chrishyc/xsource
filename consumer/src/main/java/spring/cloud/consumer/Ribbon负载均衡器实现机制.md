Ribbon负载均衡器实现机制
##1.需求
实现客户端负载均衡器
##2.方案
通过对RestTemplate设置LoadBalancerInterceptor
对request请求实现负载均衡,然后将处理完的httprequest再传递给ClientHttpRequestInterceptor迭代执行器
##3.实现
###RibbonLoadBalancerClient设计
Ribbon负载均衡中央处理器:RibbonLoadBalancerClient
![](/Users/chris/xsource/uml/ribbon/RibbonLoadBalancerClient.png)
```
public <T> T execute(String serviceId, LoadBalancerRequest<T> request, Object hint)
			throws IOException {
		ILoadBalancer loadBalancer = getLoadBalancer(serviceId);
		Server server = getServer(loadBalancer, hint);
		...
		return execute(serviceId, ribbonServer, request);
	}

	@Override
	public <T> T execute(String serviceId, ServiceInstance serviceInstance,
			LoadBalancerRequest<T> request) throws IOException {
			...
			T returnVal = request.apply(serviceInstance);
			...
			return returnVal;
	}
```
###ILoadBalancer设计
![](/Users/chris/xsource/uml/ribbon/RibbonLoadBalancerClient.png)
```
@Bean
	@ConditionalOnMissingBean
	public ILoadBalancer ribbonLoadBalancer(IClientConfig config,
			ServerList<Server> serverList, ServerListFilter<Server> serverListFilter,
			IRule rule, IPing ping, ServerListUpdater serverListUpdater) {
		if (this.propertiesFactory.isSet(ILoadBalancer.class, name)) {
			return this.propertiesFactory.get(ILoadBalancer.class, config, name);
		}
		return new ZoneAwareLoadBalancer<>(config, rule, ping, serverList,
				serverListFilter, serverListUpdater);
	}
```

###IRule设计
fallback:ZoneAvoidanceRule
```
@Bean
	@ConditionalOnMissingBean
	public IRule ribbonRule(IClientConfig config) {
		if (this.propertiesFactory.isSet(IRule.class, name)) {
			return this.propertiesFactory.get(IRule.class, config, name);
		}
		ZoneAvoidanceRule rule = new ZoneAvoidanceRule();
		rule.initWithNiwsConfig(config);
		return rule;
	}
```
###IPing设计
###DiscoveryEnabledNIWSServerList设计
###RibbonLoadBalancerContext设计
###RibbonStatsRecorder设计
###SpringClientFactory设计
获取容器bean实例
###LoadBalancerInterceptor拦截器设计
###LoadBalancerRequestFactory设计
生成ServiceRequestWrapper请求传递给拦截器执行器
###LoadBalancerRequestTransformer设计
