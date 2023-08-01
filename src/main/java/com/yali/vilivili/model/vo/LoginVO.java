package com.yali.vilivili.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Date 2022/11/16 22:48
 * @Author pq
 */
@Data
public class LoginVO {
    @ApiModelProperty(value = "userId")
    private Integer userId;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "token")
    private String token;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "type")
    private String type;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "user_avatar")
    private String userAvatar;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "email")
    private String email;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "phone")
    private String phone;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "userNum")
    private String userNum;

    /**
     * 用户ip属地
     */
    @ApiModelProperty(value = "ipLocation")
    private String ipLocation;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "gender")
    private String gender;

    /**
     * 用户生日
     */
    @ApiModelProperty(value = "birthdate")
    private String birthdate;

    /**
     * 用户个性签名
     */
    @ApiModelProperty(value = "signature")
    private String signature;

    /**
     * 用户学校
     */
    @ApiModelProperty(value = "school")
    private String school;

    /**
     * 用户所在地
     */
    @ApiModelProperty(value = "location")
    private String location;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "createtime")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
}
