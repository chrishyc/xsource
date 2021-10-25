package mq.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class MyAsyncProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        // 实例化生产者，并指定生产组名称
        DefaultMQProducer producer = new DefaultMQProducer("producer_grp_01");

        // 指定nameserver的地址
        producer.setNamesrvAddr("localhost:9876");

        // 初始化生产者
        producer.start();

        for (int i = 0; i < 100; i++) {

            Message message = new Message(
                    "tp_demo_02",
                    ("hello lagou " + i).getBytes("utf-8")
            );

            // 消息的异步发送
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功:" + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败：" + throwable.getMessage());
                }
            });
        }

        // 由于是异步发送消息，上面循环结束之后，消息可能还没收到broker的响应
        // 如果不sleep一会儿，就报错
        Thread.sleep(10_000);

        // 关闭生产者
        producer.shutdown();
    }
}
