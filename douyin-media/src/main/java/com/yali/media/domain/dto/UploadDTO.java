package com.yali.media.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "视频文件信息")
public class UploadDTO {
    @ApiModelProperty(value = "mediaId", example = "1")
    private Long id;
    @ApiModelProperty(value = "文件requestId", example = "1")
    private String requestId;
    @ApiModelProperty(value = "文件名称", example = "Redis实战课.mp4")
    private String filename;
    @ApiModelProperty(value = "视频大小，单位字节", example = "1024")
    private Long size;
    @ApiModelProperty(value = "视频格式", example = "mp4")
    private String format;
    @ApiModelProperty(value = "视频url", example = "http://localhost:9000/media/21.mp4")
    private String url;
}
