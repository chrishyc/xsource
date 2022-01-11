#说说对jvm的理解
##总
jvm是虚拟机,字节码指令集(汇编语言),内存管理:栈 堆 方法区等,屏蔽了底层操作系统的差异性,实现了一套jvm指令的跨平台执行
jvm管理了class文件的加载classloader,编译javac,jit,运行时,jvm内存管理,垃圾回收GC,本地方法调用JNI,监控JMX
常用的java,大数据语言scala,安卓kotlin都编译成class文件,在jvm上执行,
常见jvm实现，Hotspot，淘宝TaobaoVM
##分,具体来说:
###编译
```asp
前端编译javac:
javac将源码编译成.class文件,常见的语法糖在这里完成,泛型,拆箱,lambda方法
常见的插件注解处理器在这里完成,lombok生成set,get
```
```asp
后端编译:
解释器:
jvm指令边解释边执行,为了跨平台为直接编译为本地目标代码
JIT编译器:
热点代码翻译成本地代码执行,客户端编译器C1,服务端编译器C2
热点代码探测,代码优化:方法内联,逃逸分析,栈上分配,同步消除
```
###加载
[z_1_加载_类加载器_加载器类型_双亲委派.md]
```asp
bootstrap,jre/lib/rt.jar,java.lang,nio,concurrent
ext,jre/lib/ext.jar
app,classpath
自定义,tomcat,WebAppClassLoader,每个Context 对应一个 WebappClassloader, 主要用于加载 WEB-INF/lib 与 WEB-INF/classes 下面的资源
```
class加载,[z_1_加载_02_Class_加载_链接_初始化_初始化顺序.md]
```asp
加载,二进制字节流,磁盘,内存,网络,proxy动态代理生成class中加载
连接:
- 验证:class版本,继承关系是否有final/private,
- 准备:分配内存
- 解析:符号引用转直接引用,内存地址
初始化:clinit,准备了类的变量初始值后，虚拟机会把该类的虚方法表也一同初始化
```
###运行时
```asp
私有区:
虚拟机栈,每一个线程对应一个虚拟机栈,每个方法对应一个栈帧,每个栈帧上包含操作数栈,局部方法表,动态连接(多态,运行时符号引用转直接引用),方法返回地址,指令集基于栈
本地方法栈,jvm通过jni支持对其他语言的通信,调用c,c++时hotspot会开辟本地方法栈,用于native方法出栈入栈,仍是同一个线程,指令集基于寄存器
pc计数器,指向当前线程执行的字节码行号
```
[](z_3_运行时_02_操作数栈_局部变量表_指令概述_方法重载_多态.md)
[深入理解java虚拟机8.2.3]
```asp
共享:
heap堆区:
方法区:
```
```asp

```
###内存管理

#对象的创建过程
```asp
1. class loading,classloader双亲委派加载
2. class linking (verification,方法签名,属性签名, preparation,分配内存, resolution,符号引用到直接引用)
3. class initializing,clinit初始化静态变量,静态代码块
4. 申请对象内存,对象头markword赋值,
5. 成员变量赋默认值,
6. 调用构造方法<init>
1. 成员变量顺序赋初始值 2. 执行构造方法语句
```
#项目中遇到的jvm问题

##对象过大导致oom
事件链路对象过大,全部晋升老年代
前端无响应,业务人员一直点,dubbo
##日志脱敏tostring导致常量池解析异常
tostring时常量池解析异常
指令行数,jclasslib找对应指令,发现是tostring中invokevirtual指令isSet报错
isSet不会进行脱敏,也不能脱敏,否则调用时是脱敏的数据,破坏业务

#NoClassDefFoundError vs ClassNotFoundException
NoClassDefFoundError:[类加载.解析]阶段,在运行时我们想调用某个类的方法或者访问这个类的静态成员的时候，发现这个类不可用，
此时Java虚拟机就会抛出NoClassDefFoundError错误
class类加载的解析阶段，会将符号引用 转 直接引用(多态过程)，找不到这个代理类的tostring方法

ClassNotFoundException:编译错误,路径中找不到类
