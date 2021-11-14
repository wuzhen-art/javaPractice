package com.geektime.week8.sharding_sphere.crud_homework_1.auto_config;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Properties;

/**
 * @author 吴振
 * @since 2021/11/15 上午12:10
 */
public class RedisIdGenerator implements ShardingKeyGenerator {

    private Properties properties;

    private static StringRedisTemplate redisTemplate = null;

    public static void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisIdGenerator.redisTemplate = redisTemplate;
    }

    /**
     * Generate key.
     *
     * @return generated key
     */
    @Override
    public Comparable<?> generateKey() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment("id");
    }

    /**
     * Get algorithm type.
     *
     * @return type
     */
    @Override
    public String getType() {
        return "redis_auto_increment";
    }

    /**
     * Get properties.
     *
     * @return properties of algorithm
     */
    @Override
    public Properties getProperties() {
        return this.properties;
    }

    /**
     * Set properties.
     *
     * @param properties properties of algorithm
     */
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

