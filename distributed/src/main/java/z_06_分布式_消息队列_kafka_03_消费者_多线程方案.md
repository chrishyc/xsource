#
rebalance

#消费者两种多线程方案
消费者io:线程=1：1
单线程io:多线程work=1:n
[](https://time.geekbang.org/column/article/108512)
rocketmq多线程怎么维护offset
##Flink多线程维护offset

```
多线程+管理offset
Spark Streaming和Flink
```
##队列维护
```asp
对于第二种方案，可以添加一个共享的队列，消费线程消费完一个记录，就写入队列，然后主线程可以读取这个队列，然后依次提交小号的offset

```
[深入理解kafka核心设计3.2.10]
