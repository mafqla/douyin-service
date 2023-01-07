package com.yali.vilivili.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yali.vilivili.controller.base.OperationResult;
import com.yali.vilivili.service.ToolService;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Date 2023/1/8 3:03
 * @Author pq
 */
@Service
public class ToolServiceImpl implements ToolService {

    @Override
    public void failedResponse(HttpServletResponse response, String code, String message) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        ObjectMapper om = new ObjectMapper();
        OperationResult<Void> operationResult = new OperationResult<>(false);
        operationResult.setCode(code);
        operationResult.fail(message);
        try {
            response.getWriter().write(om.writeValueAsString(operationResult));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
