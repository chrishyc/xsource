# HDFS问题清单
##架构
Master/Slave 架构
NameNode是集群的主节点，DataNode是集群的从节点
##文件单位
HDFS 中的文件在物理上是分块存储(block)的，块的大小可以通过配置参数来规定; Hadoop2.x版本中默认的block大小是128M;
##命令空间
命名空间(NameSpace)
文件系统名字空间的层次结构和大多数现有的文件系统类似:用户可以创建、删除、移动 或重命名文件。
Namenode 负责维护文件系统的名字空间，任何对文件系统名字空间或属性的修改都将被 Namenode 记录下来。
HDFS提供给客户单一个抽象目录树，访问形式:hdfs://namenode的hostname:port/test/input
hdfs://linux121:9000/test/input

##NameNode元数据管理
NameNode元数据管理
我们把目录结构及文件分块位置信息叫做元数据。
NameNode的元数据记录每一个文件所对应的block信息(block的id,以及所在的DataNode节点 的信息)

##DataNode数据存储
文件的各个 block 的具体存储管理由 DataNode 节点承担。一个block会有多个DataNode来存
储，DataNode会定时向NameNode来汇报自己持有的block信息

##副本机制
为了容错，文件的所有 block 都会有副本。每个文件的 block 大小和副本系数都是可配置的。应用 程序可以指定某个文件的副本数目。副本系数可以在文件创建的时候指定，也可以在之后改变。 副本数量默认是3个


##一次写入，多次读出
HDFS 是设计成适应一次写入，多次读出的场景，且不支持文件的随机修改。 (支持追加写入， 不只支持随机更新)
正因为如此，HDFS 适合用来做大数据分析的底层存储服务，并不适合用来做网盘等应用(修改不 方便，延迟大，网络开销大，成本太高
