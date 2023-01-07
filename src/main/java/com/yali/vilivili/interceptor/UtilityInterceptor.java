package com.yali.vilivili.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yali.vilivili.controller.base.OperationResult;
import com.yali.vilivili.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
