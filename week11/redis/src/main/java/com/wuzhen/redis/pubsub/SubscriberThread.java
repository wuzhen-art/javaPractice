package com.wuzhen.redis.pubsub;

import com.wuzhen.redis.config.ConnectionProvider;
import com.wuzhen.redis.pubsub.domain.OrderWrapper;
import redis.clients.jedis.Jedis;

/**
 * 订阅者线程
 *
 */
public class SubscriberThread implements Runnable {

    private final ConnectionProvider connectionProvider = ConnectionProvider.getInstance();

    @Override
    public void run() {
        Jedis jedis = null;
        try {
            jedis = connectionProvider.getConnection();
            jedis.subscribe(new OrderSubscriber(), OrderWrapper.CHANNEL);
        } catch (Exception e) {
            System.out.println(String.format("subsrcibe channel error, %s", e));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
