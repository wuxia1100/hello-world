package org.cv.sf.framework.config;

import org.cv.sf.framework.interceptors.RepeatSubmitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RepeatCheckConfig implements WebMvcConfigurer {

    @Bean
    public RepeatSubmitInterceptor getRepeatSubmitInterceptor(){
        return new RepeatSubmitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getRepeatSubmitInterceptor())
                .addPathPatterns("/**")
                .addPathPatterns("/user/**")
                .addPathPatterns("/file/**")
        ;
    }
}
