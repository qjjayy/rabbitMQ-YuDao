package com.example.rabbitmq.message;

import java.io.Serializable;

public class BroadcastMessage implements Serializable {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_BROADCASTING";
    public static final String EXCHANGE = "EXCHANGE_BROADCASTING";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public BroadcastMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "BroadcastMessage{" + "id=" + id + '}';
    }

}
