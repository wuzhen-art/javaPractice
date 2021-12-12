package com.geek.mq;

import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;


/**
 * @author 吴振
 * @since 2021/12/12 下午9:21
 */
public class TopicMainTest {


    public static void main() throws Exception {
        Destination destination = new ActiveMQTopic("test.topic");
        MQPublisher publisher = new MQPublisher(destination);
        for (int i=0 ; i < 10 ; i++) {
            Thread subscriberThread = new Thread(new MSConsumer(destination, message -> {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.printf("thread: [%s] received message : %s \n", Thread.currentThread().getName(), textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }));
            subscriberThread.start();
        }


        publisher.sendTextMessage("Hello World");

    }
}
