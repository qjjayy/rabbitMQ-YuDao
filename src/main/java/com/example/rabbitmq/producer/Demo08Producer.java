package com.example.rabbitmq.producer;

import com.example.rabbitmq.message.Demo08Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo08Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, Integer delay) {
        // 创建 Demo08Message 消息
        Demo08Message message = new Demo08Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo08Message.EXCHANGE, Demo08Message.ROUTING_KEY, message, message1 -> {
            if (delay != null && delay > 0) {
                message1.getMessageProperties().setExpiration(String.valueOf(delay));
            }
            return message1;
        });
    }

}
