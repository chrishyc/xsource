##1.发布事件、监听事件设计模式?

##2.在哪里订阅,在哪里发布,监听哪些?传入数据?传入数据?传入数据设计?
a.订阅入口为ActivitiEventDispatcherImpl.addEventListener
b.发布ActivitiEventDispatcherImpl.dispatchEvent
c.传入为ActivitiEvent

##3.如何实现发布订阅?用到哪些类,分别承担什么角色?
ActivitiEventSupport
ActivitiEventDispatcherImpl
ActivitiEventListener
ActivitiEventType
CopyOnWriteArrayList
##4.用到哪些数据结构?

##5.内部监听器
