package com.geek.mq;

import javax.jms.*;
import java.util.function.Consumer;

/**
 * @author 吴振
 * @since 2021/12/12 下午9:18
 */
public class MSConsumer implements MessageListener, Runnable{
    private final Destination destination;
    private final Session session;

    private final Consumer<Message> consumer;

    public MSConsumer(Destination destination, Consumer<Message> consumer) {
        this.destination = destination;
        this.consumer = consumer;
        this.session = SessionProvider.getSession();
    }

    @Override
    public void run() {
        try {
            MessageConsumer consumer = session.createConsumer(this.destination);
            consumer.setMessageListener(this);

            while (true) {
                //todo 阻塞线程
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        this.consumer.accept(message);
    }
}
