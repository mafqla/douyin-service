package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频请求参数
 *
 * @author fuqianlin
 * @date 2023-01-22 15:11
 **/

@Data
public class VideosRo {

    //添加视频
    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("视频描述")
    private String description;

    @ApiModelProperty("视频封面")
    private String cover;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("视频状态")
    private String status;


}
