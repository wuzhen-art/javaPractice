package com.geek.mq;

import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author 吴振
 * @since 2021/12/12 下午9:21
 */
public class QueueMainTest {


    public static void main(String[] args) throws JMSException {
        Destination destination = new ActiveMQQueue("test.queue");
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


        publisher.sendTextMessage("consumer");
    }
}
