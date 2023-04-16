package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 添加用户
 *
 * @author fuqianlin
 * @date 2023-04-16 20:41
 **/

@ApiModel(description = "新增用户请求参数")
@Data
public class AddUserRO {
    /**
     * 用户名
     */
    @ApiModelProperty(name = "username", value = "用户名", required = false)
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[,.\\-+_!@#$%^&*();\\/|<>\"']).{11,20}$",
            message = "密码长度必须在11到20之间, 且必须由字母和数字组成，至少包含一个特殊字符和一个大写字母")
    @ApiModelProperty(name = "password", value = "用户密码", required = false)
    private String password;


    /**
     * 用户邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(name = "email", value = "用户邮箱", required = true)
    private String email;

}
