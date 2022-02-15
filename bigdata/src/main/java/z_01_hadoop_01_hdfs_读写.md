#写
![](.z_01_hadoop_01_hdfs_读写_images/dc1d4b96.png)
![](.z_01_hadoop_01_hdfs_读写_images/72eb0143.png)
![](.z_01_hadoop_01_hdfs_读写_images/49b33e0e.png)
```asp
1. 客户端通过Distributed FileSystem模块向NameNode请求上传文件，NameNode检查目标文件 是否已存在，父目录是否存在。
2. NameNode返回是否可以上传。
3. 客户端请求第一个 Block上传到哪几个DataNode服务器上。
4. NameNode返回3个DataNode节点，分别为dn1、dn2、dn3。
5. 客户端通过FSDataOutputStream模块请求dn1上传数据，dn1收到请求会继续调用dn2，然后
dn2调用dn3，将这个通信管道建立完成。
6. dn1、dn2、dn3逐级应答客户端。
7. 客户端开始往dn1上传第一个Block(先从磁盘读取数据放到一个本地内存缓存)，以Packet为单
位，dn1收到一个Packet就会传给dn2，dn2传给dn3;dn1每传一个packet会放入一个确认队列
等待确认。
8. 当一个Block传输完成之后，客户端再次请求NameNode上传第二个Block的服务器。(重复执行
3-7步)。
```
#读
![](.z_01_hadoop_01_hdfs_读写_images/3f09252f.png)
```asp
1. 客户端通过Distributed FileSystem向NameNode请求下载文件，NameNode通过查询元数据， 找到文件块所在的DataNode地址。
2. 挑选一台DataNode(就近原则，然后随机)服务器，请求读取数据。
3. DataNode开始传输数据给客户端(从磁盘里面读取数据输入流，以Packet为单位来做校验)。 4. 客户端以Packet为单位接收，先在本地缓存，然后写入目标文件。
```
