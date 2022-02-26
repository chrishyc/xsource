#count(*) & count(1) & count(field)
[](https://time.geekbang.org/column/article/72775)
##count为啥需要实时计算行数？
MVCC
##三者选择
count(*)=count(1)<count(field),count(field)要计算field是否为null
#线上慢查询问题
##count(*)
定时任务查询数据库master主库,过多的统计查询产生太多慢查询,导致mysql负载很高,出事故
最后这些都走从库查询
#优化
group by ,order by ,filesort
join
where ,,,
