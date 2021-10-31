package mq.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class MultiProducer {
    
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 在实例化生产者的同时，指定了生产组名称
        for (int i = 0; i < 2; i++) {
            DefaultMQProducer producer = new DefaultMQProducer("myproducer_grp_0" + i);
            
            // 指定NameServer的地址
            producer.setNamesrvAddr("localhost:9876");
            
            // 对生产者进行初始化，然后就可以使用了
            producer.start();
            
            // 创建消息，第一个参数是主题名称，第二个参数是消息内容
            Message message = new Message(
                    "tp_demo_01",
                    "hello lagou 01".getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // 发送消息
            final SendResult result = producer.send(message);
            System.out.println(result);
        }
        
    }
    
    public int minimumOperations(int[] nums, int start, int goal) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        Set<Integer> visited = new HashSet<>();
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer cur = queue.poll();
                if (visited.contains(cur)) continue;
                if (cur < 0 || cur > 1000) continue;
                visited.add(cur);
                if (cur == goal) return level;
                for (int j = 0; j < nums.length; j++) {
                    queue.offer(cur + nums[j]);
                    queue.offer(cur - nums[j]);
                    queue.offer(cur ^ nums[j]);
                }
            }
            level++;
        }
        return -1;
    }
    
    
}
