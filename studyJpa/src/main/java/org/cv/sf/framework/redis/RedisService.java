package org.cv.sf.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key,String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public String getString(String key){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setHash(String sky,String fieldKey,String value){
        HashOperations<String,Object,Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(sky,fieldKey,value);
    }

    public String getHash(String key,String fieldKey){
        return (String) stringRedisTemplate.opsForHash().get(key,fieldKey);
    }

    public Long setList(String key,String value){
        ListOperations<String,String> listOperations = stringRedisTemplate.opsForList();
        return listOperations.leftPush(key,value);
    }

    public List<String> getList(String key,int start,int end){
        ListOperations<String,String> listOperations = stringRedisTemplate.opsForList();
        return listOperations.range(key,start,end);
    }

    public void delete(String key){
        stringRedisTemplate.delete(key);
    }
}
