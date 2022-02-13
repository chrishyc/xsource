#降低采样
```asp
- 缓存系统内存使用降低
  - 监控系统为了加快查询速度会在各个环节上设置缓存
  - 那么如果采集指标过多，无疑会使缓存内存使用变多
- 存储系统磁盘使用降低
  - 持久话存储的磁盘使用量和监控指标的数量是成正比的
- 组件间网络传输流量降低
  - 更多的监控指标数据意味着，组件间网络传输流量更大
- 查询速度提升降低
  - 更多的监控指标意味着查询的速度会被拖慢
```
#采集端高基数
[马士兵vip]
前端,1个亿
标签的值过多
```asp
- Top 10 label names with value count： 标签中value最多的10个
- Top 10 series count by metric names： metric_name匹配的series最多的10个
- Top 10 label names with high memory usage： 标签消耗内存最多的10个
- Top 10 series count by label value pairs： 标签对数量最多的10个
基于内存中的倒排索引 算最大堆取 top10
```
```asp
- 一个高基数的查询会把存储打挂
- 一个50w基数查询1小时数据内存大概的消耗为1G，再叠加cpu等消耗
```
label乘积太多 ，比如bucket有50种，再叠加4个10种的业务标签，所以总基数为`50*10*10*10*10=50w`
[](https://segmentfault.com/a/1190000017553625)
##预查询
[](https://zhuanlan.zhihu.com/p/228042105)

#长期查询降采样
