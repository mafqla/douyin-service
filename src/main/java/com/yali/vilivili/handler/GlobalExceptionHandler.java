package com.yali.vilivili.handler;

import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.controller.base.OperationResult;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
            OR.setResult(false).setCode(StringUtils.isEmpty(lsgException.getCode())? OperationResult.CommonCodes.REAULT_FAIL: lsgException.getCode()).setMsg(lsgException.getMessage());
            responseEntity = ResponseEntity.ok(OR);

        } else {
            OR.setResult(false).setCode(OperationResult.CommonCodes.REAULT_FAIL).setMsg("服务器异常");
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
        OR.setCode(OperationResult.CommonCodes.GENERAL_FAIL).setResult(false);
        OR.setMsg(e.getMessage());
        return new ResponseEntity(OR, HttpStatus.OK);
    }
}