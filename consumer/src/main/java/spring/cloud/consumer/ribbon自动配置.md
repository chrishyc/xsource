ribbon自动配置
##1.需求
使得ribbon负载均衡器生效
##2.方案
基于@ConditionalOnClass(RestTemplate.class)来决定是否让Ribbon生效
##3.实现
spring.factories配置自动配置类:
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration
```
RibbonAutoConfiguration主要注解有:
@RibbonClients即@Import(RibbonClientConfigurationRegistrar.class)
@AutoConfigureAfter(EurekaClientAutoConfiguration)
@AutoConfigureBefore({LoadBalancerAutoConfiguration.class})中包含@ConditionalOnClass(RestTemplate.class)
当我们在业务中申明
```
@Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
```
后,LoadBalancerAutoConfiguration会注入容器中,并触发它配置的@bean方法.

几个重要类:
```
@Bean
@ConditionalOnMissingBean(LoadBalancerClient.class)
public LoadBalancerClient loadBalancerClient() {
	return new RibbonLoadBalancerClient(springClientFactory());
}
```
```
@Bean
@ConditionalOnMissingBean
public LoadBalancerRequestFactory loadBalancerRequestFactory(
		LoadBalancerClient loadBalancerClient) {
	return new LoadBalancerRequestFactory(loadBalancerClient, this.transformers);
}
```

```
@Bean
public LoadBalancerInterceptor ribbonInterceptor(
		LoadBalancerClient loadBalancerClient,
		LoadBalancerRequestFactory requestFactory) {
	return new LoadBalancerInterceptor(loadBalancerClient, requestFactory);
}

@Bean
@ConditionalOnMissingBean
public RestTemplateCustomizer restTemplateCustomizer(
		final LoadBalancerInterceptor loadBalancerInterceptor) {
	return restTemplate -> {
		List<ClientHttpRequestInterceptor> list = new ArrayList<>(
				restTemplate.getInterceptors());
		list.add(loadBalancerInterceptor);
		restTemplate.setInterceptors(list);
	};
}
```
##类型

##设计模式
生产者lambda模式:ObjectFactory::getObject
##语法
@AutoConfigureAfter @AutoConfigureBefore
@EnableConfigurationProperties
@ConditionalOnMissingBean
restTemplate -> restTemplate.setRequestFactory(ribbonClientHttpRequestFactory);
##命名
Initializer
RestTemplateCustomizer
LoadBalancerInterceptor