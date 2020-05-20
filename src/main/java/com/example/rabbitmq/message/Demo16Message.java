package com.example.rabbitmq.message;


public class Demo16Message {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_DEMO_16";
    public static final String EXCHANGE = "EXCHANGE_DEMO_16";
    public static final String ROUTING_KEY = "ROUTING_KEY_16";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo16Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo16Message{" + "id=" + id + '}';
    }

}
