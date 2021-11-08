#日志分析
##执行延时
SET profiling = 1;
SHOW PROFILES
SHOW PROFILE
show profile for query 1;
![](.z_3_mysql_优化体系_性能分析_images/1eac9e9d.png)
##explain
[](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html)
type = NULL，MYSQL不用访问表或者索引就直接能到结果
##count(*) vs count(name)
