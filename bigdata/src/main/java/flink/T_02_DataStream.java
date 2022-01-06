package flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Flink程序开发的流程总结如下:
 * 1)获得一个执行环境
 * 2)加载/创建初始化数据
 * 3)指定数据操作的算子
 * 4)指定结果数据存放位置
 * 5)调用execute()触发执行程序 注意:Flink程序是延迟计算的，只有最后调用execute()方法的时候才会真正触发执行程序
 */
public class T_02_DataStream {
    public static void main(String[] args) throws Exception {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        // 获取Flink流执行环境
        StreamExecutionEnvironment streamExecutionEnvironment =
                StreamExecutionEnvironment.getExecutionEnvironment();
        // 获取socket输入数据
        DataStreamSource<String> textStream =
                streamExecutionEnvironment.socketTextStream(ip, port, "\n");
        SingleOutputStreamOperator<Tuple2<String, Long>>
                tuple2SingleOutputStreamOperator = textStream.flatMap((FlatMapFunction<String, Tuple2<String, Long>>) (s, collector) -> {
            String[] splits = s.split("\\s");
            for (String word : splits) {
                collector.collect(Tuple2.of(word, 1L));
            }
        });
        SingleOutputStreamOperator<Tuple2<String, Long>> word =
                tuple2SingleOutputStreamOperator.keyBy("word")
                        .timeWindow(Time.seconds(2), Time.seconds(1))
                        .sum(1); // 打印数据
        word.print();
        // 触发任务执行
        streamExecutionEnvironment.execute("wordcount stream process");
    }
    
}
