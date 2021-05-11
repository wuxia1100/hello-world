package org.cv.sf.framework.config;

import org.cv.sf.framework.interceptors.RequestRuleInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestRuleConfig implements WebMvcConfigurer {

    @Bean
    public RequestRuleInterceptor requestRuleInterceptor(){
        return new RequestRuleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestRuleInterceptor())
                .addPathPatterns("/log/**")
        ;

    }
}
