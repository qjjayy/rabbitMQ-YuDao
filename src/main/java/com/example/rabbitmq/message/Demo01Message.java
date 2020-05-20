package com.example.rabbitmq.message;

import java.io.Serializable;

public class Demo01Message implements Serializable {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_DEMO_01";
    public static final String EXCHANGE = "EXCHANGE_DEMO_01";
    public static final String ROUTING_KEY = "ROUTING_KEY_01";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo01Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo01Message{" + "id=" + id + '}';
    }

}
