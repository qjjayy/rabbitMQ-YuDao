package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.Demo08Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Demo08Message.DEAD_QUEUE)
public class Demo08DeadConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo08Message message) {
        logger.info("[onMessage][ [延迟队列] 线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
    }

}
