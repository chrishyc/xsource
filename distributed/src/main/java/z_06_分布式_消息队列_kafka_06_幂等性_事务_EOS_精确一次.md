[](https://time.geekbang.org/column/article/132096)
#Exactly Once
#flink 内部精确一次
flink barrier
#flink端到端精确一次(flink barrier+kafka 事务)
#flink为啥自己不能保证精确一次?
checkpoint异步,可能多次消费
#kafka事务为啥还需要幂等支持?
一个事务中可能有多个消息发送出来,也有可能一个消息重复发送,可以使用幂等性去重
#协调器挂了咋办?
