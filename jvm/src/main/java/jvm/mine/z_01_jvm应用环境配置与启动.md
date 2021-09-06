##java程序启动入口
java [options] classname [args]

java [options] -jar filename [args]

-Xjre:非标准启动命令

jvm根据用户指定的class中的main来启动应用
或者指定-jar由jvm虚拟机寻找


##Java虚拟机类路径
启动类路径默认对应jre\lib目录  
扩展类路径默认对应jre\lib\ext目录，使用Java扩展机 制的类位于这个路  
我们自己实现的类，以及第三方类库则位于 用户类路径  

优先使用用户输入的-Xjre选项作为jre目录。  
如果没有输入该 选项，则在当前目录下寻找jre目录。  
如果找不到，尝试使用 JAVA_HOME环境变量(环境变量的设计在shell,maven,grafana都有)
##类文件形式
DirEntry:表示目录形式的类路径
ZipEntry:表示ZIP或JAR文件形式的类路径
CompositeEntry:组合Entry
WildcardEntry:正则组合Entry
