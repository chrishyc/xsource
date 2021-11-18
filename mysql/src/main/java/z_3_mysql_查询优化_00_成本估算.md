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
