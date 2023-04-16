package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;


/**
 * @Description 新增用户RO
 * @Date 2023.1.6 18:34
 * @Author fuqianlin
 */
@ApiModel(description = "新增用户接受类")
@Data
public class updateAndSaveUserRO {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = false)
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username", value = "用户名" ,required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{11,20}$",
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

    /**
     * 用户头像
     */
    @ApiModelProperty(name = "user_avatar", value = "用户头像", required = false)
    private String user_avatar;

    /**
     * 用户是否有效
     */
    @ApiModelProperty(name = "isValid", value = "用户是否有效", required = false)
    private byte isValid;

    /**
     * 用户ip
     */
    @ApiModelProperty(name = "u_ip", value = "用户ip", required = false)
    private String u_ip;

    /**
     * 用户注册时间
     */
    @ApiModelProperty(name = "createTime", value = "用户注册时间", required = false)
    private Date createTime;

    /**
     * 用户更新时间
     */
    @ApiModelProperty(name = "updateTime", value = "用户更新时间", required = false)
    private Date updateTime;

    /**
     * 用户类型
     */
    @ApiModelProperty(name = "type", value = "用户类型", required = false)
    private byte type;
}
