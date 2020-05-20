package com.example.rabbitmq;

import com.example.rabbitmq.producer.Demo12Producer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Demo12ProducerTest extends RabbitmqTestApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo12Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        for (int id = 1; id <= 2; id++) {
            producer.syncSend(id);
            logger.info("[testSyncSend][发送编号: [{}] 发送成功]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await(10000, TimeUnit.MILLISECONDS);
    }

}
