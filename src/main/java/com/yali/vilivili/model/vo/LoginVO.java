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
     * 创建时间
     */
    @ApiModelProperty(value = "createtime")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
}
