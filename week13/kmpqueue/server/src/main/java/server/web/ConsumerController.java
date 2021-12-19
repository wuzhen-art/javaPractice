package server.web;

import entity.Message;
import entity.TextMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.QueueRegistry;
import server.core.Queue;

import java.util.Collection;
import java.util.Collections;

/**
 */
@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final QueueRegistry registry;

    public ConsumerController(QueueRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("get/{queueName}/{fetchSize}")
    public Collection<Message> get(@PathVariable("queueName") String queueName, @PathVariable("fetchSize") Integer fetchSize) {
        if (fetchSize == null || fetchSize < 0)
            return Collections.emptyList();

        if (!registry.containsKey(queueName))
            throw new IllegalArgumentException(String.format("queue [%s] not found", queueName));

        Queue queue = this.registry.get(queueName);
        return queue.fetch(fetchSize);
    }

    @PostMapping("confirm")
    public ResponseEntity<String> confirm(@RequestBody TextMessage message) {
        if (!StringUtils.hasLength(message.getQueue()))
            return ResponseEntity.status(500).body("queue must be not empty");

        String queueName = message.getQueue();
        if (!registry.containsKey(queueName))
            return ResponseEntity.status(500).body(String.format("queue [%s] not found", queueName));
        Queue queue = this.registry.get(queueName);
        queue.confirm(message);

        return ResponseEntity.ok("ok");
    }

}
