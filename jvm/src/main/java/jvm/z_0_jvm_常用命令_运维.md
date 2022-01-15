#运维命令
##启动参数
配置虚拟机参数的常用命令
-XX:+PrintFlagsInitial
-XX:+PrintFlagsFinal
-XX:+PrintGCDetails
-XX:MaxMetaspaceSize=size
-XX:MaxDirectMemorySize=size
-XX:+UseConcMarkSweepGC

java  -Xmx20m     -Xms20m        -Xmn10m
-XX:SurvivorRatio=ratio

java -XX:+PrintCommandLineFlags -version//查看默认垃圾回收器
java -XX:+PrintFlagsFinal -version | grep -A 20 MaxMetaspaceSize//查看元数据区大小
man java | grep -A 30 Xss//查看栈大小

##进程
jps：Lists the instrumented Java Virtual Machines (JVMs) on the target system
jps -l java进程和包名
jps -v 启动参数详情
##线程堆栈
jstack - Prints Java thread stack traces for a Java process
jstack -l 10244生成pid=10244的线程堆栈日志
##gc
jstat - Monitors Java Virtual Machine (JVM) statistics
jstat -gc 21891 250 7每隔250ms查看pid=21891的gc情况，共7次

jinfo- Generates configuration information
jinfo -flag MaxTenuringThreshold 10244查看进程10244信息
##内存dump
jmap - Prints shared object memory maps or heap memory details for a process
生成实时dump文件

System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");//java动态代理，保存字节码文件到磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/chris/Downloads/test/spring/com/tutorialspoint/$Proxy123.class");//查看CGLib字节码

#运维工具
VisualVM
MAT
arthas
JMAP
JSTACK
JPS
##MAT实战
[](https://help.eclipse.org/latest/index.jsp?topic=/org.eclipse.mat.ui.help/welcome.html)
[](https://juejin.cn/post/6911624328472133646#heading-14)
###shallow heap
这个对象实际占用的堆大小
###retained heap
如果这个对象被删除了（GC回收掉），能节省出多少内存
[](https://blog.csdn.net/wwlwwy89/article/details/74330544)
#编译
检测热点代码：-XX:CompileThreshold = 10000
##注解处理器
javac -processor
[T02_compile_04_annotations.java]
#加载
-XX: +TraceClassLoading
