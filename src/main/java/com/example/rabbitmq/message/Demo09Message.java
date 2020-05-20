package com.example.rabbitmq.message;

import java.io.Serializable;

public class Demo09Message implements Serializable {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_DEMO_09";
    public static final String EXCHANGE = "EXCHANGE_DEMO_09";
    public static final String ROUTING_KEY = "ROUTING_KEY_09";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo09Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo09Message{" + "id=" + id + '}';
    }

}
