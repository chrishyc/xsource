##invoke设计模式分析
ProtocolFilterWrapper.buildInvokerChain
invoker:待处理者
filter:处理者
Invocation:方法参数
链路头echoFilter
链路尾部:AbstractProxyInvokerAbstractProxyInvoker
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b1557e7a87e1430cb5b0ac58cb82a61c~tplv-k3u1fbpfcp-zoom-1.image)

[参考](https://juejin.cn/post/6858938670112571399)


future原理分析


dubbo原理分析





同步异步

字节码exception
异常栈含义分析
##dubbo io对象模型
##dubbo异步调用
ProtocolFilterWrapper.buildInvokerChain
```
 asyncResult.whenCompleteWithContext((r, t) -> {
                            if (filter instanceof ListenableFilter) {
                                ListenableFilter listenableFilter = ((ListenableFilter) filter);
                                Filter.Listener listener = listenableFilter.listener(invocation);
                                try {
                                    if (listener != null) {
                                        if (t == null) {
                                            listener.onResponse(r, invoker, invocation);
                                        } else {
                                            listener.onError(t, invoker, invocation);
                                        }
                                    }
                                } finally {
                                    listenableFilter.removeListener(invocation);
                                }
```
