package com.example.rabbitmq.message;

import java.io.Serializable;

public class ClusteringMessage implements Serializable {
    // RabbitTemplate 默认使用Java自带的序列化方式

    public static final String QUEUE = "QUEUE_CLUSTERING";
    public static final String EXCHANGE = "EXCHANGE_CLUSTERING";

    // 编号
    private Integer id;

    public Integer getId() {
        return id;
    }

    public ClusteringMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "ClusteringMessage{" + "id=" + id + '}';
    }

}
