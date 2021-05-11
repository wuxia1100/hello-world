import com.alibaba.fastjson.JSON;
import org.cv.sf.framework.redis.RedisService;
import org.cv.sf.dto.entity.MUserEntity;
import org.cv.sf.studyJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = studyJpaApplication.class)
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${spring.redis.listen-pattern}")
    public String pattern;

    /**
     * 1 一直在纠结用convertAndSend如何发送一个带有时间的消息
     * 2 因为网上是这样测试的，这个方法有channel参数，我就认为消息订阅的数据发送必须带有channel
     * 3 这样测试监听确实也可以监听到消息的发布
     * 4 关键是发布/接受  和 键空间通知，还是有区别的啊，虽然都是Redis的推送，但是不一样啊
     * 5 并不知道键空间通知的具体流程，也不知道这种方式叫键空间通知
     * 6 直接网上搜，有很多种方式实现了消息队列，但是别人不是用的这种方式
     * 7 你也生搬硬套，套不上了吧：用别人的方式（工具）来套你的工具
     * 8 两种都是解决问题，但是这里的两种工具不能拼接来使用啊
     */
    @Test
    public void sendMessage(){
        //存入Redis需要通知的业务key：延迟消息队列
        stringRedisTemplate.opsForValue().set("redisExpiresKey","1",10,TimeUnit.SECONDS);
        //及时消息队列：根据channel,redis 的默认机制，订阅了这个主题，再这里发送这个主题的消息
        //监听器配置了这个主题的监听，就可以立即收到推送
        // stringRedisTemplate.convertAndSend(pattern,"及时消息队列");
    }

    @Test
    public void saveStringExpire(){
        ValueOperations operations = stringRedisTemplate.opsForValue();
        operations.set("keyExpire","value 定期时间",60,TimeUnit.SECONDS);
    }

    @Test
    public void getStringExpire(){
        ValueOperations operations = stringRedisTemplate.opsForValue();
        System.out.println("getStringExpire() :"+operations.get("keyExpire"));
    }


    @Test
    public void saveUser(){
        MUserEntity user = new MUserEntity();
        user.setNickname("nickname");
        user.setAvatar("jianyi");
        user.setEmail("20adf");
        redisService.setHash("user","name", JSON.toJSONString(user));
    }

    @Test
    public void getUser(){
        System.out.println(redisService.getHash("user","name"));
    }

    @Test
    public void saveString(){
        redisService.setString("stringKey","stringValue");
    }



    @Test
    public void updateString(){
        redisService.setString("stringKey","stringValueUpdate");
    }

    @Test
    public void getString(){
        System.out.println("getString() : "+redisService.getString("userNew"));
    }

    @Test
    public void setList(){
        for(int i = 0;i < 4;i++){
            System.out.println("setList() 存入第"+i+"个元素:"+redisService.setList("list","list"+i));
        }
    }

    @Test
    public void getList(){
        System.out.println("getList() :"+redisService.getList("list",0,-3));
    }

    @Test
    public void delete(){
        redisService.delete("stringKey");
    }
}
