package com.wuzhen.redis.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuzhen.redis.pubsub.domain.OrderWrapper;
import redis.clients.jedis.JedisPubSub;

/**
 * 订单订阅者
 */
public class OrderSubscriber extends JedisPubSub {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(String channel, String message) {
        try {
            OrderWrapper wrapper = objectMapper.readValue(message, OrderWrapper.class);
            System.out.println("received order with id is " + wrapper.getOrderId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }
}
