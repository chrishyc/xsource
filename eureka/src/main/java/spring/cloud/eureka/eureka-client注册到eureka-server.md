##需求
eureka client将自身注册到eureka server中
##方案
DiscoveryClient实例化过程中,获取eureka server中注册的service服务之后(fetchRegistry(false))
开始向服务端进行注册自己register()
##实现
```
eurekaTransport.registrationClient.register(instanceInfo)
```