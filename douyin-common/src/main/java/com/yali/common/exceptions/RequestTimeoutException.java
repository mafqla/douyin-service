package com.yali.common.exceptions;

/**
 * 请求超时异常
 **/
public class RequestTimeoutException extends CommonException {
    public RequestTimeoutException(String message) {
        super(message);
    }

    public RequestTimeoutException(int code, String message) {
        super(code, message);
    }

    public RequestTimeoutException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
