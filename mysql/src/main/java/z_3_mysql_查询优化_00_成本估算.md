##估算页面数 & 条目
##成本计算
I/O 成本
```
mysql> SHOW TABLE STATUS LIKE 'single_table'\G
*************************** 1. row ***************************
           Name: single_table
         Engine: InnoDB
        Version: 10
     Row_format: Dynamic
           Rows: 14399,//估算值
 Avg_row_length: 110
    Data_length: 1589248,//Data_length = 聚簇索引的页面数量 x 每个页面的大小
Max_data_length: 0
   Index_length: 3047424
      Data_free: 4194304
 Auto_increment: 46177
    Create_time: 2021-11-05 18:11:33
    Update_time: NULL
     Check_time: NULL
      Collation: utf8_general_ci
       Checksum: NULL
 Create_options:
        Comment:
```
```asp
读取一个页面花费的 成本默认是 1.0
页面数=1589248/16/1024=97
I/O 成本: 97 x 1.0 + 1.1 = 98.1

内存检测一条记录是否符合搜索条件的成本默认是 0.2
cpu成本: 14399 x 0.2 + 1.0 = 2880.8

总成本: 98.1 + 2880.8

```
#成本
在 EXPLAIN 单词和真正的查询语句中间加上 FORMAT=JSON 
EXPLAIN FORMAT=JSON SELECT * FROM s1 INNER JOIN s2 ON s1.key1 = s2.key2 WHERE s1.co
#优化过程
优化过程大
致分为了三个阶段:
prepare 阶段 
optimize 阶段 
execute 阶段
对于单表查询来说，我们主要关注 optimize 阶段的 "rows_estimation" 这个过程，这个过程深入分析了对单表查询的各种执行方案的成本;

对于多表连接查询来 说，我们更多需要关注 "considered_execution_plans" 这个过程，这个过程里会写明各种不同的连接方式所对 应的成本。
反正优化器最终会选择成本最低的那种方案来作为最终的执行计划，也就是我们使用 EXPLAIN 语句所 展现出的那种方案。
