package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注册请求参数
 *
 * @author fuqianlin
 * @date 2023-01-14 20:26
 **/
@Data
public class RegisterRO {

    /**
     * 用户名 不能为空且长度小于200
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{1,200}$", message = "只能由中文，英文字母，数字和下划线组成，且长度必须在1到200之间的字符串")
    private String username;

    /**
     * 密码 不能为空, 密码长度必须在11到20之间, 且必须由字母和数字组成，至少包含一个特殊字符和一个大写字母
     */

    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{11,20}$",
            message = "密码长度必须在11到20之间, 且必须由字母和数字组成，至少包含一个特殊字符和一个大写字母")
    private String password;

    /**
     * 确认密码 不能为空, 长度必须在11到20之间, 且必须由字母和数字组成，必须包含一个大写字母和所有的特殊字符
     */
    @ApiModelProperty("确认密码")
    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{11,20}$",
            message = "密码长度必须在11到20之间, 且必须由字母和数字组成，至少包含一个特殊字符和一个大写字母")
    private String confirmPassword;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 验证码 由6位数字字母组成
     */
    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "验证码由6位数字字母组成")
    private String code;
}
