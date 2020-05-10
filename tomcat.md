### 1.tomcat架构
![service](https://www.ibm.com/developerworks/cn/java/j-lo-tomcat1/image001.gif)
1.一个tomcat对应一个server实例，server由多个service组成   
2.每个service包含两个核心组件connector,container.其中connector只有一个，container结构如下:
![container](https://www.ibm.com/developerworks/cn/java/j-lo-servlet/image002.jpg)
每个container包含一个engine,而engine中可包括多个虚拟主机host,每个host可以包含多个
context,context就是我们通常意义上的webapp项目,wrapper对应一个servlet就是我们servlet容器
### 2.tomcat启动流程
![startup](/Users/chris/Downloads/tomcat_startup.png )
当startup.sh启动bootstrap时，会初始化server,server会初始化service，并逐级初始化container组件
host->context,整个初始化过程使用了组合模式，组件都实现了Lifecycle接口,以及ContainerBase接口  

另一部分是connector初始化,根据conf/server.xml中配置的协议选择对应的protocolHandler初始化

### 3.tomcat请求流程
![request](/Users/chris/Downloads/request.png)
请求过来时,先由connector生成对应的request,并发送给engine,然后通过mapper完成url,host,context,
wrapper的映射
