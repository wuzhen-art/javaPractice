package server.web;

import entity.TextMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.QueueRegistry;
import server.core.Queue;

/**
 */
@RestController
@RequestMapping("/api/producer")
public class ProducerController {


    private final QueueRegistry registry;

    public ProducerController(QueueRegistry registry) {
        this.registry = registry;
    }

    @PostMapping("post")
    public ResponseEntity<String> postMessage(@RequestBody TextMessage message) {
        if (!StringUtils.hasLength(message.getQueue()))
            return ResponseEntity.status(500).body("queue must be not empty");

        String queueName = message.getQueue();
        if (!registry.containsKey(queueName))
            return ResponseEntity.status(500).body(String.format("queue [%s] not found", queueName));
        Queue queue = this.registry.get(queueName);
        queue.receive(message);

        return ResponseEntity.ok("ok");
    }

    @PostMapping("resend")
    public ResponseEntity<String> resendMessage(@RequestBody TextMessage message) {
        if (!StringUtils.hasLength(message.getQueue()))
            return ResponseEntity.status(500).body("queue must be not empty");

        String queueName = message.getQueue();
        if (!registry.containsKey(queueName))
            return ResponseEntity.status(500).body(String.format("queue [%s] not found", queueName));
        Queue queue = this.registry.get(queueName);
        queue.resend(message);

        return ResponseEntity.ok("ok");
    }



}
