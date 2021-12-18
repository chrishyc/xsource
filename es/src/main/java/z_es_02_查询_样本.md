```asp
POST /indexx/_search?size=0&&filter_path=took,aggregations.sum_monthly_sales.*,aggregations.sum_monthly_sales_1.*,aggregations.p0_var_distinct_count
{
  "aggs": {
    "p0_var_distinct_count": {
      "cardinality": {
        "precision_threshold": 40000, 
        "field": "var_name"
      }
    },
    "p0_var_agg":{
      "terms": {
        "field": "var_name",
        "size": 2147483647
      },
      "aggs": {
        "p1_variable_latest_top": {
          "top_metrics": {
            "metrics":[{"field":"query_count_alert_status"}],
            "sort":{"timestamp":"desc"},
            "size":1
          }
        },
      "t-shirt-percentage":{
        "bucket_selector":{
          "buckets_path":{
            "_value0":"p1_variable_latest_top.query_count_alert_status"
          },
          "script": "params._value0>0"
        }
        
      }
      }
    },
    "p0_var_agg_1":{
      "terms": {
        "field": "var_name",
        "size": 2147483647
      },
      "aggs": {
        "p1_variable_latest_top_1": {
          "top_metrics": {
            "metrics":[
            {"field":"success_rate"}],
            "sort":{"timestamp":"desc"},
            "size":1
          }
        },
      "t-shirt-percentage_1":{
        "bucket_selector":{
          "buckets_path":{
            "_value0":"p1_variable_latest_top_1.success_rate"
          },
          "script": "params._value0>0.99"
        }
        
      }
      }
    },
    "sum_monthly_sales": {
      "stats_bucket": {
        "buckets_path": "p0_var_agg_1._count"
      }
    },
    "sum_monthly_sales_1": {
      "stats_bucket": {
        "buckets_path": "p0_var_agg._count"
      }
    }
  }
}
```
