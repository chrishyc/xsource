#
_source
https://cloud.tencent.com/developer/article/1682562
https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-source-field.html
#分片数量优化
https://www.elastic.co/cn/blog/how-many-shards-should-i-have-in-my-elasticsearch-cluster
#ILM管理
https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-index-lifecycle-management.html#ilm-gs-create-policy
#滚动
index.lifecycle.name
index.lifecycle.rollover_alias

#日期索引名
https://www.elastic.co/guide/en/elasticsearch/reference/current/date-math-index-names.html
PUT /%3Cmy-index-%7Bnow%2Fd%7D%3E
{
  "aliases": {
    "logs_write": {}
  }
}
https://wiki.eryajf.net/pages/5153.html#_2-%E7%B4%A2%E5%BC%95%E7%AD%96%E7%95%A5%E8%AF%A6%E8%A7%A3

#自动创建索引
#自动建立类型映射
One of the most important features of Elasticsearch is that it tries to get out of your way and let you start exploring your data as quickly as possible. To index a document, you don’t have to first create an index, define a mapping type, and define your fields — you can just index a document and the index, type, and fields will display automatically:
https://www.elastic.co/guide/en/elasticsearch/reference/current/dynamic-mapping.html
