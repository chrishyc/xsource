#临界知识
运行期类加载,加载二进制文件
#类加载流程
![](.z_1_加载_02_Class_加载_链接_初始化_images/4660f61f.png)
1.加载类:加载二进制文件存储为classFile  
2.验证Verification:验证classFile中属性签名,方法签名是否符合语法,验证文件是否符合JVM规定,
3.准备:给变量和方法准备内存空间,静态变量设定为默认值  
4.解析:将类、方法、属性等符号引用解析为直接引用 ,常量池中的各种符号引用解析为指针、偏移量等内存地址的直接引用
5.初始化:调用类初始化代码 <clinit>，给静态成员变量赋初始值
#class二进制文件来源
可以来自磁盘文件、 网络、数据库、内存或者动态产生,用户可以通过Java预置的或自定义类加载器，让某个本地的应用程序在运行时从网络 或其他地方上加载一个二进制流作为其程序代码的一部分
#class初始化时机

```asp
- new,getstatic putstatic invokestatic指令,(被final修饰、已在编译期把结果放入常量池的静态字段除外)
– java.lang.reflect对类进行反射调用时
– 初始化子类的时候，父类首先初始化
– 虚拟机启动时，被执行的主类必须初始化
```
##被动引用
对于静态字段,只有直接定义这个字段的类才会被初始化，
因此通过其子类来引用父类中定义的静态字段，只会触发 父类的初始化而不会触发子类的初始化
[T01_load_static]
```asp
会触发T01_load_static$SubClass的加载,不会触发他的初始化
[Loaded jvm.T01_load_static$SuperClass from file:/Users/chris/workspace/xsource/jvm/target/classes/]
[Loaded jvm.T01_load_static$SubClass from file:/Users/chris/workspace/xsource/jvm/target/classes/]
```
[T01_load_static_arr],会触发jvm.T01_load_static$SuperClass加载,不会触发初始化
#class初始化执行顺序
静态>实例化
静态变量和静态代码块在clinit中执行,按申明顺序执行
变量和代码块在init中执行,按申明顺序执行
构造函数最后执行
