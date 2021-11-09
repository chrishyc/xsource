##子查询物化Materialization
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
##DEPENDENT SUBQUERY(相关子查询)
```asp
A correlated subquery is a subquery that contains a reference to a table that also appears in the outer query
SELECT * FROM t1
  WHERE column1 = ANY (SELECT column1 FROM t2
                       WHERE t2.column2 = t1.column2);
```
[](https://dev.mysql.com/doc/refman/8.0/en/correlated-subqueries.html)
[](https://www.cnblogs.com/zhengyun_ustc/p/slowquery3.html)
