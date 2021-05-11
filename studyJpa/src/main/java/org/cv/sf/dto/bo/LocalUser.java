package org.cv.sf.dto.bo;

import org.cv.sf.dto.entity.MUserEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地用户
 */
public class LocalUser {
    //当前线程变量：存放的数据只有当前线程可以使用，线程安全
    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    public static void set(MUserEntity user){
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        LocalUser.threadLocal.set(map);
    }

    public static MUserEntity getUser(){
        return (MUserEntity) LocalUser.threadLocal.get().get("user");
    }

    /**
     * 清除当前线程保存数据
     */
    public static void clear(){
        LocalUser.threadLocal.remove();
    }
}
