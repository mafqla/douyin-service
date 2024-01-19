package com.yali.vilivili.utils;

import com.yali.vilivili.service.CodeMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Date 2022/12/22 11:59
 * @Author pq
 */
@Slf4j
public class MyException extends RuntimeException{
    private Integer statusCode;

    private String code;

    private String message;

    public MyException(Integer statusCode, String code, String msg) {
        super(msg);
        this.statusCode = statusCode;
        this.code = code;
        this.message = msg;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public MyException(Integer statusCode, String code, CodeMessageService codeMessageService) {
        super(codeMessageService.message(code));
        this.statusCode = statusCode;
        this.code = code;
        this.message = codeMessageService.message(code);
        log.error("系统出错，错误原因:{}",message);
    }

    public MyException(String code, CodeMessageService codeMessageService) {
        this(HttpStatus.OK.value(),code,codeMessageService);
    }

    public MyException(String code, String message) {
        this(HttpStatus.OK.value(), code, message);
    }

}
