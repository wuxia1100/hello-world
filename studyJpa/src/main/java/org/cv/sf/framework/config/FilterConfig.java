package org.cv.sf.framework.config;

import org.cv.sf.framework.fiters.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        //创建filter注册器对象
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //创建过滤器对象，注入到注册器中
        filterRegistrationBean.setFilter(new MyFilter());
        //配置要应用的路径，如果有多个路径，写成String[]的形式
        //"/user/*" 这样拦截是生效的
        //"/user/**" 多一个*，拦截就失效，这种意识哪里来？
        filterRegistrationBean.addUrlPatterns("/user/*");
        //注册filter对象的顺序,浏览器->服务器时，数值越小的越先执行；服务器->浏览器则相反
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }


}
