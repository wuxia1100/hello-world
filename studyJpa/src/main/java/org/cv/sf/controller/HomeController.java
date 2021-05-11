package org.cv.sf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @RequestMapping(value = "home")
    public String home(){
        return "home";
    }

    @RequestMapping(value = "userList")
    @ResponseBody
    public String userList(){
        return "hello world";
    }

    @RequestMapping(value = "login")
    public String toLogin(){
        return "login";
    }
}
