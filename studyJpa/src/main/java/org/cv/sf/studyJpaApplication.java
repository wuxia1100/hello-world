package org.cv.sf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication

//2 如果不开启这个扫描，IOC容器是无法注册这个对象的也就无法使用
//3 至于它是如何把一个接口，创建一个实现对象的那就是框架的底层具体实现了
@MapperScan("org.cv.sf.repository.mybatis,org.cv.sf.repository.mybatis_plus")

//SpringBootApplication 上使用@ServletComponentScan 注解后
//Servlet可以直接通过@WebServlet注解自动注册
//Filter可以直接通过@WebFilter注解自动注册
//Listener可以直接通过@WebListener 注解自动注册
//basePackageClasses属性会去扫描类所在包下的所有组件，而不是指定某个组件！
//@ServletComponentScan(basePackageClasses = LoginServlet.class)
public class studyJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(studyJpaApplication.class,args);
    }
}
