package cn.deveye.learning.rocketmq.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        final DefaultMQProducer defaultMQProducer = new DefaultMQProducer("test_producer");
        //这里设置NamesrvAddress
        defaultMQProducer.setNamesrvAddr("39.105.157.176:9876");
        defaultMQProducer.start();

        //启动10个线程使用producer不停的发消息
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Message message = new Message(
                                "Topic_test",
                                "TagA",
                                "test".getBytes(RemotingHelper.DEFAULT_CHARSET));
                        SendResult send = defaultMQProducer.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        while (true) {
            Thread.sleep(1000);
        }
    }
}
