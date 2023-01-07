package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Date 2022/11/16 22:58
 * @Author pq
 */
@Data
public class LoginRO {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
