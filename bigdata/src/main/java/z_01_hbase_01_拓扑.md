# hbase是什么?(Hadoop Database,sorted map)
sparse, distributed, persistent multidimensional sorted map，HBase本质来看是一个Map
##多维
这个特性比较容易理解。HBase中的Map与普通Map最大的不同在于，key是一个复合数据结构，由多维元素构成，包括rowkey、column family、qualifier、type以及timestamp。
##稀疏(面向列簇存储)
在列族中，你可以指定任意多的列，在列数据为空的情 况下，是不会占用存储空间的
##排序
构成HBase的KV在同一个文件中都是有序的，但规则并不是仅仅按照rowkey排序，而是按照KV中的key进行排序——先比较rowkey，rowkey小的排在前面；
如果rowkey相同，再比较column，即column family:qualifier，column小的排在前面；如果column还相同，再比较时间戳timestamp，即版本信息，timestamp大的排在前面
##多版本
HBase表中的数据可以有多个版本值，默认情况下是根据版本号去区分，版本号就 是插入数据的时间戳
##分布式
构成HBase的所有Map并不集中在某台机器上，而是分布在整个集群中,HDFS作为其文件存储系统
HBase集群可以非常方便地实现集群容量扩展，主要包括数据存储节点扩展以及读写服务节点扩展。HBase底层数据存储依赖于HDFS系统，HDFS可以通过简单地增加DataNode实现扩展，
HBase读写服务节点也一样，可以通过简单的增加RegionServer节点实现计算层的扩展。
##高性能
LSM,mmap
HBase目前主要擅长于OLTP场景，数据写操作性能强劲，对于随机单点读以及小范围的扫描读，其性能也能够得到保证。
对于大范围的扫描读可以使用MapReduce提供的API，以便实现更高效的并行扫描
##支持过期
HBase支持TTL过期特性，用户只需要设置过期时间，超过TTL的数据就会被自动清理，不需要用户写程序手动删除。
##容量巨大
HBase的单表可以支持千亿行、百万列的数据规模，数据容量可以达到TB甚至PB级别。传统的关系型数据库，如Oracle和MySQL等，如果单表记录条数超过亿行，
读写性能都会急剧下降，在HBase中并不会出现这样的问题。
#逻辑拓扑
##table
表，一个表包含多行数据
##row
行，一行数据包含一个唯一标识rowkey、多个column以及对应的值。在HBase中，一张表中所有row都按照rowkey的字典序由小到大排序
##column family(列式存储,物理存储角度的概念)
column family在表创建的时候需要指定，用户不能随意增减，列簇对应region store,列簇具有列式存储
##column
一行具有多列,column family可以有0列,1列或者多列,列可能分布在多个列簇中,即多个store中
##cell
(rowkey:column family:column:timestamp:type,value)确定一个cell,最小单元,每个cell有timestamp属性,代表(rowkey,column)的版本
```asp
type表示Put/Delete这样的操作类型，timestamp代表这个cell的版本。这个结构在数据库中实际是以KV结构存储的，其中（row, column,timestamp, type）
是K，value字段对应KV结构的V
```
##timestamp(多版本)
(rowkey,column,timestamp)确定某个rowkey的某列column的某个版本
#集群&物理拓扑
![](.z_01_hbase_01_拓扑_images/9b6f9e57.png)
##Master
1.处理用户的各种管理请求，包括建表、修改表、权限操作、切分表、合并数据分片以及Compaction等
2.管理集群中所有RegionServer，包括RegionServer中Region的负载均衡、RegionServer的宕机恢复以及Region的迁移等
3.清理过期日志以及文件，Master会每隔一段时间检查HDFS中HLog是否过期、HFile是否已经被删除，并在过期之后将其删除
##ZooKeeper
1.master选举
2.RegionServer集合
3.元数据表hbase:meta所在的RegionServer地址
4.实现分布式表锁
##RegionServer
RegionServer主要用来响应用户的IO请求
###WAL(高可靠,主从同步)
HLog在HBase中有两个核心作用——其一，用于实现数据的高可靠性，HBase数据随机写入时，并非直接写入HFile数据文件，而是先写入缓存，再异步刷新落盘。
为了防止缓存数据丢失，数据写入缓存之前需要首先顺序写入HLog，这样，即使缓存数据丢失，仍然可以通过HLog日志恢复；

其二，用于实现HBase集群间主从复制，通过回放主集群推送过来的HLog日志实现主从复制
###BlockCache(64K)
HBase系统中的读缓存。客户端从磁盘读取数据之后通常会将数据缓存到系统内存中，后续访问同一行数据可以直接从内存中获取而不需要访问磁盘
###Region
数据表的一个分片，当数据表大小超过一定阈值就会“水平切分”，分裂为两个Region。Region是集群负载均衡的基本单位。通常一张表的Region会分布在整个集群的多台RegionServer上，
一个RegionServer上会管理多个Region，当然，这些Region一般来自不同的数据表
###Store(store:列簇=1:1)
Store的个数取决于表中列簇（column family）的个数，多少个列簇就有多少个Store。HBase中，每个列簇的数据都集中存放在一起形成一个存储单元Store，
因此建议将具有相同IO特性的数据设置在同一个列簇中。
###MemStore
每个Store由一个MemStore和一个或多个HFile组成。MemStore称为写缓存，用户写入数据时首先会写到MemStore，当MemStore写满之后（缓存数据超过阈值，默认128M）
系统会异步地将数据f lush成一个HFile文件
###HFile
显然，随着数据不断写入，HFile文件会越来越多，当HFile文件数超过一定阈值之后系统将会执行Compact操作，将这些小文件通过一定策略合并成一个或多个大文件。

##HDFS
HBase底层依赖HDFS组件存储实际数据，包括用户数据文件、HLog日志文件等最终都会写入HDFS落盘。
##client
请求zookeeper，定位元数据表,通过元数据表定位目标数据所在RegionServer->请求RegionServer
