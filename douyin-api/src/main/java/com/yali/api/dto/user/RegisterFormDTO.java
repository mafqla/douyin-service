package com.yali.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author fuqianlin
 * @date 2023-11-16 15:25
 **/
@Data
@ApiModel(description = "注册表单实体")
public class RegisterFormDTO {
    @ApiModelProperty(value = "手机号", example = "13567891234", required = true)
    @NotNull
    private String cellPhone;
    @ApiModelProperty(value = "验证码", example = "123456", required = true)
    @NotNull
    private String verifyCode;
}
