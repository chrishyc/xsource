RestTemplate拦截器逻辑
##1.需求
给RestTemplate设置拦截器,拦截器以链式方式执行
##2.方案
把上次处理的HttpRequest请求和链式执行器传递给本次拦截器,用于本次的处理和链式执行
##3.实现
###1.预测
拦截器接口,拦截器集合,链式执行器
###RestTemplate设计
1.action:RestOperations
2.field:HttpAccessor,InterceptingHttpAccessor
3.callback:RequestCallback,HttpMessageConverterExtractor
拦截器以继承方式实现
![](/Users/chris/xsource/uml/restTemplate/RestTemplate.png)
###RequestCallback设计
###HttpMessageConverter设计
List<HttpMessageConverter<?>> messageConverters


###ClientHttpRequestFactory设计
![](/Users/chris/xsource/uml/restTemplate/InterceptingClientHttpRequestFactory.png)
###ClientHttpRequest设计
![](/Users/chris/xsource/uml/restTemplate/InterceptingClientHttpRequest.md.png)
拦截器执行器:InterceptingRequestExecution
InterceptingClientHttpRequest内部关联所有ClientHttpRequestInterceptor并使用InterceptingRequestExecution
执行这些ClientHttpRequestInterceptor(使用迭代器的执行方式),执行完成后获取最终的httpRequest并使用
SimpleClientHttpRequestFactory生成SimpleBufferingClientHttpRequest请求作为最终的http请求实体类

###ClientHttpResponse设计
###ClientHttpRequestInterceptor设计



###语法
Type:
stream:flatMap,distinct,sorted,collect
###命名
ConverterExtractor
Supported
Generic
AbstractHttpMessageConverter
doExecute
Wrapper
Intercepting