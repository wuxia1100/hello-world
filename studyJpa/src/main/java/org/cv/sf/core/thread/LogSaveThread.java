package org.cv.sf.core.thread;

import org.apache.shiro.SecurityUtils;
import org.cv.sf.framework.shiro.MyAuthenticationToken;
import org.cv.sf.service.LogService;

import java.util.Map;
import java.util.concurrent.Callable;

public class LogSaveThread implements Callable<String> {

    private LogService logService;
    private Map<String,Object> params;

    public LogSaveThread(LogService logService,Map<String,Object> params){
        this.logService = logService;
        this.params = params;
    }

    @Override
    public String call() throws Exception {
        MyAuthenticationToken myAuthenticationToken = (MyAuthenticationToken)SecurityUtils.getSubject().getPrincipal();
        if(myAuthenticationToken != null){
            params.put("username",myAuthenticationToken.getUsername());
            params.put("userId",myAuthenticationToken.getUserId());
        }else{

            params.put("username","游客");
            params.put("userId",0);
        }
        logService.save(params);
        return "保存成功";
    }
}
