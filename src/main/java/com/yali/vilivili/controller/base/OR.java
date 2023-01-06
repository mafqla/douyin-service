package com.yali.vilivili.controller.base;

import com.yali.vilivili.controller.base.OperationResult;
import org.springframework.http.ResponseEntity;

/**
 * @Description 操作结果
 * @Date 2022/12/22 11:02
 * @Author pq
 */
public class OR<T> extends OperationResult<T> {
    public OR(){
    }


    public boolean isSuccess() {
        return this.isResult();
    }

}
