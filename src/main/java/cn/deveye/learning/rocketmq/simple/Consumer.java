package cn.deveye.learning.rocketmq.simple;


import cn.deveye.learning.rocketmq.Constants;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_consumer");
        //这里设置NamesrvAddress
        consumer.setNamesrvAddr(Constants.NAME_SRV_ADDR);

        //订阅Topic 要消费那些消息
        consumer.subscribe("Topic_test", "*");

        //设置一个回调接口去接收获取到的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(messages);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

}
