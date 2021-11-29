#data
Elasticsearch makes JSON documents searchable and aggregatable. 
The documents are stored in an index or data stream, which represent one type of data.
#filter
Searchable means that you can filter the documents for conditions. For example, you can filter for data "within the last 7 days" 
or data that "contains the word Kibana". Kibana provides many ways for you to construct filters, which are also called queries or search terms.
#Aggregatable
Aggregatable means that you can extract summaries from matching documents. The simplest aggregation is count, and it is frequently used 
in combination with the date histogram, to see count over time. The terms aggregation shows the most frequent values.
#query
[](https://www.elastic.co/guide/en/elasticsearch/reference/7.15/query-dsl-query-string-query.html#query-string-syntax)
#data stream
append-only time series data
[](https://www.elastic.co/guide/en/elasticsearch/reference/7.15/data-streams.html#data-streams)
.ds-<data-stream>-<yyyy.MM.dd>-<generation>
#time-series-data
#ILM
index lifecycle management (ILM)

#rollover
#index
创建模板不存在，会自动创建，并使用内置模板
批量

query,match_all,range
runtime_mappings

#index aliases
#index别名迁移
#index template
Templates are configured prior to index creation

An index template is a way to tell Elasticsearch how to configure an index when it is created
The template contains the mappings and settings used to configure the stream’s backing indices

Since logs-my_app-default doesn’t exist, the request automatically creates it using the built-in logs-*-* index template
[](https://www.elastic.co/guide/en/elasticsearch/reference/current/index-mgmt.html)
# index alias
[](https://www.elastic.co/guide/en/elasticsearch/reference/current/aliases.html)
If you use aliases in your application’s Elasticsearch requests, you can reindex data with no downtime or changes to your app’s code.
A data stream alias points to one or more data streams.
An index alias points to one or more indices.

An alias is a secondary name for a group of data streams or indices. Most Elasticsearch APIs accept an alias in place of a data stream or index name.

You can change the data streams or indices of an alias at any time. If you use aliases in your application’s Elasticsearch requests, you can reindex data with no downtime or changes to your app’s code.

There are two types of aliases:

A data stream alias points to one or more data streams.
An index alias points to one or more indices.
An alias cannot point to both data streams and indices. You also cannot add a data stream’s backing index to an index alias
```asp
"index": "my-index-2099.05.06-000001",
"alias": "my-alias",
```

routing
[](https://www.elastic.co/guide/en/elasticsearch/reference/current/aliases.html#alias-routing)
#reindex
#component_template
#Kibana index
You can also manage index templates from Stack Management in Kibana.
#Dynamic mappings & mapping
The automatic detection and addition of new fields is called dynamic mapping
动态匹配

#代码怎么使用 rest api
#操作index doc方式
rest api,kibana
#data tier 
[](https://www.elastic.co/guide/en/elasticsearch/reference/current/data-tiers.html)
#Ingest pipelines
Ingest pipelines let you perform common transformations on your data before indexing. For example, you can use pipelines
 to remove fields, extract values from text, and enrich your data
##processors
