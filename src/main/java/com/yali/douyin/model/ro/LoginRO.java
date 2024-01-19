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
    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
