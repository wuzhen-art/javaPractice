package client.consumers;

import client.Consumer;
import client.autoconfig.KmqClientProperties;
import entity.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 */
@Component
public class TopicConsumer extends Consumer {

    public TopicConsumer(RestTemplate restTemplate, KmqClientProperties properties) {
        super(restTemplate, properties);
    }

    @Override
    public void onMessage(Message<?> message) {
        System.out.println("received!");
        System.out.println(message.getPayload());
    }

    @Override
    public String getQueueName() {
        return "topic";
    }
}
