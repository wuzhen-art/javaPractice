package com.wuzhen.redis.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuzhen.redis.config.ConnectionProvider;
import com.wuzhen.redis.pubsub.domain.OrderWrapper;
import redis.clients.jedis.Jedis;

/**
 * 订单发布
 */
public class OrderPublisher {

    private final ConnectionProvider connectionProvider = ConnectionProvider.getInstance();

    public void publishOrder(OrderWrapper order) {
        Jedis jedis = this.connectionProvider.getConnection();
        try {
            jedis.publish(OrderWrapper.CHANNEL, new ObjectMapper().writeValueAsString(order));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }



}
