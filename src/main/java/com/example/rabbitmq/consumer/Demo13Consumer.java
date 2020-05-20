package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.Demo13Message;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = Demo13Message.QUEUE, ackMode = "MANUAL")
public class Demo13Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo13Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException, InterruptedException {
        Thread.sleep(10000);
        logger.info("[onMessage][线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
        Thread.sleep(50000);
        channel.basicAck(deliveryTag, false);
    }
}
