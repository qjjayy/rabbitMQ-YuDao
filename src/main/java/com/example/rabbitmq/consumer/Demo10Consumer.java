package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.Demo10Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = Demo10Message.QUEUE_0)
@RabbitListener(queues = Demo10Message.QUEUE_1)
@RabbitListener(queues = Demo10Message.QUEUE_2)
@RabbitListener(queues = Demo10Message.QUEUE_3)  // 顺序消费
public class Demo10Consumer {
    // 如果启动相同 Consumer 的多个进程，则使用分布式锁设置多个进程中的相同的 Consumer 消费的 Queue 的分配，保证有且仅有一个 Consumer 开启对同一个 Queue 的消费。

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Message<Demo10Message> message) {
        logger.info("[onMessage][线程编号: {} 消息编号: {}]", Thread.currentThread().getId(), getQueue(message));
    }

    private static String getQueue(Message<Demo10Message> message) {
        return message.getHeaders().get("amqp_consumerQueue", String.class);
    }

}
