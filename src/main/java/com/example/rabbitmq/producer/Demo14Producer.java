package com.example.rabbitmq.producer;

import com.example.rabbitmq.message.Demo14Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Demo14Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String syncSend(Integer id) {
        // 创建 Demo14Message 消息
        Demo14Message message = new Demo14Message();
        message.setId(id);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 同步发送消息
        return (String) rabbitTemplate.convertSendAndReceive(
                Demo14Message.EXCHANGE, Demo14Message.ROUTING_KEY, message, correlationData);
    }
}
