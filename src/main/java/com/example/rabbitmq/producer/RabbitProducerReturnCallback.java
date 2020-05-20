package com.example.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 当 Producer 成功发送消息到 RabbitMQ Broker 时，但是在通过 Exchange 进行匹配不到 Queue 时，Broker 会将该消息回退给 Producer 。
**/
@Component
public class RabbitProducerReturnCallback implements RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public RabbitProducerReturnCallback(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setReturnCallback(this);  // 在构造方法中，把自己设置到 RabbitTemplate 中，作为 Return 的回调
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("[returnedMessage][message: [{}] replyCode: [{}] replayText: [{}] exchange: [{}] routingKey: [{}]]",
                message, replyCode, replyText, exchange, routingKey);
    }
}
