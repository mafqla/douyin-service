package com.yali.vilivili.config;

import com.yali.vilivili.interceptor.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description 拦截器配置，将interceptor包中的拦截器类配置进来，使其生效
 * @Date 2023/1/8 2:06
 * @Author pq
 */
@Slf4j
@Configuration

public class WebMvcConfig implements WebMvcConfigurer{
        @Resource
        UserInterceptor userInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(userInterceptor).addPathPatterns("/**");
        }
}
