package com.yali.media.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频信息保存
 *
 * @author fuqianlin
 * @date 2024-01-11 14:14
 **/

@Data
@ApiModel(description = "视频信息")
public class MediaInfoDTO {
    @ApiModelProperty(value = "数据库mediaId", example = "1")
    private Long id;

    @ApiModelProperty(value = "请求id")
    private String requestId;

    @ApiModelProperty(value = "视频原名称", example = "test.mp4")
    private String originalMediaName;

    @ApiModelProperty(value = "视频上传后的名称", example = "test.mp4")
    private String uploadedMediaName;

    @ApiModelProperty(value = "视频地址", example = "http://xxx.mp4")
    private String mediaUrl;

    @ApiModelProperty(value = "视频后缀", example = "mp4")
    private String mediaSuffix;

    @ApiModelProperty(value = "视频大小，单位字节", example = "1024")
    private Long size;
}
