package com.geek.mq;

import javax.jms.*;

/**
 * @author 吴振
 * @since 2021/12/12 下午9:11
 */
public class MQPublisher {

    private final Destination destination;
    private final MessageProducer producer;
    private final Session session;

    public MQPublisher(Destination destination) throws JMSException {
        this.destination = destination;
        this.session = SessionProvider.getSession();
        this.producer = this.session.createProducer(this.destination);
    }

    public void sendTextMessage(String message) throws JMSException {
        TextMessage textMessage = this.session.createTextMessage(message);
        this.producer.send(textMessage);
    }
}
