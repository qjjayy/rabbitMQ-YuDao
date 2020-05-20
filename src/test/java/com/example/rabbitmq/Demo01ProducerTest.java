package com.example.rabbitmq;

import com.example.rabbitmq.producer.Demo01Producer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Demo01ProducerTest extends RabbitmqTestApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo01Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);
        logger.info("[testSyncSend][发送编号: [{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testSyncSendDefault() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendDefault(id);
        logger.info("[testSyncSendDefault][发送编号: [{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, throwable);
            }

            @Override
            public void onSuccess(Void aVoid) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，发送成功]", id);
            }
        });
        logger.info("[testASyncSend][发送编号：[{}] 调用完成]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await(2000, TimeUnit.MILLISECONDS);
    }
}
