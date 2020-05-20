package com.example.rabbitmq;

import com.example.rabbitmq.producer.ClusteringProducer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class ClusteringProducerTest extends RabbitmqTestApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClusteringProducer producer;

    @Test
    public void mock() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await(20000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testSyncSend() throws InterruptedException {
        // 发送 3 条消息
        for (int i = 0; i < 3; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await(2000, TimeUnit.MILLISECONDS);
    }

}
