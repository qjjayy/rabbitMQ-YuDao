package com.example.rabbitmq.producer;

import com.example.rabbitmq.message.Demo13Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo13ProducerC {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo13Message 消息
        Demo13Message message = new Demo13Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo13Message.EXCHANGE, "error", message,  new CorrelationData(String.valueOf(id)));
    }

}
