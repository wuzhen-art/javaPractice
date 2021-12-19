package server.web;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.QueueRegistry;
import server.core.MemoryQueue;
import server.core.Queue;

import java.io.IOException;

/**
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController {


    private final QueueRegistry registry;

    public QueueController(QueueRegistry registry) {
        this.registry = registry;
    }

    @PostMapping("/create/{queueName}")
    public String create(@PathVariable("queueName") String queueName) {
        if (this.registry.containsKey(queueName))
            throw new IllegalStateException(String.format("the queue [%s] existed", queueName));

        this.registry.put(queueName, new MemoryQueue(queueName));
        return "ok";
    }

    @DeleteMapping("delete/{queueName}")
    public String delete(@PathVariable("queueName") String queueName) throws IOException {
        if (!this.registry.containsKey(queueName))
            throw new IllegalStateException(String.format("the queue [%s] not existed", queueName));

        Queue queue = this.registry.get(queueName);
        queue.close();
        return "ok";
    }

}
