package mq.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 推送消息的消费
 */
public class MyPushConsumer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        // 实例化推送消息消费者的对象，同时指定消费组名称
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_grp_02");

        // 指定nameserver的地址
        consumer.setNamesrvAddr("localhost:9876");

        // 订阅主题
        consumer.subscribe("tp_demo_02", "*");

        // 添加消息监听器，一旦有消息推送过来，就进行消费
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                final MessageQueue messageQueue = context.getMessageQueue();
                System.out.println(messageQueue);

                for (MessageExt msg : msgs) {
                    try {
                        System.out.println(new String(msg.getBody(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                // 消息消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                // 消息消费失败
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        // 初始化消费者，之后开始消费消息
        consumer.start();

        // 此处只是示例，生产中除非运维关掉，否则不应停掉，长服务
//        Thread.sleep(30_000);
//        // 关闭消费者
//        consumer.shutdown();
    }
}
