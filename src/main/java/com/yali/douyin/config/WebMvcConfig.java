package com.yali.vilivili.config;

import com.yali.vilivili.interceptor.UserInterceptor;
import com.yali.vilivili.interceptor.UtilityInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description 拦截器配置，将interceptor包中的拦截器类配置进来，使其生效
 * @Date 2023/1/8 2:06
 * @Author pq
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    UserInterceptor userInterceptor;

    @Autowired
    UtilityInterceptor utilityInterceptor;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${file.upload.path2}")
    private String fileUploadPath2;


    @Value("${file.upload.video.path}")
    private String videoUploadPath;

    @Value("${file.upload.video.cover-path}")
    private String videoCoverPath;

    @Value("${file.upload.icon.path}")
    private String iconUploadPath;

    @Value("${file.upload.context-path}")
    private String contextPath;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns("/**").order(1);
        registry.addInterceptor(utilityInterceptor).addPathPatterns("/util/**").order(2);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(contextPath + "/**")
                .addResourceLocations("file:" + fileUploadPath)
                .addResourceLocations("file:" + fileUploadPath2)
                .addResourceLocations("file:" + videoUploadPath)
                .addResourceLocations("file:" + videoCoverPath)
                .addResourceLocations("file:" + iconUploadPath);
    }
}
