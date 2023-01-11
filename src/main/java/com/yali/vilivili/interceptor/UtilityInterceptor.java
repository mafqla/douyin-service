package com.yali.vilivili.interceptor;

import com.yali.vilivili.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 收藏拦截器，需要去WebMvcConfig配置才能生效
 * @Date 2023/1/8 2:29
 * @Author pq
 */
@Component
@Slf4j
public class UtilityInterceptor implements AsyncHandlerInterceptor {

    @Resource
    private ToolService toolService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
