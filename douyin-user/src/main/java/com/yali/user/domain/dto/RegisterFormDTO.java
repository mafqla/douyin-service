package com.yali.user.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "注册和修改密码的表单实体")
public class RegisterFormDTO {

    @ApiModelProperty(value = "手机号", example = "13800010004")
    private String cellPhone;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "验证码", example = "645632")
    private String code;
}
