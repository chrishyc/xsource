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
# index alias
[](https://www.elastic.co/guide/en/elasticsearch/reference/current/aliases.html)
If you use aliases in your application’s Elasticsearch requests, you can reindex data with no downtime or changes to your app’s code.
A data stream alias points to one or more data streams.
An index alias points to one or more indices.
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
#Ingest pipelines
Ingest pipelines let you perform common transformations on your data before indexing. For example, you can use pipelines
 to remove fields, extract values from text, and enrich your data
##processors
