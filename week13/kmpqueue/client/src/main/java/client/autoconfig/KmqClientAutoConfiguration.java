package client.autoconfig;

import client.Producer;
import client.QueueAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 */
@Configuration
@EnableConfigurationProperties(KmqClientProperties.class)
public class KmqClientAutoConfiguration {

    private final KmqClientProperties properties;

    public KmqClientAutoConfiguration(KmqClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public QueueAdmin queueAdmin(@Autowired RestTemplate restTemplate) {
        return new QueueAdmin(restTemplate, this.properties);
    }

    @Bean
    public Producer producer(@Autowired RestTemplate restTemplate) {
        return new Producer(restTemplate, this.properties);
    }

}
