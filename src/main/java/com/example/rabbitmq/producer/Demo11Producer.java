package com.example.rabbitmq.producer;

import com.example.rabbitmq.message.Demo11Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Demo11Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void syncSend(Integer id) throws InterruptedException {
        // 创建 Demo11Message 消息
        Demo11Message message = new Demo11Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo11Message.EXCHANGE, Demo11Message.ROUTING_KEY, message);
        Thread.sleep(10000);
    }

}
