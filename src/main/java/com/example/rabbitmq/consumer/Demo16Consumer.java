package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.Demo16Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Demo16Message.QUEUE,
                errorHandler = "rabbitListenerErrorHandler")
public class Demo16Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo16Message message) {
        logger.info("[onMessage][线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
        throw new RuntimeException("你猜");
    }

}
