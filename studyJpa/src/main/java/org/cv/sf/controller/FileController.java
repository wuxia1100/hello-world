package org.cv.sf.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.cv.sf.core.annotation.ScopeLevel;
import org.cv.sf.core.exception.check.HandelException;
import org.cv.sf.dto.ResponseVo;
import org.cv.sf.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "file")
public class FileController {
    @Autowired
    private FileService fileService;


    @RequestMapping(value = "find")
    @ScopeLevel(scopeLevel = 10)
    @RequiresRoles(value="root")
    public ResponseVo find(@RequestParam int id) throws HandelException {
        return new ResponseVo().success(fileService.findById(id));
    }
}
