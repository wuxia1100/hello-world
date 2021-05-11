package org.cv.sf.framework.config;

import org.cv.sf.framework.redis.mq.RedisMqListen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * Redis 键空间通知
 * 	1 redis.conf 配置文件开启键空间通知
 * 	2 配置内容：notify-keyspace-events Ex
 * 	3 启用配置：Redis启动没有指定配置文件使用的是默认的配置文件 1 指定配置文件启动 2 修改默认的配置文件配置
 * 	4 cmd: setex name 10 7yue 插入一个key，
 * 	代码：stringRedisTemplate.opsForValue().set("redisExpiresKey","1",10,TimeUnit.SECONDS);
 */
@Configuration
public class RedisMessageListenConfig {
    @Value("${spring.redis.listen-pattern}")
    public String pattern;

    /**
     * 1 @Bean 注解忘了加
     * 导致监听一直没有用，搞了好久
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory){
        //创建容器
        RedisMessageListenerContainer redisContainer =  new RedisMessageListenerContainer();
        //设置工厂
        redisContainer.setConnectionFactory(factory);
        //创建主题：想要被监听的主题,数据存入的管道
        Topic topic = new PatternTopic(this.pattern);
        //添加监听器
        redisContainer.addMessageListener(new RedisMqListen(),new PatternTopic(pattern));

        return redisContainer;
    }

    /**
     * 另外一种监听创建的方式
     */
    /*@Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        // 注入 Receiver，指定类中的接受方法
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }*/
}
