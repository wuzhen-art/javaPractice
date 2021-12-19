package server.core;


import entity.Message;

import java.io.Closeable;
import java.util.Collection;

/**
 */
public interface Queue extends Closeable {

    /**
     * @return 队列名称
     */
    String getName();

    /**
     * 从队列中抓取消息
     * @param maxFetchSize 最大抓取消息个数
     * @return 消息集合
     */
    Collection<Message> fetch(int maxFetchSize);

    /**
     * 接受消息
     * @param message 消息
     * @return 返回偏移量
     */
    int receive(Message message);

    /**
     * 消息确认
     * @param message 消息
     * @return 确认成功与否
     */
    boolean confirm(Message message);

    /**
     * 消息重发
     * @param message 需要重发的消息
     */
    void resend(Message message);

}
