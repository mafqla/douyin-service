package com.yali.vilivili.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Date 2023/1/8 3:03
 * @Author pq
 */
public interface ToolService {
    void failedResponse(HttpServletResponse response, String code, String message);
}
