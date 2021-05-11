package org.cv.sf.framework.interceptors;


import com.alibaba.fastjson.JSONObject;
import org.cv.sf.core.exception.run.RepeatSubmitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 请求前拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkMethod(request);
    }

    /**
     * 校验请求路径是否存在缓存中
     */
    private boolean checkMethod(HttpServletRequest request){
        //获取Redis操作对象
        ValueOperations operations = stringRedisTemplate.opsForValue();

        //生成请求唯一标识key
        String repeatCheckKey = buildRepeatCheckKey(request);

        //如果缓存中存在，直接返回false，拒绝本次访问
        if(operations.get(repeatCheckKey) != null){
            //System.out.println("重复请求 :"+repeatCheckKey);
            throw new RepeatSubmitException(1001);
        }
        //缓存中不存在，存入缓存
        //当前方法10秒内只能访问一次：存入信息的业务含义
        operations.set(repeatCheckKey,"1",10, TimeUnit.SECONDS);
        return true;
    }


    /**
     * 1 生成key,每个用户，同一个请求，生成一个key
     * 2 ip地址+请求URL,能否保住唯一性？
     * 3 请求参数不同，即使短时间内请求多次，同样认为是有效请求，
     */
    public String buildRepeatCheckKey(HttpServletRequest request){
        return request.getRemoteHost()
                +request.getRequestURI()
                +JSONObject.toJSONString(request.getParameterMap());
    }

   /* private String buildRepeatCheckKey(HttpServletRequest request)
            throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        return MD5.INSTANCE.generateDigest(
                request.getRequestURI() + request.getMethod() + JSONObject.valueToString(request.getParameterMap())+ request
                        .getRemoteHost() + request.getHeader("token"), MD5_KEY_REPEAT_SUBMIT);
    }*/
}
