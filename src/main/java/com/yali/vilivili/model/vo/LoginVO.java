package com.yali.vilivili.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}
