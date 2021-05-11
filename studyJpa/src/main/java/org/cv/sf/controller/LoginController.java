package org.cv.sf.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cv.sf.core.exception.run.NotFoundException;
import org.cv.sf.framework.util.JwtToken;
import org.cv.sf.dto.ResponseVo;
import org.cv.sf.dto.entity.MUserEntity;
import org.cv.sf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个controller 不能加@RequiresPermissions 相关的权限校验注解
 * 否则找不到请求路径，不知道为什么
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/doLogin")
    public ResponseVo doLogin(String username, String password){
        Subject subject = SecurityUtils.getSubject();
        try {
            MUserEntity user = userService.findByUsername(username);
            if(user == null){
                throw new NotFoundException(10004);
            }
            //创建自定义的authenticationToken
            //new MyAuthenticationToken(user.getId(),username);
            subject.login(new UsernamePasswordToken(username,password));
            return new ResponseVo().success(JwtToken.INSTANCE.getToken(user.getId()));
        }catch (AuthenticationException e){
            e.printStackTrace();
            return new ResponseVo().exception("登录失败");
        }

    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/login")
    //@RequiresPermissions(value = "asf,adf")
    public String login(){
        return "login";
    }
}
