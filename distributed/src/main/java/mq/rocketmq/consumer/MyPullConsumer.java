package mq.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

/**
 * 拉取消息的消费者
 */
public class MyPullConsumer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        // 拉取消息的消费者实例化，同时指定消费组名称
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("consumer_grp_01");
        // 设置nameserver的地址
        consumer.setNamesrvAddr("localhost:9876");
        
        // 对消费者进行初始化，然后就可以使用了
        consumer.start();
        
        // 获取指定主题的消息队列集合
        final Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("tp_demo_01");
        
        // 遍历该主题的各个消息队列，进行消费
        for (MessageQueue messageQueue : messageQueues) {
            // 第一个参数是MessageQueue对象，代表了当前主题的一个消息队列
            // 第二个参数是一个表达式，对接收的消息按照tag进行过滤
            // 支持"tag1 || tag2 || tag3"或者 "*"类型的写法；null或者"*"表示不对消息进行tag过滤
            // 第三个参数是消息的偏移量，从这里开始消费
            // 第四个参数表示每次最多拉取多少条消息
            final PullResult result = consumer.pull(messageQueue, "*", 0, 10);
            // 打印消息队列的信息
            System.out.println("message******queue******" + messageQueue);
            // 获取从指定消息队列中拉取到的消息
            final List<MessageExt> msgFoundList = result.getMsgFoundList();
            if (msgFoundList == null) continue;
            for (MessageExt messageExt : msgFoundList) {
                System.out.println(messageExt);
                System.out.println(new String(messageExt.getBody(), "utf-8"));
            }
        }
        
        // 关闭消费者
        consumer.shutdown();
    }
}
