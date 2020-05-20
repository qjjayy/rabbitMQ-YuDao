package com.example.rabbitmq.message;


public class Demo15Message {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_DEMO_15";
    public static final String EXCHANGE = "EXCHANGE_DEMO_15";
    public static final String ROUTING_KEY = "ROUTING_KEY_15";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo15Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo15Message{" + "id=" + id + '}';
    }

}
