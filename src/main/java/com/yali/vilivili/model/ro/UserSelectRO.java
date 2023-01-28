package com.yali.vilivili.model.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @Description
 * @Date 2023/1/4 10:37
 * @Author pq
 */
@Data
public class UserSelectRO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    private int isValid;

    private int type;
}
