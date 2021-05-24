package cn.deveye.learning.rocketmq.simple;

import cn.deveye.learning.rocketmq.Constants;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * oneway (单向模式) 生产者异步投递消息不需要callback ，适用于日志消息投递，允许丢失数据的场景下使用
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("oneway_producer");
        // Specify name server addresses.
        producer.setNamesrvAddr(Constants.NAME_SRV_ADDR);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("Topic_test" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        //Wait for sending to complete
        Thread.sleep(5000);
        producer.shutdown();
    }
}
