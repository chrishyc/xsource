#head插件
#启动集群
./elasticsearch -d
/elasticsearch -E path.data=data1 -E path.logs=log1 -E node.name=node1 -E cluster.name=msb_teach
./elasticsearch -E path.data=data2 -E path.logs=log2 -E node.name=node2 -E cluster.name=msb_teach

./kibana

#集群启动常见问题
[](https://www.cnblogs.com/cnsdhzzl/p/9401829.html)
[](https://www.cnblogs.com/hellxz/p/11057234.html)

#luke查看物理索引文件
[下载地址](https://github.com/DmitryKey/luke)
/Users/chris/workspace/elk/lucene/luke脚本查看分析index索引文件
![](.z_es_00_常用命令_images/449b5840.png)
segments
![](.z_es_00_常用命令_images/cd1ae25e.png)
#jvm优化
[](https://www.elastic.co/guide/cn/elasticsearch/guide/current/heap-sizing.html#_swapping_%E6%98%AF%E6%80%A7%E8%83%BD%E7%9A%84%E5%9D%9F%E5%A2%93)
#禁用Swapping
