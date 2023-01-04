package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description 新增用户RO
 * @Date 2023/1/3 21:44
 * @Author pq
 */
@ApiModel(description = "新增用户接受类")
@Data
public class UserAddRO {

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username", value = "用户名", required = false) //swagger
    private String username;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private String password;



    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private String email;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private int isValid;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private String uIp;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private Date createTime;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private Date updateTime;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "id", value = "用户id", required = false)
    private int type;
}
