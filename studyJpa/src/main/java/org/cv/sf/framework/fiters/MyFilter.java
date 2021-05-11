package org.cv.sf.framework.fiters;

import javax.servlet.*;
import java.io.IOException;

/**
 * 1 配置过滤器
 * a
 * @Component   //放到spring容器中
 * @WebFilter("/*")   //指定要应用的路径
 * b
 * @WebFilter("/*")   //指定要应用的路径
 * 入口类(引导类)application上
 * @ServletComponentScan("com.kjyfx.filter")   //此注解会扫描servlet相关的注解。
 * 参数是basePackages，指定filter所在的包
 *
 * @SpringBootApplication本身包含了@Configuration，@Bean部分也可以直接写在引导类中。
 * 一个@Bean配置一个Filter，如果要配置多个Filter，需要写多个@Bean。
 * c
 * 单独写一个filter的配置类，Filter上无需加注解
 */
public class MyFilter implements Filter {
    /**
     * 1 需要过滤的路径，会被拦截
     * 2 拦截是为了处理，处理之后如何放行，往下走？返回值是空啊
     * 3 直接return，就表现过滤放行?不是的，都没有返回值
     * 4 chain.doFilter(request,response);表示放行，告诉拦截器放行了
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("我的过滤器 requestUrl:"+request.getParameterNames());
        //让程序继续往下执行(让请求通过过滤器)/放行
        //请求前逻辑：处理request请求内容
        System.out.println("我的过滤器 处理request");
        chain.doFilter(request,response);
        //请求后逻辑：处理response返回内容
        System.out.println("我的过滤器 处理response");
    }
}
