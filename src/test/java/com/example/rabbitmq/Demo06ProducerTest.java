package com.example.rabbitmq;

import com.example.rabbitmq.producer.Demo06Producer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Demo06ProducerTest extends RabbitmqTestApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo06Producer producer;

    @Test
    public void testSyncSend01() throws InterruptedException {
        this.testSyncSend(3);  // 超时情况下的批量消费
        // 阻塞等待，保证消费
        new CountDownLatch(1).await(40000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testSyncSend02() throws InterruptedException {
        this.testSyncSend(10);  // 未超时情况下的批量消费
        // 阻塞等待，保证消费
        new CountDownLatch(1).await(40000, TimeUnit.MILLISECONDS);
    }

    public void testSyncSend(int n) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
        }
    }

}
