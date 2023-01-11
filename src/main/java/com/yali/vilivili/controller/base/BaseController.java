package com.yali.vilivili.controller.base;

import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Description
 * @Date 2022/12/22 10:47
 * @Author pq
 */
@Slf4j
public class BaseController {

    public com.yali.vilivili.controller.base.OR<Void> successResult() {
        com.yali.vilivili.controller.base.OR<Void> or = new com.yali.vilivili.controller.base.OR<>();
        or.setHttpStatus(HttpStatus.OK.value())
                .setCode(String.valueOf(HttpStatus.OK.value()))
                .setMsg("操作成功");
        return or;
    }

    public ResponseEntity<com.yali.vilivili.controller.base.OR<Void>> success() {
        com.yali.vilivili.controller.base.OR<Void> or = new com.yali.vilivili.controller.base.OR<>();
        or.setHttpStatus(HttpStatus.OK.value())
                .setCode(String.valueOf(HttpStatus.OK.value()))
                .setMsg(StringUtils.EMPTY);
        return new ResponseEntity<com.yali.vilivili.controller.base.OR<Void>>(HttpStatus.OK);
    }

    public <T> ResponseEntity<T> process(Supplier<T> sp) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(sp.get());
    }

    public void processException(com.yali.vilivili.controller.base.OR or, Exception ex) {
        if (ex instanceof MyException) {
            MyException MyException = (MyException) ex;
            or.setCode(MyException.getCode());
            or.setMsg(ex.getMessage());
            or.setHttpStatus(MyException.getStatusCode());
        } else {
            // 系统错误设置RequestId，方便开发人员排查错误日志
            or.setCode("500");
            String requestId = UUID.randomUUID().toString();
            log.error("[BaseController] Exception details of requestId: " + requestId, ex);
            or.setMsg(String.format("请求编号: %s, 错误消息: %s", requestId, ex.getMessage()));
            or.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processData(Supplier<M> sp, BindingResult br, String msg, BiConsumer<OR<M>, Exception> processExp) {
        return br.hasErrors() ? errorOstResult(br) : this.processDataCallback(sp, msg, processExp);
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processData(Supplier<M> sp, BindingResult br, BiConsumer<OR<M>, Exception> processExp) {
        return br.hasErrors() ? errorOstResult(br) : this.processDataCallback(sp, processExp);
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processData(Supplier<M> sp, BiConsumer<OR<M>, Exception> processExp) {
        return this.processDataCallback(sp, processExp);
    }

    private <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> errorOstResult(BindingResult br) {
        com.yali.vilivili.controller.base.OR<M> data = new com.yali.vilivili.controller.base.OR();
        data.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        data.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        List<String> errs = (List) br.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.status(data.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(data);
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processDataCallback(Supplier<M> sp, BiConsumer<com.yali.vilivili.controller.base.OR<M>, Exception> processExp) {
        com.yali.vilivili.controller.base.OR<M> data = new com.yali.vilivili.controller.base.OR();

        try {
            M val = sp.get();
            data.setCode(String.valueOf(HttpStatus.OK.value())).setData(val);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data);
        } catch (Exception var6) {
            if (Objects.nonNull(processExp)) {
                processExp.accept(data, var6);
            }
            return ResponseEntity.status(data.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(data);
        }
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processDataCallback(Supplier<M> sp, String msg, BiConsumer<com.yali.vilivili.controller.base.OR<M>, Exception> processExp) {
        com.yali.vilivili.controller.base.OR<M> data = new com.yali.vilivili.controller.base.OR();

        try {
            M val = sp.get();
            data.setMsg(msg);
            data.setCode(String.valueOf(HttpStatus.OK.value())).setData(val);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data);
        } catch (Exception var6) {
            if (Objects.nonNull(processExp)) {
                processExp.accept(data, var6);
            }
            return ResponseEntity.status(data.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(data);
        }
    }
}
