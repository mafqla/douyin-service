package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.*;


/**
 * @Description 新增用户RO
 * @Date 2023.1.6 18:34
 * @Author fuqianlin
 */
@ApiModel(description = "新增用户接受类")
@Data
public class updateUserRO {

    /**
     * 用户id
     */
    @ApiModelProperty(name = "id",
            value = "用户id",
            example = "1")
    @Nullable
    private Integer id;
    /**
     * 用户名，长度不超过50个字符
     */
    @ApiModelProperty(name = "username",
            value = "用户名，长度不超过50个字符",
            example = "JohnDoe")
    @Nullable
    @Size(max = 50, message = "用户名长度不能超过50个字符")
    private String username;

    /**
     * 用户邮箱，必填且需符合Email格式
     */
//    @ApiModelProperty(name = "email",
//            value = "用户邮箱，必填且需符合Email格式",
//            example = "john.doe@example.com")
//    @NotBlank(message = "邮箱不能为空")
//    @Email(message = "邮箱格式不正确")
//    private String email;

    /**
     * 用户编号，可为空，长度不超过20个字符
     */
    @ApiModelProperty(name = "userNum",
            value = "用户编号，可为空，长度不超过20个字符",
            example = "123456")
    @Size(max = 20, message = "用户编号长度不能超过20个字符")
    @Nullable
    private String userNum;

    /**
     * 用户手机号，可为空，需符合手机号格式
     */
    @ApiModelProperty(name = "phone",
            value = "用户手机号，可为空，需符合手机号格式",
            example = "13812345678")
    @Nullable
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 用户性别，可为空，取值为"男"或"女"
     */
    @ApiModelProperty(name = "gender",
            value = "用户性别，可为空，取值为\"男\"或\"女\"",
            example = "男")
    @Nullable
    @Pattern(regexp = "^男|女$", message = "性别取值不正确")
    private String gender;

    /**
     * 用户生日，可为空，格式为"yyyy-MM-dd"
     */
    @ApiModelProperty(name = "birthdate",
            value = "用户生日，可为空，格式为\"yyyy-MM-dd\"",
            example = "1990-01-01")
    @Nullable
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "生日格式不正确（应为yyyy-MM-dd）")
    private String birthdate;

    /**
     * 用户签名，长度不超过200个字符
     */
    @ApiModelProperty(name = "signature",
            value = "用户签名，长度不超过200个字符",
            example = "Live life to the fullest!")
    @Size(max = 200, message = "签名长度不能超过200个字符")
    @Nullable
    private String signature;

    /**
     * 用户学校，长度不超过50个字符
     */
    @ApiModelProperty(name = "school",
            value = "用户学校，长度不超过50个字符",
            example = "清华大学")
    @Size(max = 50, message = "学校名称长度不能超过50个字符")
    @Nullable
    private String school;

    /**
     * 用户所在地，长度不超过100个字符
     */
    @ApiModelProperty(name = "location",
            value = "用户所在地，长度不超过100个字符",
            example = "北京市海淀区中关村")
    @Size(max = 100, message = "所在地名称长度不能超过100个字符")
    @Nullable
    private String location;


}
