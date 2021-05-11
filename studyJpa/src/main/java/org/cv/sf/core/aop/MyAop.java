package org.cv.sf.core.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Spring中事务的默认实现使用的是AOP，也就是代理的方式，如果大家在使用代码测试时，
 * 同一个Service类中的方法相互调用需要使用注入的对象来调用，不要直接使用this.方法名来调用，
 * this.方法名调用是对象内部方法调用，不会通过Spring代理，也就是事务不会起作用
 *
 * 开发中避免不了会对同一个类里面的方法调用，比如有一个类Test，它的一个方法A，A再调用本类的方法B
 * （不论方法B是用public还是private修饰），但方法A没有声明注解事务，而B方法有。则外部调用方法A之后，
 * 方法B的事务是不会起作用的。这也是经常犯错误的一个地方。
 *
 * 那为啥会出现这种情况？其实这还是由于使用Spring AOP代理造成的，因为只有当事务方法被当前类以外的代码调用时，
 * 才会由Spring生成的代理对象来管理。
 *
 * aop代理对象生成：只有aop被当前类以外代码调用才会生成
 */
@Aspect
@Component
public class MyAop {


    /**
     * 1 定义aop的切面，可以是具体的某一个方法，或者是一个注解
     * 2 可以是一个属性或者一个类吗？为什么可以，为什么不可以？
     * 3 定义一个切面有哪些方式：注解，配置文件，注解有几种类型
     */
    //@Pointcut("execution(* *.tracker(..))")
    @Pointcut("@annotation(org.cv.sf.core.annotation.MyAnnotation)")
    public void defineAspect(){

    }

    @Before("defineAspect()")
    public void doBefore(){
        System.out.println("这是一个aop，切点前嵌入的逻辑");
    }

    @After("defineAspect()")
    public void doAfter(){
        System.out.println("这是一个aop，嵌入切点之后的逻辑");
    }
}
