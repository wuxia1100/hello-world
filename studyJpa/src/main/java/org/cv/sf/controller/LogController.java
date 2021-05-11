package org.cv.sf.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.cv.sf.core.annotation.ScopeLevel;
import org.cv.sf.dto.bo.LocalUser;
import org.cv.sf.dto.ResponseVo;
import org.cv.sf.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping(value = "find")
    @ScopeLevel(scopeLevel = 8)
    @RequiresRoles("root:guest")
    public ResponseVo find(@RequestParam int id){
        System.out.println("当前操作用户 ："+LocalUser.getUser().getUsername());
        return new ResponseVo().success(logService.findById(Long.valueOf(id)));
    }
}
