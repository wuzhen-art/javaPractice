package client;

import client.autoconfig.KmqClientProperties;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 */
public class QueueAdmin {

    private final RestTemplate restTemplate;
    private final KmqClientProperties properties;

    private final String CREATED_PATH = "/api/queue/create";
    private final String DELETE_PATH = "/api/queue/delete";

    public QueueAdmin(RestTemplate restTemplate, KmqClientProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;

        if (properties.getQueues().size() > 0) {
            for (String queue : properties.getQueues())
                createQueue(queue);
        }

    }


    /**
     */
    public void createQueue(String queueName) throws RestClientException {
        String url = String.format("http://%s:%d%s/%s", this.properties.getServerAddress(), this.properties.getPort(), this.CREATED_PATH, queueName);
        this.restTemplate.postForEntity(url, null, String.class);
    }

    /**
     */
    public void deleteQueue(String queueName) throws RestClientException {
        String url = String.format("http://%s:%d%s/%s", this.properties.getServerAddress(), this.properties.getPort(), this.DELETE_PATH, queueName);
        this.restTemplate.delete(url);
    }
}
