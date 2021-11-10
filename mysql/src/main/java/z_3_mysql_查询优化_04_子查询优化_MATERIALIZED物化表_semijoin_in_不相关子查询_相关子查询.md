#子查询相关性
##标量子查询&行子查询
子查询结果为单值或者单行
##相关子查询 VS 不相关子查询
不相关子查询:内层子查询不依赖外层查询
相关子查询:内层子查询依赖外层查询
##标量子查询或者行子查询
###不相关
```asp
SELECT * FROM s1 WHERE key1 = (SELECT common_field FROM s2 WHERE key3 = 'a' LIMIT 1);
先单独执行 (SELECT common_field FROM s2 WHERE key3 = 'a' LIMIT 1) 这个子查询。 
然后在将上一步子查询得到的结果当作外层查询的参数再执行外层查询 SELECT * FROM s1 WHERE key1 = ... 。
对于包含不相关的标量子查询或者行子查询的查询语句来说，MySQL会分别独立的执行外层查询和子 查询，就当作两个单表查询就好了
```
###相关
```asp
SELECT * FROM s1 WHERE key1 = (SELECT common_field FROM s2 WHERE s1.key3 = s2.key3 LIMIT 1);
先从外层查询中获取一条记录，本例中也就是先从 s1 表中获取一条记录。 然后从上一步骤中获取的那条记录中找出子查询中涉及到的值，
本例中就是从 s1 表中获取的那条记录中找 出 s1.key3 列的值，然后执行子查询。
最后根据子查询的查询结果来检测外层查询 WHERE 子句的条件是否成立，如果成立，就把外层查询的那条记 录加入到结果集，否则就丢弃。
再次执行第一步，获取第二条外层查询中的记录，依次类推~
```
#子查询物化MATERIALIZED&Materialization
[](http://mysql.taobao.org/monthly/2016/07/08/)
[](https://dev.mysql.com/doc/refman/5.6/en/subquery-materialization.html)
```asp
Materialization speeds up query execution by generating a subquery result as a temporary table, normally in memory. 
The first time MySQL needs the subquery result, it materializes that result into a temporary table
在SQL执行过程中，第一次需要子查询结果时执行子查询并将子查询的结果保存为临时表 ，后续对子查询结果集的访问将直接通过临时表获得
在IN/NOT IN子查询以及 FROM 子查询
Subquery materialization uses an in-memory temporary table when possible, falling back to on-disk storage if the table becomes too larg
物化子查询优化SQL执行的关键点在于对子查询只需要执行一次。 与之相对的执行方式是对外表的每一行都对子查询进行调用，其执行计划中的查询类型为“DEPENDENT SUBQUERY”
```
#DEPENDENT SUBQUERY(相关子查询)
```asp
A correlated subquery is a subquery that contains a reference to a table that also appears in the outer query
SELECT * FROM t1
  WHERE column1 = ANY (SELECT column1 FROM t2
                       WHERE t2.column2 = t1.column2);
```
[](https://dev.mysql.com/doc/refman/8.0/en/correlated-subqueries.html)
[](https://www.cnblogs.com/zhengyun_ustc/p/slowquery3.html)
