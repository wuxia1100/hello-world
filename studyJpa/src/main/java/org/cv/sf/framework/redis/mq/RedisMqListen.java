package org.cv.sf.framework.redis.mq;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * redis 消息监听器
 */
@Component
public class RedisMqListen implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        //获取值
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        //转换成String
        String key = new String(body);
        String topic = new String(channel);

        //根据key做业务处理
        //todo
        System.out.println("redis监听到的数据：key:"+key+",topic:"+topic);
    }
}
