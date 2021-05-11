package org.cv.sf.core.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAopMethod {


    /**
     * 1 定义aop的切面，可以是具体的某一个方法，或者是一个注解
     * 2 可以是一个属性或者一个类吗？为什么可以，为什么不可以？
     * 3 定义一个切面有哪些方式：注解，配置文件，注解有几种类型
     */
    @Pointcut("execution(* *.saveAspect(..))")
    public void defineAspectMethod(){

    }

    @Before("defineAspectMethod()")
    public void doBefore(){
        System.out.println("这是一个aopMethod，切点前嵌入的逻辑");
    }

    @After("defineAspectMethod()")
    public void doAfter(){
        System.out.println("这是一个aopMethod，嵌入切点之后的逻辑");
    }
}
