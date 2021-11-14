package com.geektime.week8.sharding_sphere.crud_homework_1.auto_config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 吴振
 * @since 2021/11/15 上午12:14
 */
@Configuration
public class ShardingConfiguration implements InitializingBean, BeanFactoryAware {

    private BeanFactory beanFactory;

    public void afterPropertiesSet() throws Exception {
        //被迫这样注入，希望后续能改进，支持Spring Bean
        StringRedisTemplate redisTemplate = beanFactory.getBean(StringRedisTemplate.class);
        RedisIdGenerator.setRedisTemplate(redisTemplate);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
