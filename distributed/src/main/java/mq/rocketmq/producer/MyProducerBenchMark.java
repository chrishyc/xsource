package mq.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class MyProducerBenchMark {

    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 在实例化生产者的同时，指定了生产组名称
        DefaultMQProducer producer = new DefaultMQProducer("myproducer_grp_01");

        // 指定NameServer的地址
        producer.setNamesrvAddr("localhost:9876");

        // 对生产者进行初始化，然后就可以使用了
        producer.start();
    
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                while(true){
                    Message msg = null;
                    try {
                        msg = new Message("TopicTest", "TagA",
                                ("test").getBytes(RemotingHelper.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    SendResult sendResult = null;
                    try {
                        sendResult = producer.send(msg);
                    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("%s%n", sendResult);
                }
            }).start();
        }
    
        while(true){
            Thread.sleep(1000);
        }
    }


}
