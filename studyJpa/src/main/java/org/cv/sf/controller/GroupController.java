package org.cv.sf.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.cv.sf.dto.ResponseVo;
import org.cv.sf.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "find")
    @RequiresRoles(value = "guest")
    public ResponseVo find(@RequestParam int id ){
        return new ResponseVo().success(groupService.findById(Long.valueOf(id)));
    }
}
