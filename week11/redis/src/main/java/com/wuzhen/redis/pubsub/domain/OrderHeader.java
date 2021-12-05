package com.wuzhen.redis.pubsub.domain;

/**
 *
 */
public class OrderHeader {

    private long id;

    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
