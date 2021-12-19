package server;

import org.springframework.stereotype.Component;
import server.core.Queue;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 队列注册中心
 */
@Component
public final class QueueRegistry extends ConcurrentHashMap<String, Queue> {



}
