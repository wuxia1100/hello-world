package org.cv.sf.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.cv.sf.core.exception.run.BusinessException;
import org.cv.sf.core.thread.LogSaveThread;
import org.cv.sf.dto.ResponseVo;
import org.cv.sf.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 	异常
 * 	thorwable
 *
 *  异常类型
 * 	error
 * 	Exception
 *
 * 	校验异常
 * 	checkedExecption :必须处理，可以立即处理，也可以继续往外抛，但即使往外抛也是一种处理
 * 	：编译时候出现的异常：可以确定，必须处理
 * 	RuntimeException :可以不处理,不用处理，也不用往外抛，什么处理都不用：
 * 	运行时出现的异常，无法预知，所以不用处理
 *
 * 	没有extends RuntimeException 的异常基本都是 checkedExecption,继承的都是Exception
 * 	HttpException extends Exception : 编译异常 > throw 后必须一直往外抛，抛到最外面处理的地方
 * 	HttpException extends RuntimeException: 运行时异常 > throw 后不需要往外抛
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandel {
    @Autowired
    private LogService logService;


    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVo HandelException(HttpServletRequest req, Exception e){
        saveLog(req,e);
        log.error("未知异常",e);
        return new ResponseVo().exception(e);
    }


    /**
     * 业务异常
     * 1 不加@ResponseBody 注解 会报下面的异常，找不到返回页面的模板
     * org.thymeleaf.exceptions.TemplateInputException:
     * Error resolving template [file/find],template might not exist or
     * might not be accessible by any of the configured Template Resolvers
     * 原因：没有告诉spring做序列化处理，默认的处理方式是返回页面，所以就要初始化页面模板，然后找不到
     * 2 json 序列化异常
     * org.springframework.http.converter.HttpMessageNotWritableException:
     * No converter found for return value of type: class org.cv.sf.dto.ResponseVo
     * org.cv.sf.core.exception.run.BaseBusinessException: null
     * org.springframework.http.converter.HttpMessageNotWritableException:
     * No converter found for return value of type: class org.cv.sf.dto.ResponseVo
     * 原因：ResponseVo 对象的属性 用的是private修饰符，而没有提供get方法，序列化时无法获取属性
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseVo HandelException(HttpServletRequest req, BusinessException e){
        saveLog(req,e);
        return new ResponseVo().exception(e.code,e.getMessage());
    }

    /**
     * 记录日志
     */
    private void saveLog(HttpServletRequest req, Exception e){
        ExecutorService es = Executors.newFixedThreadPool(3);
        Map<String,Object> params = new HashMap<>();
        if(e instanceof BusinessException){
            params.put("message",((BusinessException) e).message);
            params.put("statusCode",((BusinessException) e).code);
        }else{
            params.put("message",e.getMessage());
            params.put("statusCode",-1);
        }
        params.put("method",req.getMethod());
        params.put("path",req.getRequestURL());
        params.put("permission",1);
        LogSaveThread logSaveThread = new LogSaveThread(logService,params);
        es.submit(logSaveThread);
    }
}
