package com.example.rabbitmq;

import com.example.rabbitmq.producer.Demo14Producer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Demo14ProducerTest extends RabbitmqTestApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo14Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        String result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号: [{}] 发送成功 消费结果: [{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await(10000, TimeUnit.MILLISECONDS);
    }

}
