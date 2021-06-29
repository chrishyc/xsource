#hive
Hive：借助Hive，用户可以编写SQL语句来查询HDFS上的结构化数据，SQL会被转化成MapReduce执行

[hive 使用](https://zhuanlan.zhihu.com/p/80166949)
[parquet格式](https://juejin.cn/post/6844903462572916743#heading-3)

#Hive的数据组织：
1.Hive 的存储结构包括数据库、表、视图、分区和表数据等。数据库，表，分区等等都对 应 HDFS 上的一个目录。表数据对应 HDFS 对应目录下的文件。

2.Hive 中所有的数据都存储在 HDFS 中，没有专门的数据存储格式，因为 Hive 是读模式 （Schema On Read），可支持 TextFile，SequenceFile，RCFile 或者自定义格式等

3.Hive被称为"数据仓库"，但是Hive不存储数据，数据存储在HDFS中，hive主要用于管理和解析数据，也不是作为数据库使用。
[](https://zhuanlan.zhihu.com/p/63292203)
[](https://blog.csdn.net/haramshen/article/details/52606666)
[](https://www.zhihu.com/question/49969423/answer/118701049)
[](https://cshihong.github.io/2018/05/22/Hive%E6%8A%80%E6%9C%AF%E5%8E%9F%E7%90%86/)
