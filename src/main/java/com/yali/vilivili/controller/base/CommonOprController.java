package com.yali.vilivili.controller.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Description
 * @Date 2022/12/22 10:58
 * @Author pq
 */
@Slf4j
public class CommonOprController {



    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processData(Supplier<M> sp, BindingResult br, BiConsumer<com.yali.vilivili.controller.base.OR<M>, Exception> processExp) {
        return this.processDataCallback(sp, br, (Consumer)null, processExp);
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processData(Supplier<M> sp, BiConsumer<com.yali.vilivili.controller.base.OR<M>, Exception> processExp) {
        return this.processDataCallback(sp, (Consumer)null, processExp);
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processDataCallback(Supplier<M> sp, BindingResult br, Consumer<com.yali.vilivili.controller.base.OR<M>> consumer, BiConsumer<com.yali.vilivili.controller.base.OR<M>, Exception> processExp) {
        return br.hasErrors() ? this.errorOstResult(br) : this.processDataCallback(sp, consumer, processExp);
    }

    private <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> errorOstResult(BindingResult br) {
        com.yali.vilivili.controller.base.OR<M> data = new com.yali.vilivili.controller.base.OR();
        data.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        data.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        List<String> errs = (List)br.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.status(data.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(data);
    }

    public <M> ResponseEntity<com.yali.vilivili.controller.base.OR<M>> processDataCallback(Supplier<M> sp, Consumer<com.yali.vilivili.controller.base.OR<M>> consumer, BiConsumer<com.yali.vilivili.controller.base.OR<M>, Exception> processExp) {
        com.yali.vilivili.controller.base.OR<M> data = new com.yali.vilivili.controller.base.OR();

        try {
            M val = sp.get();
            data.setCode(String.valueOf(HttpStatus.OK.value())).setData(val);
            if (Objects.nonNull(consumer)) {
                consumer.accept(data);
            }

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data);
        } catch (Exception var6) {
            CommonOprController.log.error(this.getClass().getSimpleName(), var6);
            if (Objects.nonNull(processExp)) {
                processExp.accept(data, var6);
            }

            return ResponseEntity.status(data.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(data);
        }
    }
}
