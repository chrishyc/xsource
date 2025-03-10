# 根据两张表的主键join两张表数据
=VLOOKUP(B2,实体数据源!A:C,3,FALSE)
1.查询B列第二行
2.搜索范围(实体数据源! A:C列)
3.填充哪一列
4.FALSE,精确匹配

# 上下两列相等时,拼接上下两列字符串
=TEXTJOIN(",",FALSE,IF(A3=A2,B3,""),IF(A3=A2,B2,""))