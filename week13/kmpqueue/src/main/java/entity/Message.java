package entity;

import java.io.Serializable;

/**
 */
public interface Message<T extends Serializable> {

    /**
     * @return 消息id
     */
    String getId();

    MessageHeaders getHeaders();

    T getPayload();

    String getQueue();

}
