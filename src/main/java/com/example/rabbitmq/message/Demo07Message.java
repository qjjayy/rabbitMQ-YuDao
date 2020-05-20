package com.example.rabbitmq.message;

import java.io.Serializable;

public class Demo07Message implements Serializable {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_DEMO_07";
    public static final String EXCHANGE = "EXCHANGE_DEMO_07";
    public static final String ROUTING_KEY = "ROUTING_KEY_07";

    public static final String DEAD_QUEUE = "DEAD_QUEUE_DEMO_07";
    public static final String DEAD_ROUTING_KEY = "DEAD_ROUTING_KEY_07";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo07Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo07Message{" + "id=" + id + '}';
    }

}
