package com.yali.vilivili.interceptor;

import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.controller.base.OperationResult;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 全局异常拦截器,不需要配置就能生效
 * @Date 2023/1/8 1:12
 * @Author pq
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<OR<Void>> jsonErrorHandler(Exception e) {

        ResponseEntity<OR<Void>> responseEntity;
        OR<Void> OR = new OR<>();
        if (e.getClass().equals(MyException.class)) {
            MyException lsgException = (MyException) e;
            log.debug("lsg exception,code [{}], message [{}]", lsgException.getCode(), lsgException.getMessage());
            OR.setCode(StringUtils.isEmpty(lsgException.getCode())?
                    OperationResult.CommonCodes.REAULT_FAIL: lsgException.getCode()).setMsg(lsgException.getMessage()
            );
            responseEntity = ResponseEntity.ok(OR);

            // 拦截validation异常
        } else if (e.getClass().equals(org.springframework.validation.BindException.class)) {
            org.springframework.validation.BindException bindException = (org.springframework.validation.BindException) e;
            // 获取validation异常信息
            List<FieldError> fieldErrors = bindException.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                log.error("field: {}, error: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            StringBuilder messageBuilder = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                messageBuilder.append(fieldError.getField())
                        .append(":")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            }

            OR.setCode(OperationResult.CommonCodes.INPUT_NOT_VALID).setMsg(messageBuilder.toString());


            responseEntity = ResponseEntity.ok(OR);

        } else {
            OR.setCode(OperationResult.CommonCodes.REAULT_FAIL).setMsg("服务器异常");
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(OR);
        }
        log.error(this.getClass().getSimpleName(), e);
        return responseEntity;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<OR<Void>> IllegalArgumentExceptionHandler(Exception e) {

        log.error(this.getClass().getSimpleName(), e);
        OR<Void> OR = new OR<>();
        OR.setCode(OperationResult.CommonCodes.GENERAL_FAIL);
        OR.setMsg(e.getMessage());
        return new ResponseEntity(OR, HttpStatus.OK);
    }
}