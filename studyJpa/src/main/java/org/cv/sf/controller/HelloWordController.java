package org.cv.sf.controller;

import org.cv.sf.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class HelloWordController {

    @Autowired
    private HelloWorldService helloWorld;

    @RequestMapping(value = "/world")
    public String helloWorld(){
        return "hello world: "+helloWorld.helloWorld();
    }
}
