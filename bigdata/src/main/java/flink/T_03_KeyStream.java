package flink;

import lombok.Data;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;


public class T_03_KeyStream {
  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    DataStream<Transaction> transactions = env
        .addSource(new TransactionSource())
        .name("transactions");

    DataStream<Alert> alerts = transactions
        .keyBy(Transaction::getAccountId)
        .process(new T_04_ProcessFunction())
        .name("fraud-detector");

    alerts
        .addSink(new AlertSink())
        .name("send-alerts");

    env.execute("Fraud Detection");
  }

}
