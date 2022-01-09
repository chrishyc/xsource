#说说对jvm的理解
##总
jvm是虚拟机,屏蔽了底层操作系统的差异性,实现了一套jvm指令的跨平台执行
常用的java,大数据语言scala,安卓kotlin都编译成class文件,在jvm上执行
jvm管理了class文件的加载,编译,执行,jvm内存管理,垃圾回收,本地方法调用,监控
##分,具体来说:

#项目中遇到的jvm问题
##
##对象过大导致oom
事件链路对象过大,全部晋升老年代
前端无响应,业务人员一直点,dubbo
##日志脱敏tostring导致常量池解析异常
tostring时常量池解析异常
指令行数,jclasslib找对应指令,发现是tostring中invokevirtual指令isSet报错
isSet不会进行脱敏,也不能脱敏,否则调用时是脱敏的数据,破坏业务
