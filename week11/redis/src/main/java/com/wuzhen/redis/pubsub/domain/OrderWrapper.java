package com.wuzhen.redis.pubsub.domain;

import java.util.List;

/**
 *
 */
public class OrderWrapper {

    public static final String CHANNEL = "order_pub_sub";

    private long orderId;

    private String orderCode;

    private OrderHeader header;

    private List<OrderDetail> details;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public OrderHeader getHeader() {
        return header;
    }

    public void setHeader(OrderHeader header) {
        this.header = header;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
}
