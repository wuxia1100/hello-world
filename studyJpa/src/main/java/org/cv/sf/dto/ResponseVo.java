package org.cv.sf.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口统一响应格式
 */
public class ResponseVo<T> implements Serializable {
    private static final long serialVersionUID = 2916462829605584320L;

    public Map<String,Object> resultStatus;
    public T resultValue;

    private void setResponseVo(T resultValue,Map<String,Object> resultStatus){
        this.resultStatus = resultStatus;
        this.resultValue = resultValue;
    }

    public  ResponseVo<T> success(T resultValue){
        Map<String, Object> resultStatus = new HashMap<>();
        resultStatus.put("code",200);
        resultStatus.put("message","操作成功");
        setResponseVo(resultValue,resultStatus);
        return this;
    }

    public ResponseVo exception(T resultValue){
        Map<String, Object> resultStatus = new HashMap<>();
        resultStatus.put("code",-1);
        resultStatus.put("message","系统异常,请稍后再试");
        setResponseVo(resultValue,resultStatus);
        return this;
    }

    public ResponseVo exception(){
        Map<String, Object> resultStatus = new HashMap<>();
        resultStatus.put("code",-1);
        resultStatus.put("message","系统异常,请稍后再试");
        setResponseVo(resultValue,resultStatus);
        return this;
    }

    public ResponseVo exception(int code,String message){
        Map<String, Object> resultStatus = new HashMap<>();
        resultStatus.put("code",code);
        resultStatus.put("message",message);
        setResponseVo(resultValue,resultStatus);
        return this;
    }
}
