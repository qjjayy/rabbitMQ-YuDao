package com.example.rabbitmq.config;

import com.example.rabbitmq.message.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 配置类  RabbitAdmin 会根据配置自动创建队列、交换器、绑定器
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建Queue
        @Bean
        public Queue demo01Queue() {
            return new Queue(Demo01Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo01Exchange() {
            return new DirectExchange(Demo01Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo01Binding() {
            return BindingBuilder.bind(demo01Queue()).to(demo01Exchange()).with(Demo01Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo05Queue() {
            return new Queue(Demo05Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo05Exchange() {
            return new DirectExchange(Demo05Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo05Binding() {
            return BindingBuilder.bind(demo05Queue()).to(demo05Exchange()).with(Demo05Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo06Queue() {
            return new Queue(Demo06Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo06Exchange() {
            return new DirectExchange(Demo06Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo06Binding() {
            return BindingBuilder.bind(demo06Queue()).to(demo06Exchange()).with(Demo06Message.ROUTING_KEY);
        }

        // 创建Queue(对应的DeadQueue重用了Exchange)
        @Bean
        public Queue demo07Queue() {
            return QueueBuilder.durable(Demo07Message.QUEUE).deadLetterExchange(Demo07Message.EXCHANGE).deadLetterRoutingKey(Demo07Message.DEAD_ROUTING_KEY).build();
        }

        // 创建DeadQueue
        // 在 Spring-AMQP 的消费重试机制中，在消费失败到达最大次数后，会自动抛出 AmqpRejectAndDontRequeueException 异常，从而结束该消息的消费重试。
        // 这意味着什么呢？如果我们在消费消息的逻辑中，主动抛出 AmqpRejectAndDontRequeueException 异常，也能结束该消息的消费重试。
        // 结束的方式，Spring-AMQP 是通过我们在上文中提到的 basic.nack + requeue=false ，从而实现转发该消息到死信队列中。
        @Bean
        public Queue demo07DeadQueue() {
            return new Queue(Demo07Message.DEAD_QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        // 默认情况下，spring.rabbitmq.simple.retry.enable=false ，关闭 Spring-AMQP 的消费重试功能。
        // 但是实际上，消费发生异常的消息，还是会一直重新消费。这是为什么呢？
        // Spring-AMQP 会将该消息通过 basic.nack + requeue=true ，重新投递回原队列的尾巴。
        // 如此，我们便会不断拉取到该消息，不断“重试”消费该消息。
        // 当然在这种情况下，我们一样可以主动抛出 AmqpRejectAndDontRequeueException 异常，也能结束该消息的消费重试。
        // 结束的方式，Spring-AMQP 也是通过我们在上文中提到的 basic.nack + requeue=false ，从而实现转发该消息到死信队列中。
        @Bean
        public DirectExchange demo07Exchange() {
            return new DirectExchange(Demo07Message.EXCHANGE, true, false);
        }

        // 创建Binding
        // Spring-AMQP 提供的消费重试的计数是客户端级别的，重启 JVM 应用后，计数是会丢失的。
        // 所以，如果想要计数进行持久化，需要自己重新实现下。
        // RocketMQ 提供的消费重试的计数，目前是服务端级别，已经进行持久化。
        @Bean
        public Binding demo07Binding() {
            return BindingBuilder.bind(demo07Queue()).to(demo07Exchange()).with(Demo07Message.ROUTING_KEY);
        }

        // 创建DeadBinding
        @Bean
        public Binding demo07DeadBinding() {
            return BindingBuilder.bind(demo07DeadQueue()).to(demo07Exchange()).with(Demo07Message.DEAD_ROUTING_KEY);
        }

        // 创建Queue (默认消息过期时间为5秒)
        @Bean
        public Queue demo08Queue() {
            return QueueBuilder.durable(Demo08Message.QUEUE).ttl(5000).deadLetterExchange(Demo08Message.EXCHANGE).deadLetterRoutingKey(Demo08Message.DEAD_ROUTING_KEY).build();
        }

        // 创建DeadQueue
        @Bean
        public Queue demo08DeadQueue() {
            return new Queue(Demo08Message.DEAD_QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo08Exchange() {
            return new DirectExchange(Demo08Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo08Binding() {
            return BindingBuilder.bind(demo08Queue()).to(demo08Exchange()).with(Demo08Message.ROUTING_KEY);
        }

        // 创建DeadBinding
        @Bean
        public Binding demo08DeadBinding() {
            return BindingBuilder.bind(demo08DeadQueue()).to(demo08Exchange()).with(Demo08Message.DEAD_ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo09Queue() {
            return new Queue(Demo09Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo09Exchange() {
            return new DirectExchange(Demo09Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo09Binding() {
            return BindingBuilder.bind(demo09Queue()).to(demo09Exchange()).with(Demo09Message.ROUTING_KEY);
        }

        // 创建 Queue
        @Bean
        public Queue demo10Queue0() {
            return new Queue(Demo10Message.QUEUE_0);
        }
        @Bean
        public Queue demo10Queue1() {
            return new Queue(Demo10Message.QUEUE_1);
        }
        @Bean
        public Queue demo10Queue2() {
            return new Queue(Demo10Message.QUEUE_2);
        }
        @Bean
        public Queue demo10Queue3() {
            return new Queue(Demo10Message.QUEUE_3);
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo10Exchange() {
            return new DirectExchange(Demo10Message.EXCHANGE, true, false);
        }

        // 创建 Binding
        // 解决顺序消息中的消息堆积问题
        // 在 Producer 端，将 Queue 拆成多个子 Queue 。假设原先 Queue 是 QUEUE_USER ，
        // 那么我们就分拆成 QUEUE_USER_00 至 QUEUE_USER_..${N-1} 这样 N 个队列，
        // 然后基于消息的用户编号取余，路由到对应的子 Queue 中。
        @Bean
        public Binding demo10Binding0() {
            return BindingBuilder.bind(demo10Queue0()).to(demo10Exchange()).with("0");
        }
        @Bean
        public Binding demo10Binding1() {
            return BindingBuilder.bind(demo10Queue1()).to(demo10Exchange()).with("1");
        }
        @Bean
        public Binding demo10Binding2() {
            return BindingBuilder.bind(demo10Queue2()).to(demo10Exchange()).with("2");
        }
        @Bean
        public Binding demo10Binding3() {
            return BindingBuilder.bind(demo10Queue3()).to(demo10Exchange()).with("3");
        }

        // 创建Queue
        @Bean
        public Queue demo11Queue() {
            return new Queue(Demo11Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo11Exchange() {
            return new DirectExchange(Demo11Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo11Binding() {
            return BindingBuilder.bind(demo11Queue()).to(demo11Exchange()).with(Demo11Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo12Queue() {
            return new Queue(Demo12Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo12Exchange() {
            return new DirectExchange(Demo12Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo12Binding() {
            return BindingBuilder.bind(demo12Queue()).to(demo12Exchange()).with(Demo12Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo13Queue() {
            return new Queue(Demo13Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo13Exchange() {
            return new DirectExchange(Demo13Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo13Binding() {
            return BindingBuilder.bind(demo13Queue()).to(demo13Exchange()).with(Demo13Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo14Queue() {
            return new Queue(Demo14Message.QUEUE, false, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo14Exchange() {
            return new DirectExchange(Demo14Message.EXCHANGE, false, false);
        }

        // 创建Binding
        @Bean
        public Binding demo14Binding() {
            return BindingBuilder.bind(demo14Queue()).to(demo14Exchange()).with(Demo14Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo15Queue() {
            return new Queue(Demo15Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo15Exchange() {
            return new DirectExchange(Demo15Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo15Binding() {
            return BindingBuilder.bind(demo15Queue()).to(demo15Exchange()).with(Demo15Message.ROUTING_KEY);
        }

        // 创建Queue
        @Bean
        public Queue demo16Queue() {
            return new Queue(Demo16Message.QUEUE, true, false, false);
        }

        // 创建Direct Exchange
        @Bean
        public DirectExchange demo16Exchange() {
            return new DirectExchange(Demo16Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo16Binding() {
            return BindingBuilder.bind(demo16Queue()).to(demo16Exchange()).with(Demo16Message.ROUTING_KEY);
        }
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * BatchingRabbitTemplate 提供的批量发送消息的能力比较弱。
     * 对于同一个 BatchingRabbitTemplate 对象来说，同一时刻只能有一个批次(保证 Exchange + RoutingKey 相同)，否则会报错。
     * 实际上，RabbitMQ Broker 存储的是一条消息。又或者说，RabbitMQ 并没有提供批量接收消息的 API 接口。
     * 批量发送消息是 Spring-AMQP 的 SimpleBatchingStrategy 所封装提供：
     * 在 Producer 最终批量发送消息时，SimpleBatchingStrategy 会通过 #assembleMessage() 方法，将批量发送的多条消息组装成一条“批量”消息，然后进行发送。
     * 在 Consumer 拉取到消息时，会根据#canDebatch(MessageProperties properties) 方法，判断该消息是否为一条“批量”消息？
     * 如果是，则调用# deBatch(Message message, Consumer<Message> fragmentConsumer) 方法，将一条“批量”消息拆开，变成多条消息。
     */
    @Bean(name = "batchingRabbitTemplate")
    public BatchingRabbitTemplate batchingRabbitTemplate(ConnectionFactory connectionFactory) {
        // 创建 BatchingStrategy 对象，代表批量策略
        int batchSize = 16384; // 超过收集的消协数量的最大数
        int bufferLimit = 33554432; // 每次批量发送消息的最大内存
        int timeout = 30000; // 超过收集的时间的最大等待时长，单位：毫秒
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);

        // 创建 TaskScheduler 对象，用于实现超时发送的定时器
        TaskScheduler taskScheduler = new ConcurrentTaskScheduler();

        // 创建 BatchingRabbitTemplate 对象
        BatchingRabbitTemplate batchingRabbitTemplate = new BatchingRabbitTemplate(batchingStrategy, taskScheduler);
        batchingRabbitTemplate.setConnectionFactory(connectionFactory);
        return batchingRabbitTemplate;
    }

//    @Bean
//    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
//        rabbitTemplate(connectionFactory).setChannelTransacted(true);
//        return new RabbitTransactionManager(connectionFactory);
//    }

    /**
     * 创建支持批量消费的 SimpleRabbitListenerContainer
     */
    @Bean(name = "consumerBatchContainerFactory")
    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory (
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        // 创建 SimpleRabbitListenerContainerFactory 对象
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 额外添加批量消费的属性
        factory.setBatchListener(true);
        return factory;
    }

    /**
     * 创建支持批量消费的 SimpleRabbitListenerContainer
     * 其实现方式是，阻塞等待最多 receiveTimeout 秒，拉取 batchSize 条消息，进行批量消费。
     *
     * 如果在 receiveTimeout 秒内已经成功拉取到 batchSize 条消息，则直接进行批量消费消息。
     * 如果在 receiveTimeout 秒还没拉取到 batchSize 条消息，不再等待，而是进行批量消费消息。
     *
     * 不过 Spring-AMQP 的阻塞等待时长 receiveTimeout 的设计有点“神奇”。
     *
     * 它代表的是，每次拉取一条消息，最多阻塞等待 receiveTimeout 时长。
     * 如果等待不到下一条消息，则进入已获取到的消息的批量消费。
     * 也就是说，极端情况下，可能等待 receiveTimeout * batchSize 时长，才会进行批量消费。
     */
    @Bean(name = "consumerBatchContainerFactory2")
    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory2 (
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        // 创建 SimpleRabbitListenerContainerFactory 对象
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 额外添加批量消费的属性
        factory.setBatchListener(true);
        factory.setBatchSize(10);
        factory.setReceiveTimeout(30000L);
        factory.setConsumerBatchEnabled(true);
        return factory;
    }

    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * Topic Exchange 配置类  RabbitAdmin 会根据配置自动创建队列、交换器、绑定器
     */
    public static class TopicExchangeDemoConfiguration {

        // 创建Queue
        @Bean
        public Queue demo02Queue() {
            return new Queue(Demo02Message.QUEUE, true, false, false);
        }

        // 创建Topic Exchange
        @Bean
        public TopicExchange demo02Exchange() {
            return new TopicExchange(Demo02Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo02Binding() {
            return BindingBuilder.bind(demo02Queue()).to(demo02Exchange()).with(Demo02Message.ROUTING_KEY);
        }
    }

    /**
     * Fanout Exchange 配置类  RabbitAdmin 会根据配置自动创建队列、交换器、绑定器
     */
    public static class FanoutExchangeDemoConfiguration {

        // 创建Queue
        @Bean
        public Queue demo03QueueA() {
            return new Queue(Demo03Message.QUEUE_A, true, false, false);
        }

        @Bean
        public Queue demo03QueueB() {
            return new Queue(Demo03Message.QUEUE_B, true, false, false);
        }

        // 创建Topic Exchange
        @Bean
        public FanoutExchange demo03Exchange() {
            return new FanoutExchange(Demo03Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo03BindingA() {
            return BindingBuilder.bind(demo03QueueA()).to(demo03Exchange());
        }

        @Bean
        public Binding demo03BindingB() {
            return BindingBuilder.bind(demo03QueueB()).to(demo03Exchange());
        }
    }

    /**
     * Headers Exchange 配置类  RabbitAdmin 会根据配置自动创建队列、交换器、绑定器
     */
    public static class HeadersExchangeDemoConfiguration {

        // 创建Queue
        @Bean
        public Queue demo04Queue() {
            return new Queue(Demo04Message.QUEUE, true, false, false);
        }

        // 创建Topic Exchange
        @Bean
        public HeadersExchange demo04Exchange() {
            return new HeadersExchange(Demo04Message.EXCHANGE, true, false);
        }

        // 创建Binding
        @Bean
        public Binding demo04Binding() {
            return BindingBuilder.bind(demo04Queue()).to(demo04Exchange()).where(Demo04Message.HEADER_KEY).matches(Demo04Message.HEADER_VALUE);
        }
    }

    /**
     * 集群消费 配置类
     */
    public static class ClusteringConfiguration {

        // 创建 Topic Exchange (Spring Cloud Stream RabbitMQ 默认使用Topic Exchange)
        @Bean
        public TopicExchange clusteringExchange() {
            return new TopicExchange(ClusteringMessage.EXCHANGE, true,  false);
        }
    }

    /**
     * 广播消费 配置类
     */
    public static class BroadcastingConfiguration {

        // 创建 Topic Exchange (Spring Cloud Stream RabbitMQ 默认使用Topic Exchange)
        @Bean
        public TopicExchange broadcastingExchange() {
            return new TopicExchange(BroadcastMessage.EXCHANGE, true,  false);
        }
    }
}
