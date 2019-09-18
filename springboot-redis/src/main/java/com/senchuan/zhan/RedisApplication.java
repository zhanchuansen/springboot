package com.senchuan.zhan;

import com.senchuan.zhan.message.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class RedisApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisApplication.class);

    /**
     * 注入消息监听容器
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /**
     * 注入消息接收者
     *
     * @param latch
     * @return
     */
    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }


    /**
     * 在spring data redis中，利用redis发送一条消息和接受一条消息，需要三样东西：
     * 一个连接工厂
     * 一个消息监听容器
     * Redis template
     * <p>
     * 测试
     *
     * @param args
     * @throws Exception 消息从接受者Receiver那里传过来 打印到控制台
     */
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(RedisApplication.class, args);

        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        CountDownLatch latch = ctx.getBean(CountDownLatch.class);

        LOGGER.info("Sending message...");
        template.convertAndSend("chat", "Hello from Redis!");

        latch.await();

        System.exit(0);
    }
}
